

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
