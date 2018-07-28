[返回导航页](https://github.com/rambler-kieran/JDK)

# 代码分析

&emsp;&emsp;Object全称：java.lang.Object。     
&emsp;&emsp;Object类是类层次结构的根，也就是java中所有类的祖先类，所有类默认继承Object类，所以Object类中的方法有必要研究一下。 

方法列表：
```
public final native Class<?> getClass();
public native int hashCode();
public boolean equals(Object obj);
protected native Object clone();
public String toString();
public final native void notify();
public final native void notifyAll();
public final native void wait(long timeout);
public final void wait(long timeout, int nanos);
public final void wait();
protected void finalize();
```

## 1、registerNatives()

```
private static native void registerNatives();
```
&emsp;&emsp;registerNatives含义是本地注册的意思。
分析：该类有static，为静态方法，同时有native修饰。native表示该方法的实现java本身并没有完成，
而是有c/c++来完成，形成.dll文件。但是我们注意到，这里的{}里并没有具体的方法体，而且这里是由private修饰，
那么应该如何执行呢？其实后面还一个静态代码块。


```
static {
    registerNatives();
}
```
&emsp;&emsp;静态代码块就是一个类在初始化过程中必定会执行的内容，所以在这里执行。 

## 2、getClass()

在Object类中，getClass()方法被关键字native修饰，表明其实现使用了JNI(java 
native interface)。
此方法与java反射机制有关，可以得到类的详细信息(类名，类方法等)。

```
public final native Class<?> getClass();
```
&emsp;&emsp;getClass就是获得类的意思，由注释可以知道，这个方法主要是获得该类的完整名称。


```java
public class ObjectDemo {
    public static void main(String[] args) {
        ObjectDemo object = new ObjectDemo();
        Class<? extends ObjectDemo> clazz = object.getClass();
        System.out.println(clazz);
        System.out.println(object.hashCode());

        System.out.println();
        System.out.println("--------------");
        System.out.println();

        ObjectDemo object1 = new ObjectDemo();
        Class<? extends ObjectDemo> clazz1 = object.getClass();
        System.out.println(clazz1);
        System.out.println(object1.hashCode());
    }
}
```

&emsp;&emsp;在这里我新建了一个ObjectTest类，因为Object是所有类的祖父类，所以该类也拥有getClass方法，
我们可以直接查看输出：
```
class cn.com.kieran.java.lang.Object.ObjectDemo
1627674070

--------------

class cn.com.kieran.java.lang.Object.ObjectDemo
1360875712
```

## 3、hashCode()

&emsp;&emsp;从方法声明可以得知，hashCode()返回了一个int值，此int值便是调用此方法的实例的哈希值。
对于含有容器类型的程序设计语言来说，哈希值至关重要。 

&emsp;&emsp;在java中，这样的容器数据类型有HashMap，HashTable，HashSet。当插入一条记录时，
如何保证插入的数据是否已经存在，如何快速找到需要的数据成为最重要的问题。三种数据类型中都有形
如 <K,V>的数据结构，在HashMap与HashTable中是Entry<K,V>[] table，HashSet中则是HashMap<E,Object> map，
其中的K值便通常是类实例调用hashCode()方法产生的int值，称为散列值。
 
&emsp;&emsp;getClass中提到，native修饰的方法是本地方法，其实现与JVM的实现有关，
hashCode()生的值同样与虚拟机有关，通常来说，与对象的存储地址有关。

&emsp;&emsp;虽然可以用hashCode来表示对象，但不能仅凭hashcode相同来证明两个对象是一致的。
所以在散列表的设计中需要涉及到解决冲突算法。

## 4、equals()

```
public boolean equals(Object obj) {
    return (this == obj);
}
```
&emsp;&emsp;从Obejct中equals的实现可以得知返回的是传入对象与当前对象引用地址是否一致，
对于java基本类型(String,Integer等)，都对equals()进行了重写，保证了**比较引用地址的同时还比较存储的数值**。
所以一般编写自己的类时需要用到equals()时，应该**重写方法**，否则可能出错。

## 5、clone()

&emsp;&emsp;同样是本地方法，返回一个Object，顾名思义，这是一种克隆对象的方法，返回的是当前对象的克隆。
&emsp;&emsp;这里我们知道创建对象也可以使用clone()方法。我们知道操作符“=”同样可以将新对象指向同一对象引用
&emsp;&emsp;测试如下代码:
```
Apple app=new Apple();
app.setName("红苹果");
Apple app1=app;
Apple app2= app.clone();
System.out.println(app);
System.out.println(app1);
System.out.println(app2);
System.out.println(app2.getName());
```

&emsp;&emsp;输出如下:

```
cn.com.kieran.java.lang.Object.Apple@60e53b93
cn.com.kieran.java.lang.Object.Apple@60e53b93
cn.com.kieran.java.lang.Object.Apple@5e2de80c
红苹果
```

&emsp;&emsp;可见操作符“=”仅仅将同一对象的引用指向新对象“app1”,堆区没有创建新对象，
而clone()直接在堆区创建了新对象。

## 6、toString()

```
public String toString() {
    return getClass().getName() + "@" + Integer.toHexString(hashCode());
}

```

&emsp;&emsp;看Object中的实现 ,返回“类名@hashcode()值的16进制表示”，hashcode()的生成与地址有关，
所以可以一定程度上反映当前对象再内存上的存储状态。

&emsp;&emsp;通常我们使用自己创建的类的toString方法时，会将需要了解的类的信息打印出来，而不仅仅是类名和hashcode。

## 7、wait()

```
public final native void wait(long timeout);
public final void wait(long timeout, int nanos);
public final void wait();
```

&emsp;&emsp;某对象调用wait()方法后，当前线程阻塞(此线程必须拥有该对象的monitor)，释放锁，等待被唤醒。
1. wait():当前线程阻塞，直到被唤醒。
2. wait(long time):当前线程阻塞，直到其他线程调用此对象的 notify()方法或 notifyAll()或超过设定时间
3. wait(long timeout, int nanos):当前线程阻塞导致当前的线程等待，直到其他线程调用此对象的notify()方法
或 notifyAll()方法，或者其他某个线程中断当前线程，或者已超过某个实际时间量。

## 8、notify()

```
public final native void notify();
```

此方法在Object中声明为final，无法重写，由JVM实现。是多线程编程中重要的方法。
唤醒在此对象监视器上等待的单个线程。

## 9、notifyAll()

```
public final native void notifyAll();
```

唤醒在此对象监视器上等待的所有线程。

## 10、finalize()

```
protected void finalize() throws Throwable { }
```

垃圾回收器准备释放内存的时候，会先调用finalize()。