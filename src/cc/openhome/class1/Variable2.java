package cc.openhome.class1;

public class Variable2 {
	static int i = 0, j = 0;
	public synchronized static void one() {
		i++;
		j++;
	}
	public synchronized static void two() {
		System.out.printf("i = %d, j = %d\n", i, j);
	}
}
