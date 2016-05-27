package stmExamples.stm;


import java.util.LinkedList;
import java.util.List;

/**
 * This example demonstrates the use of transactional memory on a trivial example: 
 * incrementing a shared counter in parallel. 
 * Incrementing a shared counter with multiple threads can obviously lead to 
 * race conditions. 
 * We solve this by making every increment operation a transaction. 
 */


import scala.concurrent.stm.Ref;
import scala.concurrent.stm.japi.STM;

public class Counting {
	
	public static final int N_THREADS = 128;
	public static final int N_ITERATIONS = 1_000;
	
	public static Ref.View<Integer> transactionalCounter;
	
	public static void main(String[] args) {
		// initialising our shared counter
		transactionalCounter = STM.newRef(new Integer(0));

		// creating and starting the threads
		List<Thread> threads = new LinkedList<Thread>();
		for(int i = 0; i < N_THREADS; i++){
			threads.add(new Counter());
		}
		for(Thread t : threads){
			t.start();
		}
		for(Thread t : threads){
			try {
				t.join();
			} catch (InterruptedException e) { }
		}
		
		// printing the output
		System.out.println(transactionalCounter.get());
	}
	
	
	static class Counter extends Thread{
		
		@Override
		public void run(){
			for(int i = 0; i < N_ITERATIONS; i++){
				STM.atomic(new Runnable(){
					public void run(){
						// Both versions (the commented and the uncommented) are valid: 
//						transactionalCounter.set(transactionalCounter.get() + 1);
						STM.increment(transactionalCounter, 1);
					}
				});
			}
			
		}
		
	}

}
