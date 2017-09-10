package com.phoebe.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket")
public class EchoEndpoint {
	 
	private static Long onlineCount = 0l;
	private Session session;
	private static CopyOnWriteArraySet<EchoEndpoint> echoEndpoints = new CopyOnWriteArraySet<>();
	
	@OnOpen
	public void onOpen(Session session) throws IOException {
		this.session = session;
		echoEndpoints.add(this);
		addOnlineCount();
		System.out.println("当前在线人数为: "+getOnlineCount());
	}
	 
	@OnMessage
	public String onMessage(String message) throws IOException {
		System.out.println("来自客户端的消息:" + message);
		for (EchoEndpoint echoEndpoint : echoEndpoints) {
			echoEndpoint.sendMessagae(message);
		}
		return message;
	
	}
	 
	 
	@OnError
	public void onError(Throwable t) {
		System.out.println("发生错误!!!!");
		t.printStackTrace();
	}
	 
	@OnClose
	public void onClose(Session session, CloseReason reason) {
		echoEndpoints.remove(this);
		subOnlineCount();
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	} 
	
	public void sendMessagae(String message) throws IOException{
		this.session.getBasicRemote().sendText(message);
	}
	
	private synchronized Long getOnlineCount(){
		return EchoEndpoint.onlineCount;
	}
	
	private synchronized void addOnlineCount(){
		EchoEndpoint.onlineCount++;
	}
	
	private synchronized void subOnlineCount(){
		EchoEndpoint.onlineCount--;
	}
 
}
