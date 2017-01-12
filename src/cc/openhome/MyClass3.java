package cc.openhome;

import java.util.concurrent.*;

import cc.openhome.class3.Consumer3;
import cc.openhome.class3.Producer3;

public class MyClass3 {
	public static void exp1() {
		/* CopyOnWriteArrayList操作了List接口，
		 * 
		 * 这个类等实例在写入操作时（add()，set()等），内部会建立新数组，并复制原有数组索引的参考，
		 * 然后在新数组上进行写入操作，写入完成后，再将内部原参考旧数组的变量参考至新数组。
		 * 
		 * 对写入而言，这是个很耗资源对操作，然而在使用迭代器时，写入不会影响迭代器已参考的对象。
		 * 对于一个很少进行写入操作，而使用迭代器频繁的情景下，可以使用CopyOnWriteArrayList提高迭代器迭操作的效率。
		 * 
		 * 
		 * CopyOnWriteArraySet 操作了Set接口，内部使用CopyOnWriteArrayList来完成Set的各种操作，
		 * 因此，一些特性与CopyOnWriteArrayList是相同的。
		 * 
		 * 
		 * BlockingQueue是Queue的子接口，新定义了 put()和take()等方法，
		 * 线程若调用put()方法，在队列已满的情况下会被阻断，
		 * 线程若调用take()方法，在队列为空的情况下会被阻断。
		 * 
		 * 有了这个特性，在 11.1.6节中的 生产者与消费者的例子，就不用自行设计 Clerk类，
		 * 可以直接使用 BlockingQueue。
		 * 
		 * 例子：
		 * */
		BlockingQueue();
		
		
		/* ConcurrentMap 是Map的子接口，其定义了 putIfAbsent()、remove()与 replace()等方法。
		 * 这些方法都是 原子（Atomic）操作。
		 * 
		 * putIfAbsent()：在键对象不存在 ConcurrentMap中时，才可置入键/值对象，否则返回键对应的值对象。
		 * remove()：只有在键对象存在，且对应的值对象等于指定的值对象，才将键/值对象移除。
		 * replace()：两个版本：一个版本是 只有键对象存在，且对应的值对象等于指定的值对象，才将值对象置换。
		 * 					另一个版本：是在键对象存在时，将值对象置换。
		 * 
		 * 扩展：
		 * ConcurrentHashMap是 ConcurrentMap的操作类，
		 * ConcurrentNavigableMap是 ConcurrentMap子接口，其操作类为 ConcurrentSkipListMap，可视为支持并行操作的 TreeMap版本。
		 * */
		
	} 
	public static void BlockingQueue() {
		System.out.println("BlockingQueue -> 多线程的队列的存取操作");
		BlockingQueue queue = new ArrayBlockingQueue(1);// 设置容量为1
		new Thread(new Producer3(queue)).start();
		new Thread(new Consumer3(queue)).start();
		
		/* BlockingQueue还有其他操作，以及BlockingQueue子接口及相关操作类。*/
	}
}
