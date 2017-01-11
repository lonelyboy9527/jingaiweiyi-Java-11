package cc.openhome.class2;

import java.util.concurrent.Executor;

public class DirectExecutor implements Executor {

	@Override
	public void execute(Runnable command) {
		// TODO Auto-generated method stub
		command.run();
	}

}
