package mvc;

/**
 * Created by zhangdong on 2018/6/13.
 */

@Controller
@RequestMapping("/test")
public class Mvc_ {
    @RequestMapping("/test")
    public ModelAndView test(){
        System.out.println("111");
        ModelAndView mv=new ModelAndView();
        mv.setViewName("test2");
        return mv;
    }
}
