package com.qq.server.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.chat.common.Message;
import com.chat.common.User;

public class MyQqServer {

	public MyQqServer() {

		try {
			ServerSocket ss = new ServerSocket(9999);
			System.out.println("已监听");
			while (true) {
				Socket s = ss.accept();

				ObjectInputStream ois = new ObjectInputStream(
						s.getInputStream());
				User u = (User) ois.readObject();
				System.out.println("用户名:" + u.getAccount()
						+ " 密码:" + u.getPassword());
				Message m = new Message(null,1);
				ObjectOutputStream oos = new ObjectOutputStream(
						s.getOutputStream());
				if (u.getPassword().equals("123")) {

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

}
