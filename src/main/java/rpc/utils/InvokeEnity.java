package rpc.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InvokeEnity  implements Serializable{

	   /* 
    {
    	"serviceName":"rpc.service.UserService",
    	"methodName":"getUser",
    	"parameterTypes":["java.lang.String"],
    	"parameters":["baoyou"]
    	
    }
    */
	
	 private String serviceName;
	 private String methodName;
	 private  List<String> parameterTypes;
	 private  List<String> parameters;
	 
	 
	 
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public List<String> getParameterTypes() {
		return parameterTypes;
	}
	public void setParameterTypes(List<String> parameterTypes) {
		this.parameterTypes = parameterTypes;
	}
	public List<String> getParameters() {
		return parameters;
	}
	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}
	
	public static InvokeEnity transefer(String req){
		InvokeEnity entity = FastJsonUtils.toBean(req,InvokeEnity.class);
		return entity;
	}
	
	
	public static void main(String[] args) {
		InvokeEnity entity = new InvokeEnity();
		entity.setMethodName("getUser");
		entity.setServiceName("rpc.service.UserService");
		entity.setParameters(new ArrayList<String>(){{add("baoyou");}});
		entity.setParameterTypes(new ArrayList<String>(){{add("java.lang.String");}});
		 String req = FastJsonUtils.toJSONString(entity).toString();
		//{"methodName":"getUser","parameterTypes":["java.lang.String"],"parameters":["baoyou"],"serviceName":"rpc.service.UserService"}
		InvokeEnity transefer = InvokeEnity.transefer(req);
		System.out.println(FastJsonUtils.toJSONString(transefer));
		System.out.println(transefer.getServiceName());
	}
}
