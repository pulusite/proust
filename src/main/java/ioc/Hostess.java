package ioc;

/**
 * Created by zhangdong on 2018/6/13.
 */
public class Hostess implements Master{

    private Dog dog;

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public void walkDog() {
        System.out.println("遛狗开始");
        dog.bark();
    }

    public void shopping() {
        System.out.println("购物");
    }

    @Override
    public String toString() {
        return "Hostess{" +
                ", dog=" + dog +
                '}';
    }
}
