

class TestClass {
   static {
       System.out.println("���O�Q���J");
   }
}


public class DemoLoadClass {
	   public static void main(String args[]) {
	       TestClass test = null;  //class���|���J�A�]�����|��ܡu���O�Q���J�v
	       System.out.println("�ŧi TestClass �ѦҦW��");
	       test = new TestClass(); //class�Q���J�A��ܡu���O�Q���J�v
	       System.out.println("�ͦ� TestClass ���");
	   }

}
