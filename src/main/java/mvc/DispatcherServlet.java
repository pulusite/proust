package mvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangdong on 2018/6/13.
 */
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * 解析器前缀
     */
    private String ViewResolver_prefix="/";
    /**
     * 解析器后缀
     */
    private String ViewResolver_suffix=".jsp";
    /**
     * 指定扫描的包路径
     */
    String packagePath="com.controllers";
    /**
     * 将controller对象放入到此map中
     * 类上的RequestMapping注解的value作为key
     */
    Map<String,Object> controllerMap=new HashMap();
    /**
     * 类上的RequestMapping注解的value，加上方法上的RequestMapping注解的value，作为key
     */
    Map<String,Method> methodMap=new HashMap();
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String contextPath=request.getContextPath();
        String uri=request.getRequestURI();
        String url=uri.replace(contextPath, "");
//        StringBuffer url=request.getRequestURL();
        System.out.println(url);
        String classURL=url.substring(0, url.lastIndexOf("/"));
        Object obj=controllerMap.get(classURL);
        Method method=(Method)methodMap.get(url);
        try {
            ModelAndView mv=(ModelAndView)method.invoke(obj);
            String fullMV=ViewResolver_prefix+mv.getViewName()+ViewResolver_suffix;
            request.getRequestDispatcher(fullMV).forward(request, response);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }
    @Override
    public void init() throws ServletException {
        // TODO Auto-generated method stub
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource(packagePath.replace(".", "/"));
            File file=new File(url.getFile());
            File[] array=file.listFiles();
            for(File fileTemp:array){
                String fileName=fileTemp.getName();
                String fileNameShort=fileName.substring(0, fileName.indexOf("."));
                Class clazz=Class.forName(packagePath+"."+fileNameShort);
                if(clazz.isAnnotationPresent(Controller.class)){
                    Object obj=clazz.newInstance();
//                    Controller controller = (Controller) clazz.getAnnotation(Controller.class);
//                    String key = controller.value(); //必须先实例化才能获取注解信息
                    RequestMapping rm=(RequestMapping)clazz.getAnnotation(RequestMapping.class);
                    String classURL=rm.value();
                    controllerMap.put(classURL, obj);
                    Method[] methordArray=clazz.getMethods();
                    for(Method method:methordArray){
                        if(method.isAnnotationPresent(RequestMapping.class)){
                            RequestMapping methodRM=method.getAnnotation(RequestMapping.class);
                            String methodURL=methodRM.value();
                            String requestURL=classURL+methodURL;
                            System.out.println(requestURL);
                            methodMap.put(requestURL, method);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}