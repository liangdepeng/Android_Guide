package com.example.down_module.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PipedReader;
import java.util.EventListener;
import java.util.HashMap;

import abc.common.util.KtExpandUtil;
import cn.example.common_module.AppContext;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/10/22
 * <p>
 * Summary:
 */
public class FileUtil {

    private String path;
    private final String tag = "FFFFFFF";
    public int IMAGE = 0;
    public int VIDEO = 1;
    // 文件类型
    private String environmentType = Environment.DIRECTORY_MOVIES;

    public FileUtil(String fileType) throws Exception {
        environmentType = fileType;
        // 初始化文件夹存放路径
        path = getStorePath();
        File file = new File(path);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
            Log.e(tag, String.valueOf(mkdirs));
        }
    }

    /**
     * 获取存储路径
     */
    private String getStorePath() throws Exception {
        // 存储媒体sd卡已经挂载，并且挂载点可读/写 注意6.0以上申请动态权限
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            throw new Exception("SD卡异常");

        if (Build.VERSION.SDK_INT >= 29) {
            // 沙盒存储 位于app包内部 外部不可见 android p 以上
            return AppContext.getAppContext().getExternalFilesDir(environmentType).toString() +
                    "/aaatestfill";
        }

        // 外部存储
        return Environment.getExternalStorageDirectory().toString() + "/aaatestfile";
    }

    /**
     * 创建文件 在生成的文件夹下
     *
     * @param fileName
     * @return
     */
    public File createFile(String fileName) {
        if (fileName == null || "".equals(fileName))
            throw new IllegalArgumentException("文件名不能为空");
        return new File(path, fileName);
    }

    /**
     * Android Q 开始android不允许直接访问外部存储 每个应用会有沙盒机制存储
     *
     * @param file 手动复制文件插入媒体库 使用 ContentProvider
     */
    public void copyFileToSdCardForAndroidQ(File file) {
        try {
            // 构建要插入的数据
            ContentValues contentValues = new ContentValues();
            // todo fix waiting
            contentValues.put(MediaStore.MediaColumns.TITLE,
                    file.getName().substring(file.getName().lastIndexOf(".") + 1));
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, file.getName());
            contentValues.put(MediaStore.MediaColumns.DATE_MODIFIED, System.currentTimeMillis());
            contentValues.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis());
            contentValues.put(MediaStore.MediaColumns.SIZE, file.length());
            //2018.12-2019.12 2020.05-2021-至今
            //  contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");

            if (Build.VERSION.SDK_INT >= 29) {
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,
                        Environment.DIRECTORY_DCIM);
            } else {
                contentValues.put(MediaStore.MediaColumns.DATA, file.getAbsolutePath());
            }

            // 访问公共媒体数据
            ContentResolver contentResolver = AppContext.getAppContext().getContentResolver();
            // 这里要区分类型 是图片还是视频还是其他文件等等

            Uri uri = null;
            if (TextUtils.equals(environmentType, Environment.DIRECTORY_PICTURES)) {
                uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues);
            } else {
                uri = contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        contentValues);
            }
            if (uri != null) {
                BufferedInputStream inputStream = null;
                OutputStream outputStream = null;

                // 复制文件 到手机共享媒体库
                inputStream = new BufferedInputStream(new FileInputStream(file.getAbsolutePath()));
                outputStream = contentResolver.openOutputStream(uri);

                if (outputStream != null) {

                    byte[] bytes = new byte[1024];
                    int len = 0;
                    while ((len = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, len);
                    }
                    outputStream.close();
                    inputStream.close();
                }
            }
        } catch (Exception e) {
            KtExpandUtil.Companion.showToast(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param file              要复制的文件
     * @param toFilePath        复制到的文件夹路径 不包括文件名
     * @param toFileAllPathName 复制到的文件路径 包括文件名
     * @return 是否成功
     */
    public boolean copyFile(File file, String toFilePath, String toFileAllPathName) {
        if (!file.exists()) {
            Log.e(tag, "文件不存在");
            return false;
        }
        // 复制的文件夹是否存在
        File dirs = new File(toFilePath);
        if (!dirs.exists()) {
            dirs.mkdirs();
        }
        // 输入输出流
        FileOutputStream outputStream;
        FileInputStream inputStream;
        try {
            // 输出文件 写入输出流
            outputStream = new FileOutputStream(toFileAllPathName);
            // 要复制的文件写入输入流
            inputStream = new FileInputStream(file);

            byte[] bytes = new byte[1024];
            int len = 0;
            // 每次读取1024字节
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            inputStream.close();
            outputStream.flush();
            return true;
        } catch (Exception e) {
            KtExpandUtil.Companion.showToast(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    /**
     * api>=29 不能用此方法
     * <p>
     * 获取用于放置特定类型文件的顶级共享/外部存储目录。 这是用户通常放置和管理他们自己的文件的地方，因此您应该小心放置在这里的内容，
     * 以确保您不会删除他们的文件或妨碍他们自己的组织。
     * 在有多个用户的设备上（如UserManager ），每个用户都有自己独立的共享存储。 应用程序只能访问它们正在运行的用户的共享存储。
     * 以下是在公共共享存储上操作图片的典型代码示例：
     * <p>
     * 已弃用
     * 为了提高用户隐私，不推荐直接访问共享/外部存储设备。 当应用以Build.VERSION_CODES.Q为目标时，应用无法再直接访问从此方法返回的路径。
     * 通过迁移到Context.getExternalFilesDir(String) 、 MediaStore或Intent.ACTION_OPEN_DOCUMENT等替代方案，
     * 应用程序可以继续访问存储在共享/外部存储上的内容。
     * <p>
     * 参数：
     * type – 要返回的存储目录的类型。
     * 应该是一个DIRECTORY_MUSIC ， DIRECTORY_PODCASTS ， DIRECTORY_RINGTONES ， DIRECTORY_ALARMS ，
     * DIRECTORY_NOTIFICATIONS ，
     * DIRECTORY_PICTURES ， DIRECTORY_MOVIES ， DIRECTORY_DOWNLOADS ， DIRECTORY_DCIM
     * ，或DIRECTORY_DOCUMENTS 。 不能为空。
     * 返回：
     * 返回目录的文件路径。 请注意，此目录可能尚不存在，因此您必须在使用它之前确保它存在，例如File.mkdirs()
     */
    private void getExternalStoragePublicDirectory() {
        Environment.getExternalStoragePublicDirectory("");
    }

    /**
     * api>=29 不能用此方法
     * <p>
     * 返回主共享/外部存储目录。 如果该目录已由用户安装在其计算机上、已从设备中删除或发生了其他一些问题，则当前可能无法访问该目录。
     * 您可以使用getExternalStorageState()确定其当前状态。
     * 注意：不要被这里的“外部”一词混淆。 这个目录最好被认为是媒体/共享存储。 它是一个文件系统，可以保存相对大量的数据，
     * 并在所有应用程序之间共享（不强制执行权限）。 传统上这是一个 SD 卡，但它也可以作为设备中的内置存储实现，该设备与受保护的内部存储不同，
     * 可以作为文件系统安装在计算机上。
     * 在有多个用户的设备上（如UserManager ），每个用户都有自己独立的共享存储。 应用程序只能访问它们正在运行的用户的共享存储。
     * 在具有多个共享/外部存储目录的设备中，此目录代表用户将与之交互的主存储。 可以通过Context.getExternalFilesDirs(String) 、
     * Context.getExternalCacheDirs()和Context.getExternalMediaDirs()访问二级存储。
     * 应用程序不应直接使用此顶级目录，以免污染用户的根命名空间。 应用程序私有的任何文件都应放在Context.getExternalFilesDir返回的目录中，
     * 如果应用程序被卸载，系统将负责删除该目录。 其他共享文件应放置在getExternalStoragePublicDirectory返回的目录之一中。
     * 写入此路径需要Manifest.permission.WRITE_EXTERNAL_STORAGE权限，从Build.VERSION_CODES.KITKAT开始，
     * 读取访问需要Manifest.permission.READ_EXTERNAL_STORAGE权限，如果您拥有写入权限，则会自动授予该权限。
     * 从Build.VERSION_CODES.KITKAT开始，如果您的应用程序只需要存储内部数据，请考虑使用Context.getExternalFilesDir(String) 、
     * Context.getExternalCacheDir()或Context.getExternalMediaDirs() ，它们不需要读写权限。
     * 此路径可能因平台版本而异，因此应用程序应仅保留相对路径。
     * 以下是监控外部存储状态的典型代码示例：
     * <p>
     * 已弃用
     * 为了提高用户隐私，不推荐直接访问共享/外部存储设备。 当应用以Build.VERSION_CODES.Q为目标时，
     * 应用无法再直接访问从此方法返回的路径。 通过迁移到Context.getExternalFilesDir(String) 、
     * MediaStore或Intent.ACTION_OPEN_DOCUMENT等替代方案，应用程序可以继续访问存储在共享/外部存储上的内容
     */
    private void getExternalStorageDirectory() {
        Environment.getExternalStorageDirectory();
    }
}
