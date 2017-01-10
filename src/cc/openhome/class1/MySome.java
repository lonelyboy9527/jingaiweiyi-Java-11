package cc.openhome.class1;

public class MySome implements Runnable {
	
	private volatile boolean isContinue = true;

	public void stop() {
		isContinue = false;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while (isContinue) {
			
		}

	}

}
