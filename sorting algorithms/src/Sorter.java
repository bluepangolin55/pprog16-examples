import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
		
		int[] parts = new int[2*NTHREADS + 1];
		int size = (to - from) / (2*NTHREADS);
		for(int i=0; i<parts.length; i++){
			parts[i] = from + size*i;
		}
		parts[2*NTHREADS] = to;
		
		Lock[] locks = new Lock[2*NTHREADS];
		for(int i=0; i<2*NTHREADS; i++){
			locks[i] = new ReentrantLock();
		}
		
		for(int t=0; t<NTHREADS; t++){
			final int ID = t;
			final AtomicInteger end = new AtomicInteger(to);
			threads.add(new Thread(){
				public void run(){ 
					boolean swapped = true;
					int p = 2*ID;
					int next = (p+1) % NTHREADS;
					locks[p].lock();
					locks[next].lock();
					int i = parts[p] + 1;
					while(swapped){
						swapped = false;
						while(i < end.get()) {
							if(i == parts[next]){
								locks[p].unlock();
								p = next;
								next = (p+1) % NTHREADS;
								locks[next].lock();
							}
							if(data[i-1].compareTo(data[i]) > 0){
								swap(i-1, i);
								swapped = true;
							}
							i++;
						}
						end.decrementAndGet();
						i = 1;
					}
					locks[p].unlock();
					locks[next].unlock();
				}
			});
		}
		
//		for (int i = parts[ID] + 1; i < end; i++) {
//			if(data[i-1].compareTo(data[i]) > 0){
//				swap(i-1, i);
//				swapped = true;
//			}
//		}
		
		for(Thread t : threads){
			t.start();
		}
		
		for(Thread t : threads){
			try {
				t.join();
			} catch (InterruptedException e) {}
		}
		
		bubbleSort();

	}

	private void parallelBubbleSort() {
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
							int a = i - NTHREADS;
							int b = i;
							if(data[a].compareTo(data[b]) > 0){
								swap(a, b);
								swapped = true;
							}
						}
						end -= NTHREADS;
					}
				}
			});
		}
		
		for(Thread t : threads){
			t.start();
		}
		
		for(Thread t : threads){
			try {
				t.join();
			} catch (InterruptedException e) {}
		}
		bubbleSort();
	}
	
	private void swap(int a, int b){
		T temp = data[a];
		data[a] = data[b];
		data[b] = temp;
	}
}
