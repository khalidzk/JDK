[返回导航页](https://github.com/rambler-kieran/JDK)

# 代码分析

&emsp;&emsp;String全称：java.lang.String。

## 实现接口
1. Serializable：用于序列化
2. Comparable<String>：用于比较
3. CharSequence：是String的一个更通用的抽象。

## 属性
1. value[]

&emsp;&emsp;value中存放着String的实际内容。 此外，Java的char的长度是16bit，两个字节。

```
/** The value is used for character storage. */
private final char value[];
```

2. hash

&emsp;&emsp;hash是String得哈希值，默认为0。hash在调用hashCode()时计算， 因此不是final。

```
/** Cache the hash code for the string */
private int hash; // Default to 0
```

## 构造方法
1. String()

&emsp;&emsp;无参构造方法，得到的就是空的字符串。

```
public String() {
    this.value = "".value;
}
```

2. String(String original)

&emsp;&emsp;参数是String的，直接对value和hash赋值。

```
public String(String original) {
    this.value = original.value;
    this.hash = original.hash;
}
```

3. String(char value[])

&emsp;&emsp;这类方法进行越界检查，由于String是不可变的，还会复制数组。

```
public String(char value[]) {
    this.value = Arrays.copyOf(value, value.length);
}
public String(char value[], int offset, int count) {
    if (offset < 0) {
        throw new StringIndexOutOfBoundsException(offset);
    }
    if (count <= 0) {
        if (count < 0) {
            throw new StringIndexOutOfBoundsException(count);
        }
        if (offset <= value.length) {
            this.value = "".value;
            return;
        }
    }
    // Note: offset or count might be near -1>>>1.
    if (offset > value.length - count) {
        throw new StringIndexOutOfBoundsException(offset + count);
    }
    this.value = Arrays.copyOfRange(value, offset, offset+count);
}
```

4. String(int[] codePoints, int offset, int count)

&emsp;&emsp;int[]相关的，这里的参数是Unicode的codePoint，因为Unicode是4字节的，所以使用了int。

```
public String(int[] codePoints, int offset, int count);
```

5. String(byte ascii[], int hibyte, int offset, int count)

&emsp;&emsp;对于基本平面（BMP）的字符，只需要char即可。
对于辅助平面的字符，一个codePoint，需要两个char才能存下。

```
public String(byte ascii[], int hibyte, int offset, int count)
```