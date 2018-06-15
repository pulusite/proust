package rpc.core;

import rpc.simpleio.SocketConsumer;
import rpc.simpleio.SocketProducter;

public abstract class RpcFactory {

	public abstract RpcRefer getRpcRefer();
	public abstract RpcExport getRpcExport();
	
	public static RpcFactory instances1 = new RpcFactory(){
		@Override
		public  RpcRefer getRpcRefer() {
			return new SocketConsumer();
		}
		@Override
		public  RpcExport getRpcExport() {
			return new SocketProducter();
		} 
	}; 
	
	
	public static RpcFactory getInstanceByStrategy(String  strategy){
		switch (strategy) {
		case "simpeleIO":
			 return instances1;
		}
		return instances1;
	}
	
}
