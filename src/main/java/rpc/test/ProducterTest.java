package rpc.test;

import rpc.core.RpcExport;
import rpc.core.RpcFactory;
import rpc.service.UserService;
import rpc.serviceimpl.UserServiceImpl;
import rpc.utils.Constants;

public class ProducterTest {

	  public static void main(String[] args) {
			RpcFactory rpc = RpcFactory.getInstanceByStrategy("simpleIO");
			RpcExport rpcExport = rpc.getRpcExport();
			UserService service = new UserServiceImpl();
			rpcExport.export(service, Constants.PORT);
	}

}
