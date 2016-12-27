package cc.openhome.class1;

public class TortoiseThread extends Thread {
	private int totalStep;
	private int step;
	
	public TortoiseThread(int totalStep) {
		// TODO Auto-generated constructor stub
		this.totalStep = totalStep;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while (step < totalStep) {
				Thread.sleep(1000);
				step++;
				System.out.printf("乌龟跑了 %d 步\n", step);
			}
		} catch (InterruptedException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
}
