package cc.openhome;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		exp1();
	}
	/* ********************11.1 线程******************** */
	public static void exp1() {
		/* 到目前为止介绍到各种范例都是单线程操作，也就是启动程序从main程序进入点
		 * 开始到结束只有一个流程。
		 * 
		 * 有时候，需要程序可以拥有多个流程，也就是所谓到多线程程序(Multi-thread)。
		 * */
//		MyClass1.exp1(); // 11.1.1 线程简介
//		MyClass1.exp2(); // 11.1.2 Thread 与Runnable
		MyClass1.exp3(); // 11.1.3 线程生命周期
	}
}
