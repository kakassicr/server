package com.qq.server.service;

import com.chat.common.Message;
import com.qq.server.db.SqlHelper;

public class MessageService {
	String sql = "";
	public void addMessage(Message message){
		sql="insert into messages(sender,getter,content,sendTimer,isGet) values(?,?,?,?,?)";
		String[] paras = {message.getSender(),message.getGetter(),
				message.getCon(),message.getSendTime(),message.getIsGet()+""};
		new SqlHelper().execute(sql, paras);
	}
}
