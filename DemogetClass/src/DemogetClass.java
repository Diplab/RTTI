
public class DemogetClass {

	public static void main(String[] args) {
	       String name = "godleon";
	       Class stringClass = name.getClass();

	       System.out.println("���O�W�١G" + stringClass.getName());
	       System.out.println("�O�_�������G" + stringClass.isInterface());
	       System.out.println("�O�_���򥻫��A�G" + stringClass.isPrimitive());
	       System.out.println("�O�_���}�C����G" + stringClass.isArray());
	       System.out.println("�����O�W�١G" + stringClass.getSuperclass().getName());
	}

}
