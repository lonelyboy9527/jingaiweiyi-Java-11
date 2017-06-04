package cc.openhome.class1;

/* Hare类中，兔子的流程会从run开始，兔子只要专心负责每秒睡觉或走两步 就可以了
 * 不会混杂乌龟的流程。
 * */
public class Hare implements Runnable {

	private int totalStep;
	private int step;
	private boolean[] Flags = {true, false};
	
	public Hare(int totalStep) {
		// TODO Auto-generated constructor stub
		this.totalStep = totalStep;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("------> 兔子跑步开始:");
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
