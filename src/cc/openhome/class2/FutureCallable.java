package cc.openhome.class2;

public class FutureCallable {
	public static long fibonacci(long n) {
		if (n <= 1) {
			return n;
		} else {
			 return fibonacci(n-1) + fibonacci(n-2);
		}
	}
}
