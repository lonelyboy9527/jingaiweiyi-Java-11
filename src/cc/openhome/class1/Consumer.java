package cc.openhome.class1;

// 消费者，从店员处取走int整数
public class Consumer implements Runnable {
	private Clerk clerk;
	
	public Consumer(Clerk clerk) {
		// TODO Auto-generated constructor stub
		this.clerk = clerk;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("消费者开始消耗整数...");
		for (int i = 1; i <= 10; i++) { // 消耗10次整数
			try {
				Thread.sleep((int) (Math.random() * 3000)); // 代表和店员讨价还价时间
			} catch (InterruptedException e) {
				// TODO: handle exception
				throw new RuntimeException(e);
			}
			int product = clerk.getProduct(); // 从店员处取走产品
		}

	}

}
