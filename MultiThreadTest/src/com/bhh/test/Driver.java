/**  
 * @Title: Driver.java
 * @Package: sayhi
 * @Description: TODO
 * @author: Bhh
 * @date: 2016年7月28日 下午5:17:48
 * @version: V1.0  
 */
package com.bhh.test;

import java.sql.Timestamp;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @moudle: Driver
 * @version:v1.0
 * @Description: TODO
 * @author: Bhh
 * @date: 2016年7月28日 下午5:17:48
 * 
 */
public class Driver { // ...
	static{
		String curDir = Class.class.getClass().getResource("/").getPath();
		System.err.println(curDir);
		PropertyConfigurator.configure( curDir + "log4j.properties" );
	}
	public static Logger logger = Logger.getLogger(Driver.class);

	private ConcurrentHashMap<Integer, String> conHashMap;
	/**
	 * 
	 * <p>
	 * Title: main
	 * </p>
	 * <p>
	 * author : Bhh
	 * </p>
	 * <p>
	 * date : 2016年2月22日 下午2:58:38
	 * </p>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CountDownLatch startSignal = new CountDownLatch(1);
			CountDownLatch doneSignal = new CountDownLatch(10);
			logger.info("the threads are still not prepared, please wait a moment."); // don't
																					// let
																					// run	
			Timestamp startTime = null;
			for (int i = 0; i < 10; ++i) { // create and start threads
				if (i % 2 == 0) {
					new Worker1(startSignal, doneSignal, "线程" + i).start();
				} else {
					new Worker2(startSignal, doneSignal, "线程" + i).start();
				}
			}
			startTime = new Timestamp(System.currentTimeMillis());
			logger.info("all the threads are prepared now ."
					+ startTime.getTime());
			startSignal.countDown(); // let all threads proceed																// yet
			doneSignal.await(); // wait for all to finish
			Timestamp endTime = new Timestamp(System.currentTimeMillis());
			logger.info("all the threads are end now ."
					+ endTime.getTime());
			logger.info("Total Elapsed Time is "
					+ (endTime.getTime() - startTime.getTime()));
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}

class Worker1 extends Thread {
	public static Logger logger = Logger.getLogger(Worker1.class);
	private final CountDownLatch startSignal;
	private final CountDownLatch doneSignal;

	Worker1(CountDownLatch startSignal, CountDownLatch doneSignal,
			String threadName) {
		this.startSignal = startSignal;
		this.doneSignal = doneSignal;
		this.setName(threadName);
		logger.info("Thread : " + this.getName() + " is prepared now.");
	}

	public void run() {
		try {
			startSignal.await();
			Timestamp startTime = new Timestamp(System.currentTimeMillis());
			logger.info("Thread : " + this.getName() + " starts from here and The Time is ."
					+ startTime.getTime());
			doWork();
			Timestamp endTime = new Timestamp(System.currentTimeMillis());
			logger.info("Thread : " + this.getName() + " ends now and The time is "
					+ endTime.getTime());
			logger.info("Thread : " + this.getName() + " Elapsed Time is "
					+ (endTime.getTime() - startTime.getTime()));
			doneSignal.countDown();
		} catch (InterruptedException ex) {
		} // return;
	}

	void doWork() {
		for (int i = 0; i < 1000; ++i) {
			long v = System.nanoTime();
			logger.info("ThreadName:" + this.getName() + "----count is ---" + v);
		}
	}
}

class Worker2 extends Thread {
	public static Logger logger = Logger.getLogger(Worker2.class);
	private final CountDownLatch startSignal;
	private final CountDownLatch doneSignal;

	Worker2(CountDownLatch startSignal, CountDownLatch doneSignal,
			String threadName) {
		this.startSignal = startSignal;
		this.doneSignal = doneSignal;
		this.setName(threadName);
		logger.info("Thread : " + this.getName() + " is prepared now.");
	}

	public void run() {
		try {
			startSignal.await();
			Timestamp startTime = new Timestamp(System.currentTimeMillis());
			logger.info("Thread : " + this.getName() + " starts from here and The Time is ."
					+ startTime.getTime());
			doWork();
			Timestamp endTime = new Timestamp(System.currentTimeMillis());
			logger.info("Thread : " + this.getName() + " ends now and The time is "
					+ endTime.getTime());
			logger.info("Thread : " + this.getName() + " Elapsed Time is "
					+ (endTime.getTime() - startTime.getTime()));
			doneSignal.countDown();
		} catch (InterruptedException ex) {
		} // return;
	}

	void doWork() {
		for (int i = 0; i < 1000; ++i) {
			System.nanoTime();
			logger.info("ThreadName:" + this.getName() + "----count is ---" + i);
		}
	}
}
