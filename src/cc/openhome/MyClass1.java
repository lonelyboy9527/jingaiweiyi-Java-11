package cc.openhome;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

import cc.openhome.class1.Hare;
import cc.openhome.class1.HareThread;
import cc.openhome.class1.Some;
import cc.openhome.class1.Tortoise;
import cc.openhome.class1.TortoiseThread;

public class MyClass1 {
	// 线程简介
	public static void exp1() {
		/* 如果要设计一个龟兔赛跑游戏
		 * 赛程长度为10步，每经过一秒，乌龟会前进一步，兔子则可能前进两步或睡觉，那该怎么设计呢？
		 * 
		 * 用单线程设计法：
		 * */
		run();
		/* 目前程序只有一个流程。
		 * 使用了 java.lang.Thread 的静态方法 sleep()，调用这个方法必须处理 java.lang.InterruptedException异常。
		 * 
		 * Thread.sleep(1000); // 暂停1秒
		 * 
		 * 在这个流程中，每次都是先递增乌龟的步数 再递增兔子的步数，这样对兔子很不公平。
		 * 
		 * 改进：
		 * 如果想在 main()以外独立设计流程，可以撰写类操作 java.lang.Runnable接口，
		 * 流程的进入点是操作在 run()方法中
		 * 
		 * 例子：
		 * */
		Tortoise tortoise = new Tortoise(10);
		Hare hare = new Hare(10);
		/* 相当于开了两个线程，线程会运行 实例里面的 run()方法
		 * 
		 * 创建Thread实例来执行Runnable实例定义的 run()方法。
		 * */
		Thread tortoiseThread = new Thread(tortoise); 
		Thread hareThread = new Thread(hare);

		tortoiseThread.start();
		hareThread.start();
		
		
	}
	public static void run() {
		// 龟兔赛跑
		try {	
			boolean[] flags = {true, false};
			int totalStep = 10;
			int tortoiseStep = 0;
			int hareStep = 0;
			System.out.println("龟兔🐢🐰赛跑开始...");
			while (tortoiseStep < totalStep && hareStep < totalStep) {
				Thread.sleep(1000); // 暂停1000ms，（1s）
				tortoiseStep++;
				System.out.printf("乌龟🐢跑了 %d 步...\n", tortoiseStep);
				boolean isHareSleep = flags[(int) (Math.random() * 10) % 2]; // 随机求的 0或1
				if (isHareSleep) {
					System.out.println("兔子🐰睡着了zzz");
				} else {
					hareStep +=2;
					System.out.printf("兔子🐰跑了 %d 步\n", hareStep);
				}
			}
			if (hareStep > tortoiseStep) {
				System.out.println("兔子🐰赢了！");
			} else {
				System.out.println("乌龟🐢赢了!");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	// Thread 与Runnable
	public static void exp2() {
		/* 从抽象观点的开发者角度看， JVM是台虚拟计算机，
		 * 只安装一颗称为主线程的CPU，可执行main()定义的执行流程。
		 * 
		 * 如果想要为JVM加装CPU，就是创建 Thread实例，要启动额外的CPU就是调用 start()方法，
		 * 额外的CPU执行流程的进入点，可以定义在 Runnable接口的 run()方法中。
		 * 
		 * 
		 * 除了将流程定义在Runnable接口的 run()方法中之外，
		 * 另一个撰写多线程程序的方式，就是继承 thread类，重新定义 run()方法。
		 * 
		 * 上一节程序改进如下：
		 * */
		new TortoiseThread(10).start();
		new HareThread(10).start();
		
		/* 在Java中， 任何线程可执行的流程都要定义在Runnable的Run()方法。
		 * 事实上， Thread本身也操作了 Runnable接口，而run()方法的操作如下：
		 *  @Override
		 *  
		 *  public void run() {
		 *  	if (target != null) {
		 *  	 	target.run();
		 *  	}
		 *  }
		 * 
		 * 那么是操作 Runnable在  run()中定义额外流程好，还是 继承 Thread在 run()中定义额外流程好？？？
		 * 
		 * 	操作Runnable接口的好处是具有弹性，你的类还有机会继承其他类。(Java只有单继承。)
		 * 	所以要尽量采用操作接口的方式。
		 * */
	}
	
	// 线程生命周期
	public static void exp3() {
		/* 线程的生命周期颇为复杂，下面将从最简单的开始介绍
		 * */
		
		/* <1>.Daemon 线程*/
//		exp3_1();
		
		/* <2>.Thread基本状态图*/
//		exp3_2();
		
		/* <3>.安插线程*/
//		exp3_3();
		
		/* <4>.停止线程*/
		exp3_4();
	}
	public static void exp3_4() {
		/* <4>.停止线程
		 * 线程执行完 run()后，就会进入Dead，进入Dead()的线程不可以再调用start()方法，否则
		 * 会抛出IllegalThreadStateException
		 * */
		
		/* Thread类定义有 stop()方法，不过被标示为 Deprecated（表示过去确实定义过，后来因为引发某些问题
		 * 为了确保兼容性，这些API没有直接剔除，但不建议再使用它），
		 * 
		 * 如果使用Deprecated标示的 API，变异程序会剔除警告，而在IDE中，通常会出现删除线表示不建议使用。
		 * 为什么最新的API不建议使用stop()方法？
		 * 	因为直接调用stop()方法，将不会理会所设定的释放、取得锁流程，线程会直接释放所有已锁定对象（锁定后面谈），
		 * 这有可能使对象陷入无法预期状态，
		 * 
		 * 除了stop()方法，Thread的 resume()、suspend()、destroy()等方法也不建议使用。
		 * 
		 * 如果要停止线程，最后自行操作，让线程跑完所有的流程
		 * 例子：
		 * */
		Some some = new Some();
		Thread thread = new Thread(some);
		thread.start();
		some.stop();
	}
	public static void exp3_3() {
		/* <3>.安插线程
		 * 
		 * 如果线程A正在运行，流程中允许B线程加入，等到B线程执行完毕后再继续A线程流程
		 * 则可以使用join()方法完成这个需求。
		 * 
		 * 当线程使用join()加入至另一线程中时，另一个线程会等待被加入的线程工作完毕，然后再继续。
		 * join()意味着将线程加入至另一线程的流程中。
		 * */
		join();
	}
	
	public static void join() {
		System.out.println("Main thread 开始...");
		Thread threadB = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					System.out.println("Thread B 开始...");
					for (int i = 0; i < 5; i++) {
						Thread.sleep(1000);
						System.out.println("Thread B 执行...");
					}
					System.out.println("Thread B 将结束...");
				} catch (InterruptedException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		};
		threadB.start();
		try {
			// Thread B 加入Main  thread流程
			threadB.join();
			/* 有时候，加入的线程可能处理太久，不想等待这个线程执行完毕，
			 * 则可以在join()时指定时间，如join(1000),如果1000毫秒这个线程还没有执行完毕，
			 * 就不理它了，目前的线程可执行原本工作流程。
			 * */ 
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("Main thread 将结束...");
	}
	public static void exp3_2() {
		/* <2>.Thread基本状态图
		 * 
		 * 在调用Thread实例start()方法之后，基本状态为可执行(Runnable)、被阻断(Blocked)、执行中(Running)。
		 * 
		 * start() -> Runnable可执行状态(线程尚未真正开始执行run()方法，等待排班器排班) <-> Running状态 -> Blocked状态(阻断) -> Runnable可执行状态 -> ...
		 * 																		   -> Dead状态(执行完成)
		 * 线程具有优先权，可使用Thread的 setPriority()方法设定优先权，可设定值为1到10,默认是5;
		 * 超出1-10的设定值会抛出 IllegalArgumentException。
		 * 数字越大优先级越高。（排班器优先排入CPU，优先级相同，则轮流执行。）
		 * 			
		 * Blocked状态(阻断):
		 * 1.调用sleep()方法，就会让线程进入Blocked状态。（还有调用wait()的阻断）
		 * 2.等待输入／输出完成也会进入Block()状态。
		 * 
		 * 例子：指定网页下载网页，不使用线程花费时间：
		 * */
		try {
			Download();
		} catch (Exception e) {
			// TODO: handle exception
		}
		/* 在程序每一次循环时，会进行开启网络连接，进行http请求
		 * 然后在进行写入文档等，在等待网络链接、http协议时很耗时（也就是进入Blocked的时间较长）
		 * 
		 * 改进，并行执行。
		 * 例子:
		 * */
		try {
			Download2();
		} catch (Exception e) {
			// TODO: handle exception
		}
		/* 在for循环时，会建立全新的Thread并启动，以进行网络下载。
		 * 可以执行看看与上一个的差别有多少。*/

		/* 一个进入Blocked状态的线程，可以由另一个线程调用该线程的interrupt()方法
		 * 让它离开Blocked状态。
		 * 
		 * 例如：使用Thread.sleep()会让线程进入Blocked状态，若此时有其他线程调用该线程的interrupt()方法，
		 * 会抛出InterruptedException异常对象，这是让线程“醒过来”的方式。
		 * 例子：
		 * */
		Interrupted();
	}
	public static void Interrupted() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(9999);
				} catch (InterruptedException e) {
					// TODO: handle exception
					System.out.println("我醒了XD");
				}
			}
		};
		thread.start();
		thread.interrupt();
	}
	public static void Download() throws Exception {
		System.out.println("执行Download");
		URL[] urls = {
			new URL("https://github.com/lonelyboy9527/jingaiweiyi-Java-11/issues"),
			new URL("https://github.com/lonelyboy9527/jingaiweiyi-Java-11/pulls"),
			new URL("https://github.com/lonelyboy9527/jingaiweiyi-Java-11/projects"),
			new URL("https://github.com/lonelyboy9527/jingaiweiyi-Java-11/wiki")
		};
		
		String[] fileNames = {
			"issues.html",
			"pulls.html",
			"projects.html",
			"wiki.html"
		};
		for (int i = 0; i < urls.length; i++) {
			dump(urls[i].openStream(), new FileOutputStream(fileNames[i]));
		}
	}
	public static void Download2() throws Exception {
		System.out.println("执行Download2");
		URL[] urls = {
				new URL("https://github.com/lonelyboy9527/jingaiweiyi-Java-11/issues"),
				new URL("https://github.com/lonelyboy9527/jingaiweiyi-Java-11/pulls"),
				new URL("https://github.com/lonelyboy9527/jingaiweiyi-Java-11/projects"),
				new URL("https://github.com/lonelyboy9527/jingaiweiyi-Java-11/wiki")
			};
			
			String[] fileNames = {
				"issues.html",
				"pulls.html",
				"projects.html",
				"wiki.html"
			};
		for (int i = 0; i < urls.length; i++) {
			// 匿名内部类中存取局部变量，则该变量必须是final
			final int index = i;
			new Thread() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println("index: " + index);
					try {
						dump(urls[index].openStream(), new FileOutputStream(fileNames[index]));
					} catch (Exception e) {
						// TODO: handle exception
						throw new RuntimeException(e);
					}
				}
			}.start();
		}
	}
	public static void dump(InputStream src, OutputStream dest) throws IOException{
		try (InputStream input = src; OutputStream output = dest) {
			byte[] data = new byte[1024];
			int length = -1;
			while ((length = input.read(data)) != -1) {
				output.write(data, 0, length);
			}
		}
	}
	public static void exp3_1() {
		/* <1>.Daemon 线程
		 * 
		 * 主线程会从main()方法开始执行，直到main()方法结束后停止 JVM。
		 * 如果主线程中启动了额外线程，默认会等待被启动的所有线程都执行完 run()方法才中止JVM。
		 * 如果一个Thread被标示为Daemon线程，在所有的非Daemon线程都结束时，JVM自动就会终止。
		 * (在其他的线程结束时，就会终止JVM)
		 * 
		 * 从main()方法开始的就是一个非Daemon线程，可以使用setDaemon()方法来设定一个线程是否为Daemon线程。
		 * 例子：
		 * 下面的 例子如果没有 setDaemon设定为true，则程序会不断的输出 "Orz"而不终止;
		 * 使用isDaemon()方法可以判断线程是否为Daemon线程。
		 * */
		System.out.println("Daemon线程");
		Thread thread = new Thread() {
			public void run() {
				while (true) {
					System.out.println("Orz");
				}
			}
		};
		thread.setDaemon(true); // 要设置setDaemon = true;
		thread.start();
	}
	// 关于ThreadGroup
	public static void exp4() {
		/* 每个线程都属于一个线程群组（ThreadGroup）
		 * 若在main()主流程中产生一个线程，该线程会属于main线程群组。
		 * 
		 * 可以使用以下获得目前线程的所属的线程组名：
		 * Thread.currentThread().getThreadGroup.getName();
		 * 
		 * 每个线程产生时，都会归入某个线程群组，这看线程在哪个群组中产生。
		 * 如果没有指定，则归入产生该子线程的线程群组，也可以自行指定线程群组，线程一旦归入某个群组，就无法更换群组。
		 * 
		 * java.lang.ThreadGroup类正如其名，可以管理群组中的线程。
		 * 例子：产生群组，并在产生线程时指定所属群组：
		 * */
		ThreadGroup threadGroup = new ThreadGroup("group1");
		ThreadGroup threadGroup2 = new ThreadGroup("group2");
		
		Thread thread = new Thread(threadGroup, "group1's member");
		Thread thread2 = new Thread(threadGroup, "group2's member");
		
		/* ThreadGroup的某些方法，可以对群组中的所有线程产生作用
		 * 
		 * 例如：interrupt()方法可以中断群组中的所有线程。
		 * 		setMaxPriority()方法可以设定群组中所有线程的最大优先权。
		 * */
		
		/* 一次取得群组中的所有线程，可以使用 enumerate()方法。
		 * 例如：
		 * */
		Thread[] threads = new Thread[threadGroup.activeCount()]; // activeCount取得群组中线程数量
		threadGroup.enumerate(threads); // enumerate传入Thread数组，这会将线程对象设定至每个数组索引
		 
		/* ThreadGroup 中有个uncaughtException()方法，群组中某个线程发生异常而未捕捉时，
		 * JVM会调用此方法进行处理。(如果群组有父群组，则调用父群组的)
		 * 
		 * 例子：uncaughtException的例子
		 * */
//		threadGroupDemo();
		
		
		/* 对于线程本身未捕捉的异常，可以自行指定处理方式。
		 * 例子：
		 * */
		threadGroupDemo2();
		
		/* 在这个例子中，t1, t2都是属于同一个 ThreadGroup
		 * 
		 * t1设定了Thread.UncaughtExceptionHandler实例，所以未捕捉的异常会以
		 * thread.UncaughtExceptionHandler定义的方式处理
		 * 
		 * t2没有设置，所以由ThreadGroup默认的第三个处理方式。显示堆栈追踪。
		 * */
	}
	public static void threadGroupDemo2() {
		ThreadGroup tg1 = new ThreadGroup("tg1"); 
		Thread t1 = new Thread(tg1, new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// 人为制造异常，并且抛出
				throw new RuntimeException("测试异常");
			}
		});
		t1.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				// TODO Auto-generated method stub
				System.out.printf("%s: %s%n",t.getName(), e.getMessage());
			}
		});
		Thread t2 = new Thread(tg1, new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				throw new RuntimeException("t2 测试异常");
			}
		});
		t1.start();
		t2.start();

	}
	public static void threadGroupDemo() {
		ThreadGroup tg1 = new ThreadGroup("tg1") {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				// JVM调用 uncaughtException进行处理
				System.out.printf("%s: %s%n", t.getName(), e.getMessage());
			}
			
			/* JDK5之后，uncaughtException处理顺序是：
			 * 
			 * 1.如果ThreadGroup有父ThreadGroup，就会调用父ThreadGroup的uncaughtException。
			 * 2.否则，看看Thread是否使用setUncaughtExceptionHandler()方法设定Thread.Uncaught-ExceptionHandler实例
			 * 		有的话，就调用其uncaughtException。
			 * 3.否则，看看异常是否为ThreadGroup实例，若是，则什么都不做，若否则调用异常的printStrackTrace()。
			 * */
		};
		Thread t1 = new Thread(tg1, new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// 人为制造异常，并且抛出
				throw new RuntimeException("测试异常");
			}
		});
		t1.start();		
	}
	
	// synchronized与volatile
	public static void exp5() {
		/* 还记得以前自己开发过 ArrayList类吗？
		 * 在没有接触线程前，也就是将那个ArrayList用在主线程的环境中，没有什么问题，
		 * 如果同时使用在两个以上的线程，会如何？
		 * 
		 * 例子：
		 * */
//		ArrayListDemo();
		/* 会发生java.lang.ArrayIndexOutOfBoundsException异常，因为两个在同时运行 add()方法时
		 * 在内部 list[next++] = o；会产生越界。具体要自己分析
		 * 
		 * 因为t1,t2会同时存取next，使得next在巧合的情况下，脱离原本应管控的条件，
		 * 我们称这样的类为 不具备线程安全(Thread-safe)的类。
		 * */
		
		/* <1>.使用synchronized*/
		exp5_1();
	}
	public static void exp5_1() {
		/* <1>.使用synchronized
		 * 
		 * 可以在add()方法上加上 synchronized关键字。
		 * 在加上 synchronized关键字之后，再次执行前面范例，就不会看到 ArrayIndexOutOfBoundsException了
		 * */
	}
	public static void ArrayListDemo() {
		final ArrayList<Integer> list = new ArrayList<Integer>();
		Thread t1 = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					list.add(1);
				}
			}
		};
		Thread t2 = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					list.add(2);
				}
			}
		};
		t1.start();
		t2.start();
	}
}
