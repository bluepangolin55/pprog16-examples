import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Sorter<T extends Comparable<T>> {
	
	
	int NTHREADS = 16;

	T[] data;
	private int from;
	private int to;
	private boolean sorted;

	public Sorter(T[] data) {
		this.data = data;
		this.from = 0;
		this.to = data.length;
		this.sorted = false;
	}

	public boolean sort() {
//		bubbleSort();
//		parallelBubbleSort();
		parallelBubbleSortSlow();
		sorted = true;
		return true;
	}
	
	public boolean isSorted(){
		return sorted;
	}

	private void bubbleSort() {
		boolean swapped = true;
		int end = to;
		while(swapped){
			swapped = false;
				for (int i = 1; i < end; i++) {
					if(data[i-1].compareTo(data[i]) > 0){
						swap(i-1, i);
						swapped = true;
					}
				}
				end --;
		}
	}
	
	private void parallelBubbleSortSlow() {
		LinkedList<Thread> threads = new LinkedList<Thread>();
		
		final AtomicInteger end = new AtomicInteger(to);
		
		for(int t=0; t<NTHREADS; t++){
			final int ID = t;
			threads.add(new Thread(){
				public void run(){ 
					boolean swapped = true;
					int localEnd = to;
					while(swapped){
						swapped = false;
						for (int i = 1; i < localEnd; i++) {
//						for (int i = 1; i < end.get(); i++) {
							while(true){
								T a = data[i-1];
								T b = data[i];
	//							if(data[i-1].compareTo(data[i]) > 0){
								if(a.compareTo(b) > 0){
									synchronized(a){
										synchronized(b){
											if(a == data[i-1] && b == data[i]){
												swap(i-1, i);
												swapped = true;
												break;
											}
										}
									}
								}
								else{
									break;
								}
							}
						}
						end.decrementAndGet();
						localEnd --;
					}
				}
			});
		}
		
		for(Thread t : threads){
			t.run();
		}
		
		for(Thread t : threads){
			try {
				t.join();
			} catch (InterruptedException e) {}
		}
		
		bubbleSort();

	}

	private void parallelBubbleSort() {
		long time = System.currentTimeMillis();
		LinkedList<Thread> threads = new LinkedList<Thread>();
		
		for(int t=0; t<NTHREADS; t++){
			final int ID = t;
			threads.add(new Thread(){
				public void run(){
					boolean swapped = true;
					int end = to;
					while(swapped){
						swapped = false;
						for (int i = NTHREADS + ID; i < end; i += NTHREADS) {
							if(data[i - NTHREADS].compareTo(data[i]) > 0){
								swap(i - NTHREADS, i);
								swapped = true;
							}
						}
						end -= NTHREADS;
					}
				}
			});
		}
		
		System.out.println("setup: " + (System.currentTimeMillis() - time));
		time = System.currentTimeMillis();
		for(Thread t : threads){
			t.start();
		}
		
		for(Thread t : threads){
			try {
				t.join();
			} catch (InterruptedException e) {}
		}
		System.out.println("parallel: " + (System.currentTimeMillis() - time));
		time = System.currentTimeMillis();
		bubbleSort();
		System.out.println("sequential: " + (System.currentTimeMillis() - time));
	}
	
	private void swap(int a, int b){
		T temp = data[a];
		data[a] = data[b];
		data[b] = temp;
	}
}
