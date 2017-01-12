package cc.openhome.class3;

import java.util.concurrent.BlockingQueue;

public class Producer3 implements Runnable {

	private BlockingQueue<Integer> queue;
	
	public Producer3(BlockingQueue<Integer> queue) {
		// TODO Auto-generated constructor stub
		this.queue = queue;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("生产者开始生产整数...");
		for (int product = 1; product <= 10; product++) {
			try {
				try {
					Thread.sleep((int) (Math.random() * 3000));
					queue.put(product);
					System.out.printf("生产者提供整数 (%d)\n", product);
				} catch (Exception e) {
					// TODO: handle exception
					throw new RuntimeException(e);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

}
