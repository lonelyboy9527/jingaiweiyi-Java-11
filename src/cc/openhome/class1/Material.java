package cc.openhome.class1;

public class Material {
	private int data1 = 0;
	private int data2 = 0;
	
	private Object lock1 = new Object();
	private Object lock2 = new Object();
	
	/* 在这里想避免doSome()或是doOther()中，同时被两个以上线程执行 synchronized区块
	 * 注意data1和data2并不在同一个方法中，所以有个线程 执行doSome，另一个线程执行doOther
	 * 时，并不会引发共享存取问题，此时分别提供不同对象作为锁定来源。
	 * */
	public void doSome() {
		synchronized (lock1) {
			data1 ++;
		}
	}
	
	public void doOther() {
		synchronized (lock2) {
			data2 ++;
		}
	}
}
