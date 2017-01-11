package cc.openhome.class2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.concurrent.Executor;

public class Pages {
	private URL[] urls;
	private String[] fileNames;
	private Executor executor;
	
	public Pages(URL[] urls, String[] fileNames, Executor executor) {
		// TODO Auto-generated constructor stub
		this.urls = urls;
		this.fileNames = fileNames;
		this.executor = executor;
	}
	
	public void download() {
		for (int i = 0; i < urls.length; i++) {
			final int index = i;
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						dump(
							urls[index].openStream(), 
							new FileOutputStream(fileNames[index])
						);
						
					} catch (Exception e) {
						// TODO: handle exception
						throw new RuntimeException(e);
					}
				}
			});
		}
	}
	private void dump(InputStream src, OutputStream dest) throws IOException {
		try (InputStream input = src; OutputStream output = dest) {
			byte[] data = new byte[1024];
			int length = -1;
			while ((length = input.read(data)) != -1) {
				output.write(data, 0, length);
			}
		}
	}
}
