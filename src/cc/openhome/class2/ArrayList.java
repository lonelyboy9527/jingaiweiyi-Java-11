package cc.openhome.class2;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class ArrayList {
	private Lock lock = new ReentrantLock(); // 使用ReentrantLock
	private Object[] list;
	private int next;
	
	public ArrayList(int capacity) {
		// TODO Auto-generated constructor stub
		list = new Object[capacity];
	}
	public ArrayList() {
		// TODO Auto-generated constructor stub
		this(16);
	}
	public void add(Object o) {
		try {
			lock.lock(); // 进行锁定
			if (next == list.length) {
				list = Arrays.copyOf(list, list.length * 2);
			}
			list[next++] = o;
		} finally {
			// TODO: handle finally clause
			lock.unlock(); // 解除锁定
		}
	}
	public Object get(int index) {
		try {
			lock.lock();
			return list[index];
		} finally {
			// TODO: handle finally clause
			lock.unlock();
		}
	}
	public int size() {
		try {
			lock.lock();
			return next;
		} finally {
			lock.unlock();
		}
	}
}
