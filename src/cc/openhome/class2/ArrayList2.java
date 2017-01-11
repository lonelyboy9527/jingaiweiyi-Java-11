package cc.openhome.class2;

import java.util.Arrays;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ArrayList2 {
	private ReadWriteLock lock = new ReentrantReadWriteLock(); // 使用ReadWriteLock
	
	private Object[] list;
	private int next;
	
	public ArrayList2(int capacity) {
		// TODO Auto-generated constructor stub
		list = new Object[capacity];
	}
	public ArrayList2() {
		// TODO Auto-generated constructor stub
		this(16);
	}
	public void add(Object o) {
		try {
			lock.writeLock().lock();; // 取得写入锁定
			if (next == list.length) {
				list = Arrays.copyOf(list, list.length * 2);
			}
			list[next++] = o;
		} finally {
			// TODO: handle finally clause
			lock.writeLock().unlock(); // 解除写入锁定
		}
	}
	public Object get(int index) {
		try {
			lock.readLock().lock(); // 取得读取锁定
			return list[index];
		} finally {
			// TODO: handle finally clause
			lock.readLock().unlock(); // 解除读取锁定
		}
	}
	public int size() {
		try {
			lock.readLock().lock();
			return next;
		} finally {
			lock.readLock().unlock();
		}
	}
}
