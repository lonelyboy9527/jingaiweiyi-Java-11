package cc.openhome;

import cc.openhome.class2.Resource2;

import cc.openhome.class2.ArrayList;
import cc.openhome.class2.ArrayList2;
import cc.openhome.class2.Clerk2;
import cc.openhome.class2.Consumer2;
import cc.openhome.class2.Producer2;
import cc.openhome.class2.Clerk3;
public class MyClass2 {
	// Lock、ReadWriteLock 与 Condition
	public static void exp1() {
		/* synchronized 要求线程必须取得对象锁定，才可执行所标示的区块范围
		 * 然而使用synchronized 有许多限制，未取得锁定的线程会直接被阻断。
		 * 
		 * 如果你希望的功能是线程可尝试取得锁定，无法取得锁定时就先做其他事，
		 * 直接使用synchronized 必须通过一些设计才可完成这个需求，
		 * 若要搭配 waite()、notify()、notifyAll()等方法，在设计上则更为复杂。
		 * 
		 * 
		 * java.util.concurrent.locks包中提供Lock、ReadWrite、Condition接口
		 * 以及相关操作类，可以提供类似synchronized、wait()、notify()、notifyAll()的作用，以及更多高级的功能。
		 * 
		 * */
		
		/* <1>.使用Lock*/
//		Lock();
		
		/* <2>.使用ReadWriteLock*/
//		ReadWriteLock();
		
		/* <3>使用Condition*/
		Condition();
	}
	public static void Lock() {
		/* <1>.使用Lock
		 * 
		 * Lock接口主要操作类之一为ReentrantLock，可以达到 synchronized的作用，
		 * 也提供额外的功能。
		 * 
		 * 它控制对某种共享资源的独占。也就是说，同一时间内只有一个线程可以获取这个锁并占用资源。
		 * 其他线程想要获取锁，必须等待这个线程释放锁。在Java实现中的ReentrantLock就是这样的锁。
		 * 
		 * 另外一种锁，它可以允许多个线程读取资源，但是只能允许一个线程写入资源，ReadWriteLock就是这样一种特殊的锁，简称读写锁。
		 * 
		 * 例子：使用ReentrantLock 改写 6.2.5节中的ArrayList 为线程安全的类
		 * */
		ArrayList arrayList = new ArrayList(10);
		/* ReentranLock顾名思义，如果已经有线程取得Lock对象锁定
		 * 尝试再次锁定同一Lock对象是可以的。想要锁定Lock对象，可以调用其lock()方法，
		 * 只有取得Lock对象锁定的线程，才可以继续往后执行程序代码，要解除锁定，可以调用Lock对象的unlock()。
		 * */
		
		/* Lock接口还定义了 tryLcok()方法，如果线程调用 tryLock()可以取得锁定，则会返回true，
		 * 若无法取得锁定并不会发生阻断，而是返回 false。
		 * 
		 * 如果调用的时候能够获取锁，那么就获取锁并且返回true，
		 * 如果当前的锁无法获取到，那么这个方法会立刻返回false
		 * 
		 * 例子：解决11.1.5中的 DeadLockDemo的死结问题
		 * */
		Resource2();
		/* 11.1.5是因为线程无法同时取得两个Resource的锁定而阻断，
		 * 
		 * 改写后的cooperate会在while循环中，使用目前Resource的Lock的
		 * tryLock()尝试取得锁定，以及被传入Resource的Lock的tryLock()尝试取得锁定，
		 * 
		 * 只有在两次tryLock()返回值都是true，也就是两个Resource都取得锁定，才进行资源整合，
		 * 再离开while循环，无论哪个tryLock()成功，都要在finally中测试并解除锁定。
		 * */
	}
	public static void Resource2() {
		System.out.println("Resource2 -> 解决死结");
		final Resource2 resource = new Resource2("哈哈哈", 10);
		final Resource2 resource2 = new Resource2("嘻嘻嘻", 20);
		Thread t1 = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int i = 0; i < 10; i++) {
					resource.cooperate(resource2);
				}
			}
		};
		
		Thread t2 = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (int i = 0; i < 10; i++) {
					resource2.cooperate(resource);
				}
			}
		};
		t1.start();
		t2.start();
	}
	public static void ReadWriteLock() {
		/* <2>.使用ReadWriteLock
		 * 
		 * 前面一个例子设计了线程安全的 ArrayList，如果两个线程都想调用 get()与size()方法，
		 * 由于锁定的关系，其中，一个线程只能等待另外一个线程解除锁定，无法两个线程同时调用
		 * get()与size()方法。
		 * 
		 * 然而，这两个方法都只是读取对象状态，并没有变更对象状态，
		 * 如果读取操作，可允许线程同时并行的话，那对读取效率将会有所改善。
		 * 
		 * ReadWriteLock接口定义了读取锁定与写入锁定对行为，
		 * 可以使用readLock()、writeLock()方法返回Lock操作对象。
		 * 
		 * ReentrantReadWriteLock是ReadWriteLock接口主要操作类，
		 * readLock方法会返回ReentrantReadWriteLock.readLock实例，调用其Lock()方法时，若没有任何写入锁定时，可以取的读取锁定。
		 * writeLock方法会返回ReentrantReadWriteLock.writeLock实例，调用其Lock()方法时，若没有任何读取或者写入锁定时，才可以取得写入锁定。
		 * 
		 * 例子：使用ReadWriteLock改写前面对ArrayList，改进读取效率
		 * */
		ArrayList2 arrayList2 = new ArrayList2();
		
		/* 若有线程调用 add()方法打算进行写入操作时，先取得写入锁定，
		 * 这样若有其他线程打算再次取得写入锁定或取得读取锁定，都必须等待此次写入锁定解除。
		 * 
		 * 若有线程调用 get()方法打算进行读取操作时，先取得读取锁定，记得要在finally中解除写入锁定。
		 * 这样若有其他线程后续也可再取得读取锁定，若有线程打算取得写入锁定，必须等待所有读取锁定解除，记得要在finally中解除读取锁定。
		 * 
		 * 这样设计之后，若线程都只是在调用get()或size()方法，都不会因等待锁定而进入阻断状态，可以增加效率。
		 * */
	}
	public static void Condition() {
		/* <3>使用Condition
		 * 
		 * Condition接口来搭配 Lock，最基本的用法就是达到 Object的 wait()、notify()、notifyAll()方法的作用。
		 * 
		 * 例子：先来看看使用 Lock与 Condition改写的11.1.6中的生产者、消费者、范例的 Clerk类
		 * */
		Clerk2 clerk = new Clerk2();
		new Thread(new Producer2(clerk)).start();
		new Thread(new Consumer2(clerk)).start();
		/* 可以调用Lock的newCondition取得Condition操作对象，
		 * 调用Condition的await()将会使线程进入Condition的等待集合。
		 * 
		 * 要通知等待集合中的一个线程，则可以调用signal()方法；如果要通知所有等待集合中的线程，可以调用signalAll()方法。
		 * */
		
		/* 优化：
		 * 可以重复调用 Lock的newCondition()，取得多个Condition实例，这代表了可以有多个等待集合，
		 * 比如：创建两个等待集合：一个给生产者线程用，一个给消费者线程用，
		 * 		生产者只通知消费者等待集合，消费者只通知生产者等待集合，那会比较有效率。
		 * 
		 * 如果Clerk3无法收东西了，那就请生产者线程至生产者等待集合中等待，而生产者线程设定产品后，会通知消费者等待集合中的线程。
		 * 反之。。。
		 * 
		 * */
		Clerk3 clerk3 = new Clerk3();
	}
	
	// 使用 Executor
	public static void exp2() {
		/* Runnale用来定义可执行流程与可使用数据， Thread用来执行Runnale。
		 * 两者结合的基本做法正如前面介绍的，将Runnale指定给Thread创建之用，并调用start()开始执行。
		 * 
		 * 如何建立Thread、是否重用Thread、何时销毁Thread、被指定的Runable何时排给Thread执行，这些都是复杂的议题。
		 * 为此，从JDK5开始，定义了 java.util.concurrent.Execute接口，
		 * 目的是将 Runnable的指定与实际如何执行分离。
		 * 
		 * Execute接口只定义了一个 execute()方法。
		 * */
	}
}
