package com.multithread;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {

	public static void main(String[] args) {

		// Create a Logger object to log errors
		Logger log = Logger.getLogger("MyLogger");
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler("error.log", true);
			log.addHandler(fileHandler);
			SimpleFormatter formatter = new SimpleFormatter();
			fileHandler.setFormatter(formatter);
		} catch (IOException e) {
			System.out.println("Error creating log file");
			System.exit(1);
		}

		// Create an ExecutorService with 5 threads
		ExecutorService es = Executors.newFixedThreadPool(5);

		// Create 5 runnable tasks
		MyThread[] runnables = new MyThread[5];
		for (int i = 0; i < 5; i++) {
			runnables[i] = new MyThread(i, log);
			es.submit(runnables[i]);
		}

		// Wait for 10 seconds to shut down the server
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		// Request shutdown of all threads
		for (MyThread runnable : runnables) {
			runnable.requestShutdown();
		}

		// Wait for all threads to complete their current tasks
		es.shutdown();
		try {
			if (!es.awaitTermination(10, java.util.concurrent.TimeUnit.SECONDS)) {
				es.shutdownNow();
			}
		} catch (InterruptedException ex) {
			es.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}
}