package cc.openhome;

import cc.openhome.class2.Resource2;
import cc.openhome.class2.SubDir;
import cc.openhome.class2.ThreadPerTaskExecutor;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import cc.openhome.class2.ArrayList;
import cc.openhome.class2.ArrayList2;
import cc.openhome.class2.Clerk2;
import cc.openhome.class2.Consumer2;
import cc.openhome.class2.DirectExecutor;
import cc.openhome.class2.FutureCallable;
import cc.openhome.class2.Pages;
import cc.openhome.class2.Producer2;
import cc.openhome.class2.Clerk3;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cc.openhome.class2.Fibonacci;
import java.util.concurrent.ForkJoinPool;
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
		System.out.println("Lock -> 线程锁");
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
		System.out.println("Resource2 -> 使用tryLock解决死结");
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
		System.out.println("ReadWriteLock -> 读写锁，能够改进效率，读取操作，可允许线程同时并行");
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
		System.out.println("Condition -> 搭配Lock 达到 Object的 wait()、notify()、notifyAll()方法的作用");
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
		System.out.println("Condition优化 -> 创建多个等待集合，提高效率");
		Clerk3 clerk3 = new Clerk3();
	}
	
	// 使用 Executor
	public static void exp2() {
		System.out.println("Executor -> 目的是将 Runnable的指定与实际如何执行分离");
		/* Runnale用来定义可执行流程与可使用数据， Thread用来执行Runnale。
		 * 两者结合的基本做法正如前面介绍的，将Runnale指定给Thread创建之用，并调用start()开始执行。
		 * 
		 * 如何建立Thread、是否重用Thread、何时销毁Thread、被指定的Runable何时排给Thread执行，这些都是复杂的议题。
		 * 为此，从JDK5开始，定义了 java.util.concurrent.Execute接口，
		 * 
		 * 目的是将 Runnable的指定与实际如何执行分离。
		 * 
		 * Execute接口只定义了一个 execute()方法。
		 * 
		 * 例子：将11.1.3节中的Download 与 Download2封装起来。
		 * */
//		download();
		
		/* 使用 DirectExecutor实现Executor接口，调用传入的execute()方法
		 * 上面还是单纯使用 主线程 执行指定的每个页面下载。
		 * 
		 * 如果定义一个 ThreadPerTaskExecutor：对于每个传入的Runnable对象，会建立一个实例并执行start()执行。
		 * 如果这样使用Pages:
		 * */
//		download2();
		
		/* 那么针对每个网页，会启动一个线程来进行下载。
		 * 或许你会想到，若要下载的页面非常多，每次建立一个线程下载页面完后，
		 * 就丢弃该线程过于浪费系统资源。也许你会想到操作一个具有线程池的 Executor，
		 * 建立可重复使用的线程，这是可行的。
		 * 不过不用亲自操作，因为 Java SE API中提供有接口与操作类，可达到此类要求。
		 * */
		
		exp2_1();
	}
	public static void download() {
		System.out.println("download -> 使用 Executor的 execute()方法执行 run()");
		URL[] urls = null;
		try {
			URL[] urls2 = {
					new URL("https://github.com/lonelyboy9527/jingaiweiyi-Java-11/issues"),
					new URL("https://github.com/lonelyboy9527/jingaiweiyi-Java-11/pulls"),
					new URL("https://github.com/lonelyboy9527/jingaiweiyi-Java-11/projects"),
					new URL("https://github.com/lonelyboy9527/jingaiweiyi-Java-11/wiki")
			};
			urls = urls2;
		} catch (Exception e) {
			// TODO: handle exception
		}
			
		String[] fileNames = {
			"issues2.html",
			"pulls2.html",
			"projects2.html",
			"wiki2.html"
		};
		new Pages(urls, fileNames, new DirectExecutor()).download();
	}
	public static void download2() {
		System.out.println("download2 -> 使用 Executor的 execute()方法中，创建多线程执行 run()");
		URL[] urls = null;
		try {
			URL[] urls2 = {
					new URL("https://github.com/lonelyboy9527/jingaiweiyi-Java-11/issues"),
					new URL("https://github.com/lonelyboy9527/jingaiweiyi-Java-11/pulls"),
					new URL("https://github.com/lonelyboy9527/jingaiweiyi-Java-11/projects"),
					new URL("https://github.com/lonelyboy9527/jingaiweiyi-Java-11/wiki")
			};
			urls = urls2;
		} catch (Exception e) {
			// TODO: handle exception
		}
			
		String[] fileNames = {
			"issues3.html",
			"pulls3.html",
			"projects3.html",
			"wiki3.html"
		};
		new Pages(urls, fileNames, new ThreadPerTaskExecutor()).download();
	}
	// 线程池
	public static void exp2_1() {
		System.out.println("在多线程中 -> 通过线程池管理线程");
		/* <1>.使用ThreadPoolExecutor*/
//		ThreadPoolExecutor();
		
		/* <2>.使用ScheduledThreadPoolExecutor*/
//		ScheduledThreadPoolExecutor();
		
		/* <3>.使用ForkJoinPool*/
		ForkJoinPool();
	}
	public static void ThreadPoolExecutor() {
		System.out.println("ThreadPoolExecutor -> 通过ExecutorService的子接口ThreadPoolExecutor来实现线程池功能");
		/* <1>.使用ThreadPoolExecutor
		 * 
		 * 在Java SE API中，像线程池这类服务的行为，实际上是定义在 Executor的子接口
		 * java.util.concurrent.ExecutorService中。
		 * 
		 * 通用的ExecutorService由抽象类 AbstractExecutorService操作。如果需要线程池的功能，则可以使用其子类
		 * java.util.concurrent.ThreadPoolExecutor。
		 * 
		 * 根据不同的线程池要求，ThreadPoolExecutor又有数种不同构造函数可共使用。
		 * 不过通常会使用:
		 * newCachedThreadPool()、
		 * newFixedThreadPool()静态方法来创建 ThreadPoolExecutor实例，程序看起来清楚。
		 * 
		 * 通过newCachedThreadPool返回的实例，会在必要时建立线程，你的 Runnable可能执行在新建的线程，或是重复利用的线程中。
		 * 通过newFixedThreadPool则可指定在池中建立固定数量的线程。
		 * 
		 * 
		 * 例子：在ThreadPoolExecutor搭配前面的Pages使用:
		 * */
//		download3();
		
		/* ExecutorService还定义了 submit()、invokeAll()、invokeAny()等方法，
		 * 这些方法中出现了 java.util.concurrent.Future、java.util.concurrent.Callable接口。
		 * 
		 * Future:就是让你将来取得结果，你可以将要执行的工作交给Future，
		 * 		Future会使用另一个线程来进行工作，你就可以先忙别的事去了。
		 * 		过些时候，再调用Future的get()取得结果，如果结果已经产生，get()会直接返回，否则会进入阻断直到结果返回。
		 * 		get()的另一个版本则可以指定等待时间。(到时间还没有产生，则抛出异常)，也可使用 isDone()看看结果是否产生。
		 * Future经常与Callable搭配使用
		 * 
		 * Callable的作用与 Runnable相似，可让你定义想要执行的流程，
		 * 			不过Runnable的run方法无法返回值，也无法抛出受检异常。
		 * 			而Callable的call()方法可以返回值，也可以抛出受检异常。
		 * 
		 * 举个例子：Future与Callable运用的实例，在未来某个时间获取 斐波那契数字
		 * */
//		FutureCallable();
		
		/* 如果你的流程已定义在某个Runnable对象中，FutureTask创建时也有接受Runnable的版本
		 * 并可指定一个对象在调用get()时返回(运算结束后)
		 * 
		 * 回头看看ExecutorService的submit()方法，它可以接受Callable对象，调用后返回的 
		 * Future对象，就是让你在稍后可以取得运算结果。
		 * 
		 * 例如，改写以上的范例为使用 ExecutorService的版本：
		 * */
		FutureCallable2();
		/* 范例中的执行结果与上个例子相同。
		 * 
		 * 如果有多个Callable，可以先收集为 Collection中，然后调用 ExecutorService的
		 * invokeAll()，这会以 List<Future<T>>返回与 Callable相关联的Future对象。
		 * 
		 * 如果有多个 Callable，只要有一个执行完成就可以。那可以先收集在 Collection中，
		 * 然后调用 ExecutorService的invokeAny()，只要Collection其中一个Callable完成，
		 * invokeAny()就会返回该Callavle的执行结果。
		 * */
	}
	public static void download3() {
		System.out.println("download3 -> 通过ExecutorService线程池来下载");
		URL[] urls = null;
		try {
			URL[] urls2 = {
					new URL("https://github.com/lonelyboy9527/jingaiweiyi-Java-11/issues"),
					new URL("https://github.com/lonelyboy9527/jingaiweiyi-Java-11/pulls"),
					new URL("https://github.com/lonelyboy9527/jingaiweiyi-Java-11/projects"),
					new URL("https://github.com/lonelyboy9527/jingaiweiyi-Java-11/wiki")
			};
			urls = urls2;
		} catch (Exception e) {
			// TODO: handle exception
		}
			
		String[] fileNames = {
			"issues3.html",
			"pulls3.html",
			"projects3.html",
			"wiki3.html"
		};
		ExecutorService executorService = Executors.newCachedThreadPool();
		new Pages(urls, fileNames, executorService).download();
		// 会在指定执行的 Runnable都完成后，将ExecutorService关闭（在这里就是关闭ThreadPoolExecutor）
		
		// 还有另一个 shutdownNow()方法，则可以立即关闭 ExecutorService，尚未执行的Runnable对象会以 List<Runnable>返回
		executorService.shutdown();
	}
	
	public static void FutureCallable() {
		System.out.println("FutureCallable -> Future与 Callable搭配在某个未来获取线程的执行结果");
		FutureTask<Long> fib30 = new FutureTask<> (
				new Callable<Long>() {
					public Long call() {
						return FutureCallable.fibonacci(30);
					}
				}
			);
			System.out.println("老板，我要第 30 个费式数，待会来拿......");
			new Thread(fib30).start();
			System.out.println("先忙别的事去......");
			try {
				Thread.sleep(5000);
				System.out.printf("第 30 个费式数: %d\n", fib30.get());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	}
	public static void FutureCallable2() {
		System.out.println("FutureCallable2 -> 使用 ExecutorService的 submit在某个未来获取线程的执行结果");
		ExecutorService service = Executors.newCachedThreadPool();
		System.out.println("老板，我要第 30 个费式数，待会来拿......");
		Future<Long> fib30 = service.submit(new Callable<Long>() {
			public Long call() {
				return FutureCallable.fibonacci(30);
			}
		});
		System.out.println("先忙别的事去......");
		try {
			Thread.sleep(5000);
			System.out.printf("第 30 个费式数: %d\n", fib30.get());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void ScheduledThreadPoolExecutor() {
		System.out.println("ScheduledThreadPoolExecutor -> 对重复性执行的线程进行工作排程");
		/* <2>.使用ScheduledThreadPoolExecutor
		 * 
		 * ScheduledThreadPoolExecutor 为 ExecutorService的子接口，可以让你进行工作排程；
		 * 
		 * schedule()方法用来排定Runnable或 Callable实例延迟多久后执行一次，并返回Future子接口 scheduleFuture的实例;
		 * 对于重复性执行，可使用 scheduleWithFixedDelay() 与 scheduleAtFixedRate()方法。
		 * 
		 * 
		 * 在一个线程只排定一个 Runnable实例的情况下，
		 * scheduleWithFixedDelay()方法可排定 延迟多久首次执行 Runnable，执行完Runnable会 排定延迟多久 后再次执行。
		 * scheduleAtFixedRate()方法可指定 延迟多久首次执行 Runnable，同时依据指定周期 排定每次执行Runnable的时间 。
		 * 这两种方法运行中，上次排定的工作抛出异常，不会影响下次排程的进行。
		 * 
		 * ScheduledExecutorService的操作类 ScheduledThreadPoolExecutor为 ThreadPoolExecutor的子类，
		 * 具有线程池和排程功能。
		 * 
		 * 可以使用 Executor的newScheduledThreadPool()方法指定返回内建多少个线程的 ThreadPoolExecutor；
		 * 使用 newSingleThreadScheduledExecutor()则可使用单个线程执行排定的工作。
		 * 
		 * 
		 * 例子：newSingleThreadScheduledExecutor返回的ScheduledExecutorService，在排定个Runnable的情况下，
		 * 		scheduleWithFixedDelay执行的时间点。
		 * */
//		ScheduledExecutorService();
		
		/* 如果把以上的scheduleWithFixedDelay改成scheduleAtFixedRate，
		 * 每次排定的执行周期虽然为1秒，但由于每次工作执行实际上为2秒，会超过排定周期，
		 * 所以上一次执行完工作后，会立即执行下一次工作。，结果就是显示为2秒一个间隔。
		 * */
		ScheduledExecutorService2();
		/* 注意：如果将 Thread.sleep(2000)改成 Thread.sleep(500)，
		 * 由于每次工作执行时间不会超过排定周期，所以显示会 1秒一个间隔。
		 * */
	}
	public static void ScheduledExecutorService() {
		System.out.println("ScheduledExecutorService -> scheduleWithFixedDelay指定多久开始执行线程，之后间隔多久一次");
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		System.out.printf("开始时间: %s\n", new java.util.Date().toString());
		service.scheduleWithFixedDelay(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				// 取得当前系统时间。
				System.out.println(new java.util.Date());
				
				try {
					// 假设这个工作会进行两秒
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO: handle exception
					throw new RuntimeException(e);
				}
			}
		}, 3000, 1000, TimeUnit.MILLISECONDS);
		// 3000毫秒后开始。每次完成工作延迟1000毫秒
				/* 每次工作会执行2秒，而后延迟1秒，所以看到的时间显示总共是 3秒一个间隔：*/
	}
	public static void ScheduledExecutorService2() {
		System.out.println("ScheduledExecutorService2 -> scheduleAtFixedRate指定多久开始执行线程，一次周期多久时间");
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		System.out.printf("开始时间: %s\n", new java.util.Date().toString());
		service.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				// 取得当前系统时间。
				System.out.println(new java.util.Date());
				
				try {
					// 假设这个工作会进行两秒
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO: handle exception
					throw new RuntimeException(e);
				}
			}
		}, 3000, 1000, TimeUnit.MILLISECONDS);
		// 3000毫秒后开始。每次工作时间周期为1000毫秒
				/* 每次工作会执行2秒，而给定周期为1秒，所以看到的时间显示总共是2秒一个间隔，下一个周期立即执行*/
		
	}
	
	public static void ForkJoinPool() {
		System.out.println("ForkJoinPool -> 结合多线程将问题分而治之");
		/* <3>.使用ForkJoinPool
		 * 
		 * Future的操作类ForkJoinTask 及其子类，
		 * ExecutorService的操作类ForkJoinPool及其子类
		 * 它们都是JDK7中新增的API，主要目的是在解决 <分而治之> 的问题
		 * 
		 * 分而治之的问题:是指这些问题的解决，可以分解为性质相同的子问题，子问题还可以再分解为更小的子问题，
		 * 				将性质相同的子问题解决并收集运算结果，整体问题也就解决了。
		 * 
		 * 例如：求解斐波那契数列数列，若想解决第N个费式数，必须先求得 第N-1个，再求得第N-2费式数，
		 * 		如果可以使用两个执行流程，N-1费式数 和 N-2费式数同时并行，那么解决问题的速度将会加快。
		 * 
		 * 在分而治之需要结合并行的情况下， 可以使用 ForkJoinTask，其操作了 Future接口，可让你在未来取得耗时工作的执行结果。
		 * 		如果有个ForkJoinTask在 ForkJoinPool的管理时，执行了 fork()方法，则会以另一个线程来执行它；
		 * 		如果想取得ForkJoinTask的执行结果，可以调用join()方法；
		 * 		如果结果尚未产生，则会阻断至执行结果返回。
		 * 
		 * 		可以使用ForkJoinTask的子类 RecursiveTask，这用于执行后会返回结果的时候，
		 * 		RecursiveTask是个抽象类，使用时必须继承它，并操作compute)方法，
		 * 
		 * 例子：下面使用RecursiveTask与 ForkJoinPool解决斐波那契数的示范：
		 * */
//		RecursiveTask();
		/* 继承RecursiveTask后，主要将子任务的分解与求解过程撰写于compute()方法之中，
		 * 为了避免任务分解过细，产生、管理线程的成本超过了使用Fork/Join的好处，n小于10就不分解出子任务，直接递归求解了。
		 * 
		 * n-1子任务调用了 fork()方法，ForkJoinPool会分配线程来执行其compute()方法，
		 * 程序中直接调用 f2的compute()。在取得n-2费式数的值后，
		 * 幸运的话，此时f1的值已经算好了，此时直接调用join()来取得运算结果
		 * 		   如果f1的值没有算好，就会等待f1执行结果返回。
		 * 
		 * ForkJoinPool 的invoke()方法，接受 ForkJoinTask实例，
		 * 		如果是 RecursiveTask，将会调用其compute()开始执行运算，所有 ForkJoinTask实例的compute()方法执行完毕，ForkJoinPool就会关闭。
		 * 
		 * 
		 * 如果分而治之的过程中，子任务不需要返回值，可以继承 RecursiveAction并操作 compute()方法。
		 * 例子：写个文档搜索的程序：
		 * */
		RecursiveAction();
		
		/* 在逐一取得文件夹路径信息时，如果是文件夹就先分解为查询文件夹的子任务。
		 * 		如果是文档就比较是否符合搜索条件，是的话就显示在控制台中。
		 * 
		 * 最后调用ForkJoinTask的invokeAll()方法，ForkJoinPool会为每个查询子文件夹的子任务
		 * 		分配线程进行运算。
		 * */
	}
	public static void RecursiveTask() {
		System.out.println("RecursiveTask -> 操作ForkJoinTask的子类RecursiveTask抽象类实现分而治之");
		Fibonacci fibonacci = new Fibonacci(45);
		ForkJoinPool mainPool = new ForkJoinPool();
		System.out.println(mainPool.invoke(fibonacci)); // 开始分而治之
	}
	public static void RecursiveAction() {
		System.out.println("RecursiveAction -> 操作ForkJoinTask的子类RecursiveAction抽象类，针对子任务没有返回值的情况");
		
		ForkJoinPool mainPool = new ForkJoinPool();
		SubDir subDir = new SubDir(Paths.get("/Users/yunshang/Desktop"), ".rtf");
		mainPool.invoke(subDir);
	}
}
