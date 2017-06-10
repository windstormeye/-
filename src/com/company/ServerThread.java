package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by pjpjpj on 2017/6/4.
 */
public class ServerThread extends Thread {
    // 和本线程相关的Socket
    Socket socket = null;
    // 定长线程池的大小根据系统资源进行设置

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    //线程执行的操作，响应客户端的请求
    @Override
    public void run() {
        System.out.println("线程:" + Thread.currentThread().getName() + "正在执行");

        InputStream is=null;
        InputStreamReader isr=null;
        BufferedReader br=null;
        OutputStream os=null;
        PrintWriter pw=null;
        ChineseParticiple chineseParticiple = new ChineseParticiple();
        try {
            //获取输入流，并读取客户端信息
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String info=null;
            String lastString = new String();
            while((info=br.readLine())!=null){//循环读取客户端的信息
                System.out.println("客户端："+info);
                lastString = info;
            }

            socket.shutdownInput();//关闭输入流
            //获取输出流，响应客户端的请求
            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            Machine machine = new Machine(lastString);
            try {
                pw.write(machine.rutrunString());
            } catch (Exception e) {

            }
            pw.flush();//调用flush()方法将缓冲输出
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            //关闭资源
            try {
                if(pw!=null)
                    pw.close();
                if(os!=null)
                    os.close();
                if(br!=null)
                    br.close();
                if(isr!=null)
                    isr.close();
                if(is!=null)
                    is.close();
                if(socket!=null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
