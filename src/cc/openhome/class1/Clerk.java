package cc.openhome.class1;

// 店员，从生产者那里获取产品

/* Clerk只能有一个整数，-1表示没有产品。
 * 
 * 由于店员一次只能持有一个int整数，所以必须尽到要求等待与通知的职责。
 * */
public class Clerk {
	private int product = -1; // 只持有一个产品，-1表示没有产品
	
	public synchronized void setProduct(int product) {
		while (this.product != -1) {
			try {
				wait(); // 目前店员没有空间放收到的产品，请稍后
			} catch (InterruptedException e) {
				// TODO: handle exception
				throw new RuntimeException(e);
			}
		}
		this.product = product; // 店员收货
		System.out.printf("生产者设定 (%d)\n", this.product);
		notify(); // 通知等待集合中的线程（例如消费者Consumer）
	}
	
	public synchronized int getProduct() {
		while (this.product == -1) {
			try {
				wait(); // 目前店员没货，请等待
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
				// TODO: handle exception
			}
		}
		int  p = this.product; // 准备交换
		System.out.printf("消费者取走 (%d)\n", this.product);
		this.product = -1; // 表示产品被取走
		notify(); // 通知等待集合中的线程(例如生产者Producer)
		return p; // 交货
	}
}
