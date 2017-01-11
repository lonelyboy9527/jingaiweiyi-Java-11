package cc.openhome.class2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Resource2 {
	private Lock lock = new ReentrantLock();
	private String name;
	private int resource;
	
	public Resource2(String name, int resource) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.resource = resource;
	}
	
	public String getName() {
		return name;
	}
	
	public int doSome() {
		return ++resource;
	}
	
	public void cooperate(Resource2 resource) {
		while (true) {
			boolean myLock = this.lock.tryLock(); // 尝试取得目前Resource的Lock锁定 
			boolean resourceLock = resource.lock.tryLock(); // 尝试取得被传入的Resource的Lock锁定
			try {
				if (myLock && resourceLock) { // 如果两个Resource的Lock都取得锁定，才执行资源整合。
					resource.doSome();
					System.out.printf("%s 整合 %s的资源\n", this.name, resource.getName());
					break; // 资源整合成功，退出循环
				}
			} finally {
				// TODO: handle finally clause
				if (myLock) { // 如果当前这个Resource的Lock取得锁定成功，就解除锁定。
					this.lock.unlock();
				}
				
				if (resourceLock) { // 如果被传入的Resource的Lock取得锁定成功，就解除锁定。
					resource.lock.unlock();
				}
			}
		}
	}
}
