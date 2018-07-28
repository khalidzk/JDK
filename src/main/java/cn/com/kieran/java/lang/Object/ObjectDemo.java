package cn.com.kieran.java.lang.Object;

/**
 * TODO
 *
 * @author zhengkai
 * @version V1.0
 * @since 2018-07-28 10:34
 */
public class ObjectDemo {
    public static void main(String[] args) {
        Object object = new Object();

        System.out.println(object.getClass());
        System.out.println(object.hashCode());

        Object object1 = new Object();

        System.out.println();
        System.out.println("--------------");
        System.out.println();

        System.out.println(object1.getClass());
        System.out.println(object1.hashCode());
    }
}
