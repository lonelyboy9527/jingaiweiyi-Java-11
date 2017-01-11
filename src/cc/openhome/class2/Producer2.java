package cc.openhome.class2;

import cc.openhome.class2.Clerk2;

public class Producer2 implements Runnable {
	private Clerk2 clerk;
	public Producer2(Clerk2 clerk) {
		// TODO Auto-generated constructor stub
		this.setClerk(clerk);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("生产者开始生产整数..."); // 产生1-10的随机整数
		for (int product = 1; product <= 10; product++) {
			try {
				Thread.sleep((int) (Math.random() * 3000)); // 暂停随机时间(表示生产过程)
			} catch (Exception e) {
				// TODO: handle exception
				throw new RuntimeException(e);
			}
			clerk.setProduct(product); // 将产品交给店员
		}
	}
	public Clerk2 getClerk() {
		return clerk;
	}

	public void setClerk(Clerk2 clerk) {
		this.clerk = clerk;
	}
}
