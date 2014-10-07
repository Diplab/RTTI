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