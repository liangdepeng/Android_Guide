package com.example.myapplication.socket;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Socket 客户端
 * 在同一台电脑上 IntelliJ IDEA  运行对应的 服务端代码即可
 *
 * https://github.com/liangdepeng/java_All_practice/blob/master/src/http/SocketServer.java
 */
public class SocketClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_client);

        findViewById(R.id.sumbit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);

                // android 网络访问在子线程 别忘了清单文件的网络权限
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        initTcpClient();
                    }
                }).start();
            }
        });
    }

    private void initTcpClient() {
        try {
            //创建客户端Socket，指定服务器的IP地址和端口
            Socket socket = new Socket("192.200.23.43", 1234);
            //获取输出流，向服务器发送数据
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.println("客户端发送的数据");
            printWriter.println("客户端发送的数据222");
            printWriter.flush();
            socket.shutdownOutput();

            // 获取服务端输入流 发过来的信息 读取信息
            InputStream inputStream = socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String data = null;

            while ((data = bufferedReader.readLine()) != null) {
                String finalData = data;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SocketClientActivity.this, finalData, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            socket.shutdownInput();
            // 关闭释放资源
            bufferedReader.close();
            reader.close();
            inputStream.close();
            printWriter.close();
            outputStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(SocketClientActivity.this, "异常" + e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            });
        }finally {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.sumbit_btn).setEnabled(true);
                }
            });
        }
    }
}