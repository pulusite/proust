package rpc.simpleio;

import rpc.core.RpcRefer;
import rpc.utils.FastJsonUtils;
import rpc.utils.InvokeEnity;
import org.springframework.util.CollectionUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.util.ArrayList;

public class SocketConsumer implements RpcRefer {
	 
	 @Override
		public <T> T refer(Class<T> interfaceClass, String host, int port) { 
		 return  (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),  new Class<?>[] {interfaceClass}, new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				Socket socket = null;  
				 ObjectInputStream input = null;
	            try {   
	                socket = new Socket(host, port);    
	                DataOutputStream out = new DataOutputStream(socket.getOutputStream());    
	                InvokeEnity entity = new InvokeEnity();
	                entity.setServiceName(interfaceClass.getName());
	                entity.setMethodName(method.getName());
	                ArrayList<String>  parameterTypes= new ArrayList<String>();
	                for (int i = 0; i < method.getParameterTypes().length; i++) {
	                	Class<?> class1 = method.getParameterTypes()[i];
	                	parameterTypes.add(class1.getName());
					}
	                entity.setParameterTypes(parameterTypes);
	                entity.setParameters(CollectionUtils.arrayToList(args));
	                
	                out.writeUTF(FastJsonUtils.toJSONString(entity).toString());  
	                input = new ObjectInputStream(socket.getInputStream());    
	                Object serviceInvoke = input.readObject(); 
	                return serviceInvoke;
	               
	            } catch (Exception e) {   
	            	e.printStackTrace();
	            } finally {  
	                if (socket != null) {  
	                    try {  
	                        socket.close();  
	                    } catch (IOException e) {  
	                        socket = null;      
	                    }  
	                }  
	                if (input != null) {  
	                    try {  
	                    	input.close();  
	                    } catch (IOException e) {  
	                    	input = null;      
	                    }  
	                }  
	            } 
				return null;
			}
		});  
		} 
}
