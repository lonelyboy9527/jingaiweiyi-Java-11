package cc.openhome.class3;

import java.util.concurrent.BlockingQueue;

public class Consumer3 implements Runnable {
	private BlockingQueue<Integer> queue;
	
	public Consumer3(BlockingQueue<Integer> queue) {
		// TODO Auto-generated constructor stub
		this.queue = queue;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("消费者开始消耗整数...");
		for (int i = 1; i <= 10; i++) { // 消耗10次整数
			try {
				Thread.sleep((int) (Math.random() * 3000)); // 代表和店员讨价还价时间
				int product = queue.take();
				System.out.printf("消费者消耗整数 (%d)\n", product);
			} catch (InterruptedException e) {
				// TODO: handle exception
				throw new RuntimeException(e);
			}
		}
	}

}
