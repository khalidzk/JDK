[返回导航页](https://github.com/rambler-kieran/JDK)

# 代码分析

Object全称：java.lang.Object。     
Object类是类层次结构的根，也就是java中所有类的祖先类，所有类默认继承Object类，所以Object类中的方法有必要研究一下。 

## 1、registerNatives()

```
private static native void registerNatives();
```
registerNatives含义是本地注册的意思。分析：该类有static，为静态方法，同时有native修饰。native表示该方法的实现java本身并没有完成，而是有c/c++来完成，形成.dll文件。但是我们注意到，这里的{}里并没有具体的方法体，而且这里是由private修饰，那么应该如何执行呢？其实后面还一个静态代码块。


```
static {
    registerNatives();
}
```
静态代码块就是一个类在初始化过程中必定会执行的内容，所以在这里执行。 