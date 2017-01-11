package cc.openhome.class2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Clerk3 {
	private int product = -1; 
	private Lock lock = new ReentrantLock();
	private Condition producerSet = lock.newCondition(); // 拥有生产者等待集合
	private Condition consumerSet = lock.newCondition(); // 拥有消费者等待集合
	
	public void setProduct(int product) {
		try {
			lock.lock();
			while (this.product != -1) {
				try {
					producerSet.await(); // 至生产者等待集合等待
				} catch (InterruptedException e) {
					// TODO: handle exception
					throw new RuntimeException(e);
				}
			}
			this.product = product;
			this.product = product; 
			System.out.printf("生产者设定 (%d)\n", this.product);
			consumerSet.signal(); // 通知消费者等待集合中的消费者线程
			
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
					consumerSet.await(); // 至消费者等待集合等待
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			int  p = this.product; 
			System.out.printf("消费者取走 (%d)\n", this.product);
			this.product = -1; 
			producerSet.signal(); // 通知生产者等待集合中的生产者线程
			return p; 
		} finally {
			// TODO: handle finally clause
			lock.unlock();
		}
	}
}
