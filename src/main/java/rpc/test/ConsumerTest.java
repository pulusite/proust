package rpc.test;

import rpc.core.RpcFactory;
import rpc.core.RpcRefer;
import rpc.entity.User;
import rpc.service.UserService;
import rpc.utils.Constants;
import rpc.utils.FastJsonUtils;

public class ConsumerTest {

	 public static void main(String[] args) {
		 RpcFactory rpc = RpcFactory.getInstanceByStrategy("simpleIO"); 
			RpcRefer rpcRefer = rpc.getRpcRefer();
			UserService userService = rpcRefer.refer(UserService.class, Constants.IP_ADDR, Constants.PORT);
			for (int i = 0; i < 10; i++) {
				User user = userService.getUser("baoyou");
				System.out.println(FastJsonUtils.toJSONString(user).toString());
			}
			 
	}

}
