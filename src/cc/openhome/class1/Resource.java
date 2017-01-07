package cc.openhome.class1;

public class Resource {
	private String name;
	private int resource;
	
	public Resource(String name, int resource) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.resource = resource;
	}
	
	public String getName() {
		return name;
	}
	
	synchronized int doSome() {
		return ++resource;
	}
	
	public synchronized void cooperate(Resource resource) {
		resource.doSome();
		System.out.printf("%s 整合 %s 的资源\n", this.name, resource.getName());
	}
}
