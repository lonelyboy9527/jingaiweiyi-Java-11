package cc.openhome.class2;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

import java.util.List;

public class SubDir extends RecursiveAction { // 继承自RecursiveAction

	Path path; // 地址
	String pattern; // 文件后缀名
	
	public SubDir(Path path, String pattern) {
		// TODO Auto-generated constructor stub
		this.path = path;
		this.pattern = pattern;
	}
	@Override
	protected void compute() { // 操作compute()方法
		// TODO Auto-generated method stub
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
			List<SubDir>subDirs = new java.util.ArrayList<>();
			for (Path subPath : stream) {
				if (Files.isDirectory(subPath)) { // 如果是文件夹，分解为子任务收集起来，线程来执行这个子任务。
					subDirs.add(new SubDir(subPath, pattern));
				} else if (subPath.toString().endsWith(pattern)) { // 如果是文档，看看是否符搜素条件
					// 没有下一级文件夹，匹配后缀，如果是，直接打印
					System.out.println(subPath.toString());
				}
			}
			// ForkJoinPool会为每个查询子文件夹的子任务分配线程进行运算
			ForkJoinTask.invokeAll(subDirs); // 执行所有子任务，所有子任务完成后返回
		} catch (Exception e) {
			// TODO: handle exception
			// 略过无法存储的目录
		}
	}

}
