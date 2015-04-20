package com.qq.server.model;

import java.util.*;
import java.net.*;
import java.io.*;

import com.chat.common.*;
import com.qq.server.service.MessageService;

public class SerConClientThread extends Thread {

	Socket s;

	public SerConClientThread(Socket s) {

		this.s = s;
	}

	public void notifyOther(String iam) {

		HashMap hm = ManageClientThread.hm;
		Iterator it = hm.keySet().iterator();

		while (it.hasNext()) {
			Message m = new Message("", 1);
			m.setCon(iam);
			m.setType(MessageType.message_ret_onLineFriend);

			String onLineUserId = it.next().toString();
			try {
				ObjectOutputStream oos = new ObjectOutputStream(
						ManageClientThread.getClientThread(onLineUserId).s
								.getOutputStream());
				m.setGetter(onLineUserId);
				oos.writeObject(m);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}

		}
	}
	
	public void run() {
		try {
			ObjectInputStream ois;
			ObjectOutputStream oos;
			Message m;
			MessageService messageService=new MessageService();
			ois = new ObjectInputStream(s.getInputStream());
			while (true) {
				
				m = (Message) ois.readObject();
				m.setIsGet(0);
				
				System.out.println(m.getSender() + " 给 " + m.getGetter()
						+ " 说:" + m.getCon());

				// if(m.getMesType().equals(MessageType.message_comm_mes))
				// {
				//
//				if (!m.getSender().equals(m.getGetter())) {
				SerConClientThread sc = ManageClientThread.getClientThread(m.getGetter());
				oos = new ObjectOutputStream(sc.s.getOutputStream());
					oos.writeObject(m);
					messageService.addMessage(m);
					System.out.println("oos");
					
//				}
				// }else3
				// if(m.getMesType().equals(MessageType.message_get_onLineFriend))
				// {
				// System.out.println(m.getSender()+" Ҫ��ĺ���");
				//
				// String res=ManageClientThread.getAllOnLineUserid();
				// Message m2=new Message();
				// m2.setMesType(MessageType.message_ret_onLineFriend);
				// m2.setCon(res);
				// m2.setGetter(m.getSender());
				// ObjectOutputStream oos=new
				// ObjectOutputStream(s.getOutputStream());
				// oos.writeObject(m2);
				// }
				//
			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

	}
}
