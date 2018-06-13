package mvc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by zhangdong on 2018/6/13.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    String value() default "";
}