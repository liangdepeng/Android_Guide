package com.example.myapplication.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.TextUtils;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ldp.
 * <p>
 * Date: 2021/11/15
 * <p>
 * Summary:
 */
public class ImageUtil {

//    /**
//     * 处理旋转后的图片
//     * 默认不压缩
//     *
//     * @param originpath    原图路径
//     * @param context       上下文
//     * @param isReplaceFile 是否替换之前的文件 true 替换 false 不替换 默认保存位置
//     * @return 返回修复完毕后的图片路径
//     */
//    public static String amendRotatePhoto(String originpath, Context context, boolean isReplaceFile) {
//        return amendRotatePhoto(originpath, context, false, isReplaceFile);
//    }
//
//    /**
//     * 处理旋转后的图片
//     * 默认不压缩
//     * 默认替换原图路径下保存
//     *
//     * @param originpath
//     * @param context
//     * @return
//     */
//    public static String amendRotatePhoto(String originpath, Context context) {
//        return amendRotatePhoto(originpath, context, false, true);
//    }
//
//    /**
//     * 处理旋转后的图片
//     *
//     * @param originpath    原图路径
//     * @param context       上下文
//     * @param isCompress    是否压缩
//     * @param isReplaceFile 是否替换之前的文件 true 替换 false 不替换 默认保存位置
//     * @return 返回修复完毕后的图片路径
//     */
//    public static String amendRotatePhoto(String originpath, Context context, boolean isCompress, boolean isReplaceFile) {
//
//        if (TextUtils.isEmpty(originpath)) return originpath;
//
//        // 取得图片旋转角度
//        int angle = readPictureDegree(originpath);
//
//        //是否压缩
//        Bitmap bmp = null;
////        if (isCompress) {
////            // 把原图压缩后得到Bitmap对象
////            bmp = getCompressPhoto(originpath);
////        }
//
//        if (bmp != null) {
//            //处理旋转
//            Bitmap bitmap = null;
//            if (angle != 0) {
//                // 修复图片被旋转的角度
//                bitmap = rotaingImageView(angle, bmp);
//            }
//            if (bitmap != null) {
//
//            }
//            // 保存修复后的图片并返回保存后的图片路径
//            return savePhotoToSD(bitmap, originpath, context, isReplaceFile);
//        } else {
//            Bitmap localBitmap = getLocalBitmap(originpath);
//            if (localBitmap == null) return originpath;
//            //处理旋转
//            Bitmap bitmap = null;
//            if (angle != 0) {
//                // 修复图片被旋转的角度
//                bitmap = rotaingImageView(angle, localBitmap);
//            }
//            if (bitmap != null) {
//                return savePhotoToSD(bitmap, originpath, context, isReplaceFile);
//            } else {
//                return originpath;
//            }
//        }
//    }

    /**
     * 读取照片旋转角度
     *
     * @param path 照片路径
     * @return 角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /* 旋转图片
     *
     * @param angle  被旋转角度
     * @param bitmap 图片对象
     * @return 旋转后的图片
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bitmap;
        }
        if (bitmap != returnBm) {
            bitmap.recycle();
        }
        return returnBm;
    }

//    /**
//     * 保存Bitmap图片在SD卡中
//     * 如果没有SD卡则存在手机中
//     *
//     * @param mbitmap       需要保存的Bitmap图片
//     * @param originpath    文件的原路径
//     * @param isReplaceFile 是否替换原文件
//     * @return 保存成功时返回图片的路径，失败时返回null
//     */
//    public static String savePhotoToSD(Bitmap mbitmap, String originpath, Context context, boolean isReplaceFile) {
//        FileOutputStream outStream = null;
//        String fileName = "";
//        if (mbitmap == null) return originpath;
//        if (isReplaceFile) {
//            fileName = getPhotoFileName(context);
//        } else {
//            if (TextUtils.isEmpty(originpath)) return originpath;
//            fileName = originpath;
//        }
//        try {
//            outStream = new FileOutputStream(fileName);
//            // 把数据写入文件，100表示不压缩
//            mbitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
//            return fileName;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            try {
//                if (outStream != null) {
//                    // 记得要关闭流！
//                    outStream.close();
//                }
//                if (mbitmap != null) {
//                    mbitmap.recycle();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }


}
