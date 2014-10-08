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
		Method methods[] = c.getMethods();
		Constructor constructors[] = c.getConstructors();
		
		for(Method method : methods){
			System.out.println(method.toString());
		}
		for(Constructor constructor : constructors){
			System.out.println(constructor.toString());
		}
	}

}
