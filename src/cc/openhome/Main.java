package cc.openhome;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		exp1();
		exp2();
	}
	/* ********************11.2 并行API******************** */
	public static void exp2() {
		/* 使用Thread建立多线程程序，必须亲自处理 synchronized、对象锁定、wait()、
		 * notify()、notifyAll()等细节。如果需要的是线程池，读写锁等高级操作，
		 * 从JDK5之后提供了 java.util.concurrent包，可基于其中的API建立更稳固的并行应用程序。
		 * */
		MyClass2.exp1(); // 11.2.1 Lock、ReadWriteLock 与 Condition
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
//		MyClass1.exp3(); // 11.1.3 线程生命周期
//		MyClass1.exp4(); // 11.1.4 关于ThreadGoup
//		MyClass1.exp5(); // 11.1.5 synchronized与volatile
		MyClass1.exp6(); // 11.1.6 等待与通知
	}
}
