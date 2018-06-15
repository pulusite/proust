package rpc.simpleio;

import rpc.core.RpcExport;
import rpc.utils.CGLibUtils;
import rpc.utils.FastJsonUtils;
import rpc.utils.InvokeEnity;

import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class SocketProducter implements RpcExport {
	
	@Override
	 public void export(Object service, int port ) {    
	        try {     
	            ServerSocket serverSocket = new ServerSocket(port);    
	            while (true) {    
	                Socket client = serverSocket.accept();    
	                new HandlerThread(client,service);    
	            }    
	        } catch (Exception e) {    
	        	e.printStackTrace();
	        }    
	    }    
	    
	    private class HandlerThread implements Runnable {    
	        private Socket socket;   
	        private Object service ;
	        public HandlerThread(Socket client,Object service) {    
	            socket = client;    
	            this.service = service;
	            new Thread(this).start();    
	        }    
	    
	        public void run() {    
	            try {    
	                DataInputStream input = new DataInputStream(socket.getInputStream());  
	                String request = input.readUTF(); 
	                Object execute = null;
	               if( FastJsonUtils.isJson(request)){ 
	            	   InvokeEnity entityEnity = InvokeEnity.transefer(request);
						String methodName = entityEnity.getMethodName();
						List<String> parameterTypeses = entityEnity.getParameterTypes();
						List<String> parameteres = entityEnity.getParameters();
						Class<?>[] parameterTypes = new Class<?>[parameterTypeses.size()];
						Object[] parameters = new Object[parameterTypeses.size()];
						for (int i = 0; i <parameterTypeses.size(); i++) {
							parameterTypes[i] = Class.forName(parameterTypeses.get(i));
							parameters[i] = parameteres.get(i);
						}  
						execute = CGLibUtils.execute(service, methodName, parameterTypes, parameters);
						
						/* Method method = service.getClass().getMethod(methodName, parameterTypes);  
						 execute = method.invoke(service, parameters);  */
                         
	               }
	               
					
					
	                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());  
	                out.writeObject(execute);
	                out.close();    
	                input.close();    
	            } catch (Exception e) {    
	            	e.printStackTrace();
	            } finally {    
	                if (socket != null) {    
	                    try {    
	                        socket.close();    
	                    } catch (Exception e) {    
	                        socket = null;    
	                    }    
	                }    
	            }   
	        }    
	    }

		

	
}
