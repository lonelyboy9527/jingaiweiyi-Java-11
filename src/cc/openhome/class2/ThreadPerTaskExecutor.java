package cc.openhome.class2;

import java.util.concurrent.Executor;

public class ThreadPerTaskExecutor implements Executor {

	@Override
	public void execute(Runnable command) {
		// TODO Auto-generated method stub
		new Thread().start();
	}

}
