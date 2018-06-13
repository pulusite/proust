package ioc;

/**
 * Created by zhangdong on 2018/6/13.
 */
public class Hostess implements Master{

    private Dog dog;

    public void walkDog() {
        System.out.println("遛狗");
    }

    @Override
    public String toString() {
        return "Hostess{" +
                ", dog=" + dog +
                '}';
    }
}
