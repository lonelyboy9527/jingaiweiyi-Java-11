package cc.openhome;

import cc.openhome.class1.Hare;
import cc.openhome.class1.HareThread;
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
		 * 
		 * */
	}
	
	// 线程生命周期
	public static void exp3() {
		/* 线程的生命周期颇为复杂，下面将从最简单的开始介绍
		 * */
		
		/* <1>.Daemon 线程*/
		exp3_1();
	}
	public static void exp3_1() {
		/* <1>.Daemon 线程
		 * 
		 * 主线程会从main()方法开始执行，直到main()方法结束后停止 JVM。
		 * 如果主线程中启动了额外线程，默认会等待被启动的所有线程都执行完 run()方法才中止JVM。
		 * 如果
		 * */
	}
}
