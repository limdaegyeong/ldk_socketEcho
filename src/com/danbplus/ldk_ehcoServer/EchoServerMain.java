package com.danbplus.ldk_ehcoServer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

public class EchoServerMain {
	 
 
    public static void main(String[] args) throws Exception {
 
        ServerSocket serverSocket = null; 
 
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
 
        OutputStream os = null;
        OutputStreamWriter osw = null;
        PrintWriter pw = null;
        Scanner sc = new Scanner(System.in);
        
        
        Properties pro= new Properties();
        
        try {
        	pro.load(new FileInputStream("C:\\workspace_ldk\\ldk_ehcoServer\\config\\test.properties"));
        } catch (IOException e) {
        	System.out.println("파일 불러오기 오류 -> " + e);
        }
        
        String ip= pro.getProperty("echo_ip"); // 대소문자 구분
        System.out.println("Server echo_ip  : " + ip);
        int port= Integer.parseInt((pro.getProperty("echo_port"))); // 대소문자 구분
        System.out.println("Server echo_port  : " + port);

        
 
        try {
            // 1. Server Socket 생성
            serverSocket = new ServerSocket();
 
            // 2. Binding : Socket에 SocketAddress(IpAddress + Port) 바인딩 함
 
            //InetAddress inetAddress = InetAddress.getLocalHost();
            //String localhost = inetAddress.getHostAddress();
 
            serverSocket.bind(new InetSocketAddress(ip, port)); //로컬호스트와 포트번호로 바인딩함
            
            
            //System.out.println("[server] binding " + localhost);
 
            // 3. accept(클라이언트로 부터 연결요청을 기다림)
 
            Socket socket = serverSocket.accept();
            
            
//            InetSocketAddress socketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
            //ㄴ> properties에서 가져와 쓰기때문에 사용하지 않음.
            
//            System.out.println("[server] connected by client");
//            System.out.println("[server] Connect with " + socketAddress.getHostString() + " " + socket.getPort());
 
            while (true) {
 
                // inputStream 가져와서 (주 스트림) StreamReader와 BufferedReader로 감싸준다 (보조 스트림)
                is = socket.getInputStream();
                isr = new InputStreamReader(is, "UTF-8");
                br = new BufferedReader(isr);
 
                // outputStream 가져와서 (주 스트림) StreamWriter와 PrintWriter로 감싸준다 (보조 스트림)
                os = socket.getOutputStream();
                osw = new OutputStreamWriter(os, "UTF-8");
                pw = new PrintWriter(osw, true);
 
                String buffer = null;
                buffer = br.readLine(); // Blocking
                if (buffer == null) {
 
                    // 정상종료 : remote socket close()
                    // 메소드를 통해서 정상적으로 소켓을 닫은 경우
                    System.out.println("[server] closed by client");
                    break;
 
                }
 
                System.out.println("[server] recived : " + buffer);
                pw.println(buffer);
 
            }
 
            // 3.accept(클라이언트로 부터 연결요청을 기다림)
            // .. blocking 되면서 기다리는중, connect가 들어오면 block이 풀림
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
 
            try {
 
                if (serverSocket != null && !serverSocket.isClosed())
                    serverSocket.close();
 
            } catch (Exception e) {
                e.printStackTrace();
            }
 
            sc.close();
            
    		try {
    			// close하기 전에 객체가 존재하는지 확인한다.
    			if(is != null) is.close();
    			if(isr != null) isr.close();
    			if(br != null) br.close();
    			if(os != null) os.close();
    			if(osw != null) osw.close();
    			if(pw != null) pw.close();
    			System.out.println("EchoServer 종료");
    		} catch (Exception e2) {
    			
    		}
            
        }
 
    }
 
}