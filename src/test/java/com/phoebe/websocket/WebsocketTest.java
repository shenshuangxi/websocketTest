package com.phoebe.websocket;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.java_websocket.WebSocket.READYSTATE;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.ProxyServer;
import com.ning.http.client.Request;
import com.ning.http.client.Response;
import com.ning.http.client.ws.DefaultWebSocketListener;
import com.ning.http.client.ws.WebSocket;
import com.ning.http.client.ws.WebSocketListener;
import com.ning.http.client.ws.WebSocketTextListener;
import com.ning.http.client.ws.WebSocketUpgradeHandler;

public class WebsocketTest {

	private static WebSocketClient client;
	
	public static void main(String[] args) throws Exception {
		
		AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		ListenableFuture<WebSocket> listenableFuture = asyncHttpClient.prepareGet("ws://localhost:8080/websocket")
				.execute(new WebSocketUpgradeHandler.Builder().addWebSocketListener(new WebSocketTextListener() {
					
					@Override
					public void onOpen(WebSocket websocket) {
						System.out.println("打开链接");
						websocket.sendMessage("你好啊 服务器!!!!!!!");
					}
					
					@Override
					public void onError(Throwable t) {
						t.printStackTrace();
				        System.out.println("发生错误已关闭");
					}
					
					@Override
					public void onClose(WebSocket websocket) {
						System.out.println("链接已关闭");
					}

					@Override
					public void onMessage(String message) {
						System.out.println("收到消息"+message);
					}
				}).build());
		
		
		
//		client = new WebSocketClient(new URI("ws://localhost:8080/websocket"),new Draft_17()) {
//
//	        @Override
//	        public void onOpen(ServerHandshake arg0) {
//	            System.out.println("打开链接");
//	        }
//
//	        @Override
//	        public void onMessage(String arg0) {
//	            System.out.println("收到消息"+arg0);
//	        }
//
//	        @Override
//	        public void onError(Exception arg0) {
//	            arg0.printStackTrace();
//	            System.out.println("发生错误已关闭");
//	        }
//
//	        @Override
//	        public void onClose(int arg0, String arg1, boolean arg2) {
//	            System.out.println("链接已关闭");
//	        }
//
//	        @Override
//	        public void onMessage(ByteBuffer bytes) {
//	            try {
//	                System.out.println(new String(bytes.array(),"utf-8"));
//	            } catch (UnsupportedEncodingException e) {
//	                e.printStackTrace();
//	            }
//	        }
//
//
//	    };
//
//	    client.connect();
//
//	    while(!client.getReadyState().equals(READYSTATE.OPEN)){
//	        System.out.println("还没有打开");
//	    }
//	    System.out.println("打开了");
////	    send("hello world".getBytes("utf-8"));
//	    client.send("hello world");
		
		
		
		
		
	}
	
	public static void send(byte[] bytes){
	    client.send(bytes);
	}
	
}
