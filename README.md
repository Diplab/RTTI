Run-Time Type Information
======
## Outline

- [前言](#前言)
- [傳統的RTTI機制](#傳統的RTTI機制)
	+ [向上轉型或向下轉型](#向上轉型或向下轉型)
	+ [Class 物件](#Class 物件)
	+ [使用instanceof](#使用instanceof)
- [Reflection機制](#Reflection機制)
	+ [RTTI機制與Reflection機制差異](#RTTI機制與Reflection機制差異)
- [參考文獻](#參考文獻)

## 前言
執行期型別資訊(Run-Time Type Information, RTTI)機制，
讓你得以在程式執行期間找出、並使用型別資訊。

包含兩種形式：
- [傳統的RTTI機制](#傳統的RTTI機制)，他假設你在編譯期和執行期擁有所有型別資訊。
- [Reflection機制](#Reflection機制)，允許你在執行期間找出和class相關的資訊。

## 傳統的RTTI機制

###向上轉型或向下轉型
這是RTTI最基本的形式，所有轉型動作都會於執行期檢查正確性。
向上轉型(Upcasting)是安全的，
但向下轉型(Downcasting)卻是有風險的，編譯器不會讓你向下轉型至實際上不是該型別之subclass的型別，
轉型錯誤時會擲出ClassCastException。

```java
import java.lang.reflect.Array;
import java.util.*;

abstract class Shape {
  void draw() {
    System.out.println(this + ".draw()");
  } // draw()
  
  abstract public String toString() ;
}

class Circle extends Shape {
  public String toString() { return "Circle"; }
}

class Square extends Shape {
  public String toString() { return "Square"; }
}

class Triangle extends Shape {
  public String toString() { return "Triangle"; }
}

public class DemoPolymorphismAndUpcasting {
	  public static void main(String[] args) {
		    ArrayList s = new ArrayList();
		    s.add(new Circle());
		    s.add(new Square());
		    s.add(new Triangle());
		    Iterator e = s.iterator();
		    while(e.hasNext())
		      ((Shape)e.next()).draw();
		  }
}
```

### Class 物件
為了妥善使用有限的資源，Java 在真正需要使用到 class 的時候才會將其載入，
而載入 Class 的工作是由 Class Loader(類別載入器) 所負責。
當每一個 class 被載入時，JVM 就會為其自動產生一個 Class object。

每一個 object 都可以透過 getClass() 的方式取得 Class object，以下為一個簡單的範例：

```java

public class DemogetClass {

	public static void main(String[] args) {
	       String name = "godleon";
	       Class stringClass = name.getClass();

	       System.out.println("類別名稱：" + stringClass.getName());
	       System.out.println("是否為介面：" + stringClass.isInterface());
	       System.out.println("是否為基本型態：" + stringClass.isPrimitive());
	       System.out.println("是否為陣列物件：" + stringClass.isArray());
	       System.out.println("父類別名稱：" + stringClass.getSuperclass().getName());
	}

}

```
Java允許我們從多種管道為一個class生成對應的Class object。整理如下圖：

![Class_object.png](img/Class_object.png)


而 Java 只有在真正要用到 class 的時候才會將其載入，而真正用到的時候是指以下情況：
- 使用 class 生成 object 時
- 使用者指定要載入 class (利用 Class.forName() 或是 ClassLoader.loadClass())

若僅是宣告並不會載入 class，以下用一段程式碼來測試：
```java

class TestClass {
   static {
       System.out.println("類別被載入");
   }
}

public class DemoLoadClass {
	   public static void main(String args[]) {
	       TestClass test = null;  //class不會載入，因此不會顯示「類別被載入」
	       System.out.println("宣告 TestClass 參考名稱");
	       test = new TestClass(); //class被載入，顯示「類別被載入」
	       System.out.println("生成 TestClass 實例");
	   }
}

```

此外，由於 Java 支援 Run-Time Type Identification(RTTI，執行時期型別辨識)，因此 Class 的訊息在編譯時期就會被加入 .class 檔案中；
而執行時，JVM 則會在使用某 class 時，會先檢查相對應的 Class object 是否已經載入，如果沒有載入，則會尋找相對應的 .class 檔案載入。
而一個 class 在 JVM 中只會有一個 Class object，所以每個 object 都可以透過 getClass() 或是 .class 屬性取得 Class object。
之前有說過，在 Java 中任何東西皆為 object，因此 Array 也不例外，甚至如 primitive type、關鍵字 void 都有相對應的 Class object，以下用一段程式來說明：
```java
public class ClassDemo2 {
   public static void main(String args[]) {
       System.out.println(boolean.class);  //boolean
       System.out.println(void.class); //void

       int[] intAry = new int[10];
       System.out.println(intAry.getClass().toString());   //class [I

       double[] dblAry = new double[10];
       System.out.println(dblAry.getClass().toString());   //class [D
   }
}
```

### 使用instanceof
Java還有第三種RTTI形式，那就是關鍵字instanceof，
他能夠告訴你某個物件是否為某特定型別的一個實體(instance)。
他會回傳一個boolean值，所以你可以採用問句型式運用之，例如：

```
if ( x instanceof Dog )
	((Dog) x).bark() ;
```

上述if述句會在x被轉型為Dog之前先檢查物件x是否屬於class Dog。
當你手上沒有其他資訊可以告訴你物件型別時，在向下轉型之前先運用instanceof進行檢查，是很重要的一件事
否則你可能會收到ClassCastException。



## Reflection機制
Reflection在Java指的是我們可以於執行期載入、探知、使用編譯期間完全未知的classes。
換句話說，Java程式可以載入一個執行期才得知名稱的class，
獲悉其完整構造（但不包括 methods定義），並生成其物件實體、或對其 fields 設值、或喚起其 methods。
。這種「看透 class」的能力（the ability of the program to examine itself）被稱為 introspection(內省)。

```java
import java.lang.reflect.*;
 
class pet{
	private String name;
	public pet(String name){
		this.name = name;
	}	
	public void ShowName(){
		System.out.println(name);
	}
}
 
class dog extends pet{
	public dog(String name){
		super(name);
	}
}
 
class cat extends pet{
	public cat(String name){
		super(name);
	}
}
 
class Ted extends cat{
	public Ted(String name){
		super(name);
	}
	
	public void move(){
		System.out.println("Move");
	}
	
	public void miue(){
		System.out.println("miue");
	}
}

public class DemoReflect {

	public static void main(String[] args) throws Exception{
		Class<?> c = Class.forName("Ted");
		Method []methods = c.getMethods();
		Constructor []constructors = c.getConstructors();
		
		for(Method method : methods){
			System.out.println(method.toString());
		}
		for(Constructor constructor : constructors){
			System.out.println(constructor.toString());
		}
	}

}
```

### RTTI機制與Reflection機制差異
- 使用RTTI時，編譯器在編譯期即開啟並檢查.class檔，換句話說你可以採用一般方式呼叫物件的所有函式。
- 但如果採用reflection機制，編譯期並不會取用.class檔，他會由執行環境加以開啟和檢驗。


## 參考文獻

- [Java 學習筆記 (10) - Reflection](http://godleon.blogspot.tw/2007/09/class-class-java-class-class-jvm-class.html)
- [Java Reflection](http://jjhou.boolan.com/javatwo-2004-reflection.pdf)
- [Java RTTI and Reflection](http://www.cnblogs.com/fxjwind/p/3430178.html)
- [Java with RTTI](http://noveltypioneer.blogspot.tw/2014/05/java-with-rtti.html)







