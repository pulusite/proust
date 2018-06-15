package rpc.utils;

import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;

public class CGLibUtils {
	
	public static  Object execute(Object targetObject,String methodName,Class<?>[] parameterTypes, Object[] parameters){
		try {
			FastClass serviceFastClass = FastClass.create(targetObject.getClass());
			FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
			return  serviceFastMethod.invoke(targetObject, parameters);
		} catch (Exception e) {
			return null ;
		}
	}

}
