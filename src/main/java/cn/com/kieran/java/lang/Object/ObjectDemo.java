package cn.com.kieran.java.lang.Object;

/**
 * TODO
 *
 * @author zhengkai
 * @version V1.0
 * @since 2018-07-28 10:34
 */
public class ObjectDemo {
    public static void main(String[] args) throws Exception{
        ObjectDemo object = new ObjectDemo();
        Class<? extends ObjectDemo> clazz = object.getClass();
        System.out.println(clazz);
        System.out.println(object.hashCode());

        ObjectDemo object1 = new ObjectDemo();
        Class<? extends ObjectDemo> clazz1 = object.getClass();
        System.out.println();
        System.out.println("--------------");
        System.out.println();

        System.out.println(clazz1);
        System.out.println(object1.hashCode());

        Apple app=new Apple();
        app.setName("红苹果");
        Apple app1=app;
        Apple app2= app.clone();
        System.out.println(app);
        System.out.println(app1);
        System.out.println(app2);
        System.out.println(app2.getName());
    }
}


