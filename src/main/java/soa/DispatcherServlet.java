package soa;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.context.ApplicationContext;
import soa.schema.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangdong on 2018/6/15.
 *
 * 这个是soa框架中给生产者接收请求用的servlet，这个必须是采用http协议才能掉得到
 *
 * @author Administrator
 *
 */
public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 7394893382457783784L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            JSONObject requestparam = httpProcess(req, resp);
            String serviceId = requestparam.getString("serviceId");
            String methodName = requestparam.getString("methodName");
            JSONArray paramTypes = requestparam.getJSONArray("paramTypes");
            JSONArray methodParamJa = requestparam.getJSONArray("methodParams");
            //
            Object[] objs = null;
            if (methodParamJa != null) {
                objs = new Object[methodParamJa.size()];
                int i = 0;
                for (Object o : methodParamJa) {
                    objs[i++] = o;
                }
            }
            // 获取spring上下文
            ApplicationContext application = Service.getApplication();
            Object serviceBean = application.getBean(serviceId);
            // 需要考虑重载
            Method method = getMethod(serviceBean, methodName, paramTypes);
            if (method != null) {
                // 反射调用
                Object result = method.invoke(serviceBean, objs);
                resp.getWriter().write(result.toString());
            } else {
                resp.getWriter().write("no such method!!!!");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private Method getMethod(Object bean, String methodName, JSONArray paramType) {
        Method[] methods = bean.getClass().getMethods();
        List<Method> retmMethod = new ArrayList<Method>();
        for (Method method : methods) {
            // 找到相同方法名的方法
            if (methodName.trim().equals(method.getName())) {
                retmMethod.add(method);
            }
        }
        if (retmMethod.size() == 0) {
            return retmMethod.get(0);
        }

        boolean isSameSize = false;
        boolean isSameType = false;
        for (Method method : retmMethod) {
            Class<?>[] typs = method.getParameterTypes();
            if (typs.length == paramType.size()) {
                isSameSize = true;
            }
            if (isSameSize) {
                continue;
            }
            for (int i = 0; i < typs.length; i++) {
                if (typs[i].toString().contains(paramType.getString(i))) {
                    isSameType = true;
                } else {
                    isSameType = false;
                    break;
                }
            }
            if (isSameType) {
                return method;
            }
        }
        return null;
    }

    // 获取请求参数
    public static JSONObject httpProcess(HttpServletRequest req,
                                         HttpServletResponse resp) throws IOException {
        StringBuffer sb = new StringBuffer();
        InputStream is = req.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is,
                "utf-8"));
        String s = "";
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        if (sb.toString().length() <= 0) {
            return null;
        }
        return JSONObject.parseObject(sb.toString());
    }

}