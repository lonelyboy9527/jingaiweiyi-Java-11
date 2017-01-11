package cc.openhome.class1;

// 生产者，生产产品
public class Producer implements Runnable {
	
	private Clerk clerk;
	public Producer(Clerk clerk) {
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

	public Clerk getClerk() {
		return clerk;
	}

	public void setClerk(Clerk clerk) {
		this.clerk = clerk;
	}

}
