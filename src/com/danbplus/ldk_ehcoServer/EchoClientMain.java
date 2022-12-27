package com.danbplus.ldk_ehcoServer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;


import com.danbplus.properties.PropertiesEx;

public class EchoClientMain {
	 
    public static void main(String[] args) throws Exception {
    	
    	
        // 클라이언트 소켓 생성
 
        Socket socket = new Socket();
        Scanner sc = new Scanner(System.in);
 
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
 
        OutputStream os = null;
        OutputStreamWriter osw = null;
        PrintWriter pw = null;
 
        // new InetSocketAddress(InetAddress.getLocalHost() 6011
        
        Properties pro= new Properties();
        
        try {
        	pro.load(new FileInputStream("C:\\workspace_ldk\\ldk_ehcoServer\\config\\test.properties"));
        } catch (IOException e) {
        	System.out.println("파일 불러오기 오류 -> " + e);
        }
        
        String ip= pro.getProperty("echo_ip"); // 대소문자 구분
        System.out.println("client echo_ip  : " + ip);
        int port= Integer.parseInt((pro.getProperty("echo_port"))); // 대소문자 구분
        System.out.println("client echo_port  : " + port);
        
        try {
            socket.connect(new InetSocketAddress(ip, port));
            System.out.println("[client] connected with server");
 
            while (true) {
 
                is = socket.getInputStream(); //
                isr = new InputStreamReader(is, "UTF-8");
                br = new BufferedReader(isr);
 
                os = socket.getOutputStream();
                osw = new OutputStreamWriter(os, "UTF-8");
                pw = new PrintWriter(osw, true);
 
                // 읽는거
                System.out.print("client : ");
                String data = sc.nextLine();
 
                if ("exit".equals(data))
                    break;
 
                pw.println(data);
 
                data = br.readLine(); //한줄씩 읽어와서 data에 담는다
                System.out.println("server : " + data);
 
            }
 
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();  //네트워크 접속 종료
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
 
            sc.close(); //스캐너 닫기
            

    		try {
    			// close하기 전에 객체가 존재하는지 확인한다.
    			if(is != null) is.close();
    			if(isr != null) isr.close();
    			if(br != null) br.close();
    			if(os != null) os.close();
    			if(osw != null) osw.close();
    			if(pw != null) pw.close();
    			if(pro != null)pro.clone();
    			System.out.println("EchoClient 종료");
    		} catch (Exception e2) {
    			
    		}
    		
    		
        }	
        
 
    }
 
}
