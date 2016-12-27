package cc.openhome.class1;

public class HareThread extends Thread {
	private int totalStep;
	private int step;
	private boolean[] Flags = {true, false};
	
	public HareThread(int totalStep) {
		// TODO Auto-generated constructor stub
		this.totalStep = totalStep;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while (step < totalStep) {
				Thread.sleep(1000);
				boolean isHareSleep = Flags[(int)(Math.random() * 10) % 2];
				if (isHareSleep) {
					System.out.println("兔子睡着了zzz");
				} else {
					step +=2;
					System.out.printf("兔子跑了 %d 步\n", step);
				}
			}
		} catch (InterruptedException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
}
