package com.qq.server.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.chat.common.Message;
import com.chat.common.User;
import com.qq.server.service.UserService;

public class MyQqServer extends Thread{
	public MyQqServer() {

		

	}
	
	@Override
	public void run() {

		// TODO Auto-generated method stub
		try {
			ServerSocket ss= new ServerSocket(9999);
			System.out.println("已监听");
			while (true) {
				Socket s= ss.accept();
				System.out.println("已连接");
				ObjectInputStream ois = new ObjectInputStream(
						s.getInputStream());
				User u = (User) ois.readObject();
				System.out.println("用户名:" + u.getAccount()
						+ " 密码:" + u.getPassword());
				Message m = new Message(null,1);
				ObjectOutputStream oos = new ObjectOutputStream(
						s.getOutputStream());
				UserService userService=new UserService();
				if (userService.CheckUser(u.getAccount(), u.getPassword())) {	
					String friendlist=userService.getFriendlist(u.getAccount());
					m.setCon(friendlist);
					oos.writeObject(m);
					SerConClientThread scct = new SerConClientThread(s);
					ManageClientThread.addClientThread(u.getAccount(), scct);
					
					scct.start();
//
//					scct.notifyOther(u.getAccount());
				} else {
					m.setType(2);
					oos.writeObject(m);
					s.close();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
		}
	}
	
//	public void close(){
//		try {
//			if(ss!=null){
//			ss.close();
//			}
//			System.out.println("已关闭");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
