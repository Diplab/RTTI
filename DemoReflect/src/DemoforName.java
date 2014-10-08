

public class DemoforName {

	public static void main(String[] args){
		//create instance of "Class"
		Class<?> c = null;
		try{
			c=Class.forName("Foo");
		}catch(Exception e){
			e.printStackTrace();
		}
 
		//create instance of "Foo"
		Foo f = null;
 
		try {
			f = (Foo) c.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}	
 
		f.print();
	}

}
