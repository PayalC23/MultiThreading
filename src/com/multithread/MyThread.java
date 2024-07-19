package com.multithread;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyThread implements Runnable {
	 private int id;
	 
	   
	    private Logger logger;
	    private volatile boolean shutdownRequested;
	   // private final Object lock = new Object();
	    
	    public MyThread(int id, Logger logger) {
	        this.id = id;
	       // this.scanner = scanner;
	        this.logger = logger;
	        this.shutdownRequested = false;
	      
	    }

	    public void requestShutdown() {
	        shutdownRequested = true;
	    }
	@Override
	public void run() {
		System.out.println("Thread " + id + " started");
      
		try {
	            while (!shutdownRequested) {
	                // Simulate client's request
	                String request = generateRandomRequest();

	                // Handle different types of requests
	                String response = handleRequest(request);

	                // Send back the response
	                System.out.println("Response from Thread " + id + ": " + response);
	                Thread.sleep(1000); // Simulate some delay
	            }
	        } catch (Exception e) {
	            logger.log(Level.SEVERE, "Error processing request", e);
	        } finally {
	            System.out.println("Thread " + id + " finished");
	        }
	    }
	 private String generateRandomRequest() {
	        String[] requests = {"Welcome", "add 2 3", "multiply 4 5", "unknown"};
	        return requests[(int) (Math.random() * requests.length)];
	    }
	 private String handleRequest(String request) {
	        try {
	            // Handle simple text-based requests
	            if (request.startsWith("Welcome")) {
	                return "MultiThreading!";
	            } else if (request.startsWith("add")) {
	                // Handle complex operation: addition
	                String[] parts = request.split(" ");
	                int num1 = Integer.parseInt(parts[1]);
	                int num2 = Integer.parseInt(parts[2]);
	                return "Result: " + (num1 + num2);
	            } else if (request.startsWith("multiply")) {
	                // Handle complex operation: multiplication
	                String[] parts = request.split(" ");
	                int num1 = Integer.parseInt(parts[1]);
	                int num2 = Integer.parseInt(parts[2]);
	                return "Result: " + (num1 * num2);
	            } else {
	                return "Unknown request";
	            }
	        } catch (NumberFormatException e) {
	            logger.log(Level.WARNING, "Malformed request", e);
	            return "Error: malformed request";
	        } catch (Exception e) {
	            logger.log(Level.SEVERE, "Internal processing error", e);
	            return "Error: internal processing error";
	        }
	    }
		
	}
