
import java.lang.reflect.Method;
 
class Foo {
	public void print() {
		System.out.println("abc");
	}
}

public class DemoGetClassName {

	public static void main(String[] args){
		Foo f = new Foo();
		System.out.println(f.getClass().getName());			
	}
}
