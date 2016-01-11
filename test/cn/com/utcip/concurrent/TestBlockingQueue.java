package cn.com.utcip.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestBlockingQueue {

	public static void main(String[] args) {
		ExecutorService rexec = Executors.newFixedThreadPool(5);
		ExecutorService fexec = Executors.newFixedThreadPool(5);
		BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
		for (int i = 1; i <= 50; i++) {
			ReadThread read = new ReadThread(queue, i);
			rexec.execute(read);
			FetchThread fetch = new FetchThread(queue);
			fexec.execute(fetch);
		}
		rexec.shutdown();
		fexec.shutdown();


	}

}

class ReadThread implements Runnable{
	private BlockingQueue<String> queue;
	private int no;
	public ReadThread(BlockingQueue<String> queue,int no){
		this.no = no;
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			String msg = "任务 " + no;
			queue.put(msg);
			System.out.println("写入："+msg);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}

class FetchThread implements Runnable{
	private BlockingQueue<String> queue;
	public FetchThread(BlockingQueue<String> queue){
		this.queue = queue;
	}
	@Override
	public void run() {
		try {
			String msg = queue.take();
			System.out.println("执行："+msg);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}
