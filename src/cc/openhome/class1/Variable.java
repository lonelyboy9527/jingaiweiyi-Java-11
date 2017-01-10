package cc.openhome.class1;

public class Variable {
	static int i = 0, j = 0;
	public static void one() {
		i++;
		j++;
	}
	public static void two() {
		System.out.printf("i = %d, j = %d\n", i, j);
	}
}
