import java.util.LinkedList;
import java.util.concurrent.atomic.*;

class Main {

	public static int sum = 0;
	public static AtomicInteger atomicSum = new AtomicInteger(0);



	public static void main(String[] args) {
		LinkedList<Thread> threads = new LinkedList<Thread>();

		TASLock l = new TASLock();
		// TASLockWait l = new TASLockWait();
		// TASLockSynchronized l = new TASLockSynchronized();


		AtomicInteger a = null;

		test(a);

		for(int i=0; i<16; i++){
			threads.add(new Thread(){
				public void run(){

					for(int i=0; i<800000; i++){
						l.lock();
						// synchronized(l){
							sum ++;
						// }
						// atomicSum.incrementAndGet();
						l.unlock();
					}
				}
				}
			);
		}
		long time = System.currentTimeMillis();
		for(Thread t : threads){
			t.start();	
		}

		try{
			for(Thread t : threads){
				t.join();	
			}
		}
		catch(InterruptedException e){
		}
		System.out.println("Time: " + (System.currentTimeMillis() - time));

		System.out.println(sum);
		// System.out.println(atomicSum.get());
	}


	public static void test(AtomicInteger a){
		if(a != null && a.get() == 0){
			System.out.println("Kj;lajdsfl;ajsfl");
		}
	}

}