package com.danbplus.ldk_ehcoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;


import com.danbplus.properties.PropertiesEx;

public class EchoClientMain {
	 
    public static void main(String[] args) throws Exception {
    	
    	PropertiesEx propertiesEx;
    	
        // Ŭ���̾�Ʈ ���� ����
 
        Socket socket = new Socket();
        Scanner sc = new Scanner(System.in);
 
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
 
        OutputStream os = null;
        OutputStreamWriter osw = null;
        PrintWriter pw = null;
 
        // new InetSocketAddress(InetAddress.getLocalHost() 6077
        
        int port;
        port = Integer.parseInt(PropertiesEx.main(args).getProperty("port"));
        
        try {
            socket.connect(new InetSocketAddress(InetAddress.getLocalHost(), port));
            System.out.println("[client] connected with server");
 
            while (true) {
 
                is = socket.getInputStream(); //
                isr = new InputStreamReader(is, "UTF-8");
                br = new BufferedReader(isr);
 
                os = socket.getOutputStream();
                osw = new OutputStreamWriter(os, "UTF-8");
                pw = new PrintWriter(osw, true);
 
                // �д°�
                System.out.print("client : ");
                String data = sc.nextLine();
 
                if ("exit".equals(data))
                    break;
 
                pw.println(data);
 
                data = br.readLine(); //���پ� �о�ͼ� data�� ��´�
                System.out.println("server : " + data);
 
            }
 
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();  //��Ʈ��ũ ���� ����
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
 
            sc.close(); //��ĳ�� �ݱ�
            

    		try {
    			// close�ϱ� ���� ��ü�� �����ϴ��� Ȯ���Ѵ�.
    			if(is != null) is.close();
    			if(isr != null) isr.close();
    			if(br != null) br.close();
    			if(os != null) os.close();
    			if(osw != null) osw.close();
    			if(pw != null) pw.close();
    			System.out.println("EchoClient ����");
    		} catch (Exception e2) {
    			
    		}
    		
    		
        }	
        
 
    }
 
}