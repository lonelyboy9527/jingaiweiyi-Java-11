package cc.openhome.class2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Clerk2 {
	private int product = -1; 
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition(); // 建立Condition对象
	
	public void setProduct(int product) {
		try {
			lock.lock();
			while (this.product != -1) {
				try {
					condition.await(); // 用Condition的await()取代Object的 wait()
				} catch (InterruptedException e) {
					// TODO: handle exception
					throw new RuntimeException(e);
				}
			}
			this.product = product; // 店员收货
			System.out.printf("生产者设定 (%d)\n", this.product);
			condition.signal(); // 用Condition的signal()取代Object的 notify()
			
		} finally {
			// TODO: handle finally clause
			lock.unlock();
		}
	}
	
	public int getProduct() {
		try {
			lock.lock();
			while (this.product == -1) {
				try {
					condition.await();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			int  p = this.product; 
			System.out.printf("消费者取走 (%d)\n", this.product);
			this.product = -1; 
			condition.signal();
			return p; 
		} finally {
			// TODO: handle finally clause
			lock.unlock();
		}
	}
}
