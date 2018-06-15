package rpc.serviceimpl;

import rpc.entity.User;
import rpc.service.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public User getUser(String name) { 
		User user = new User();
		user.setName(name);
		user.setPassword(name +"1213212");
		return user;
	}

	 

}
