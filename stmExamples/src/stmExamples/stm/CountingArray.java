package stmExamples.stm;

import java.util.LinkedList;
import java.util.List;

import scala.concurrent.stm.Ref.View;
import scala.concurrent.stm.TArray;
import scala.concurrent.stm.japi.STM;

/**
 * Similar to the other example we demonstrate the use of transactional memory. 
 * He we don't just increment one counter but a whole array of counters. 
 */

public class CountingArray {
	
	public static final int N_THREADS = 128;
	public static final int N_ITERATIONS = 100;
	public static final int ARRAYSIZE = 1_000;
	
	public static TArray.View<Integer> transactionalArray;
	
	public static void main(String[] args) {
		// initialising our array of counters
		transactionalArray= STM.newTArray(ARRAYSIZE);
		for(int i=0; i<ARRAYSIZE; i++){
			transactionalArray.update(i, 0);
		}

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
		for(int i=0; i<ARRAYSIZE; i++){
			System.out.println(transactionalArray.refViews().apply(new Integer(i)).get());
		}
	}
	
	
	static class Counter extends Thread{
		
		@Override
		public void run(){
			for(int i = 0; i < N_ITERATIONS; i++){
				STM.atomic(new Runnable(){
					public void run(){
						for(int j = 0; j<ARRAYSIZE; j++){
							// Both versions (the commented and the uncommented) are valid: 
//							Integer old = transactionalArray.refViews().apply(new Integer(j)).get();
//							transactionalArray.update(j, old+1);
							View<Integer> v = transactionalArray.refViews().apply(new Integer(j));
							STM.increment(v, 1);
						}
					}
				});
			}
			
		}
		
	}

}
