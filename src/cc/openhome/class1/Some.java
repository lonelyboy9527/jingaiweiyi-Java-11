package cc.openhome.class1;

public class Some implements Runnable {
	private boolean isContinue = true;
	
	public void stop() {
		isContinue = false;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (isContinue) {
			// 通过一个标记来判断
			try {
				Thread.sleep(1000);
				System.out.println("开始Some线程!...");
			} catch (InterruptedException e) {
				// TODO: handle exception
				throw new RuntimeException(e);
			}
		}
	}

}
