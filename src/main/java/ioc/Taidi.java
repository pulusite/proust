package ioc;

/**
 * Created by zhangdong on 2018/6/13.
 */
public class Taidi implements Dog{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void bark() {
        System.out.println("泰迪叫");
    }

    @Override
    public String toString() {
        return "Taidi{" +
                "name='" + name + '\'' +
                '}';
    }
}
