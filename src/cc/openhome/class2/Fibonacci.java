package cc.openhome.class2;

import java.util.concurrent.RecursiveTask;

public class Fibonacci extends RecursiveTask<Long> {

	final long n;
	public Fibonacci(long n) {
		this.n = n;
	}
	
	@Override
	protected Long compute() { // 操作compute()方法
		// TODO Auto-generated method stub
		if (n <= 10) { // 小于10就不分解了，直接返回运算
			return Fibonacci.fibonacci(n);
		}
		
		Fibonacci f1 = new Fibonacci(n - 1); // 分解出 n-1子任务
		f1.fork(); // 请 ForkJoinPool 分配线程来执行这个子任务
		Fibonacci f2 = new Fibonacci(n - 2); // 分解出 n-2子任务
		return f2.compute() + f1.join(); // 直接执行f2这个子任务，取得f1子任务的结果
	}
	
	public static long fibonacci(long n) {
		if (n <= 1) {
			return n;
		} else {
			return fibonacci(n -1) + fibonacci(n - 2);
		}
	}

}
