package cc.openhome.class1;

/* 在Tortoise类中，乌龟的流程会从run()开始，
 * 乌龟只要专心负责每秒走一步就可以了，不会混杂兔子的流程。
 * 
 * */
public class Tortoise implements Runnable {

	private int totalStep;
	private int step;
	
	public Tortoise(int totalStep) {
		// TODO Auto-generated constructor stub
		this.totalStep = totalStep;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("------> 乌龟跑步开始:");
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
