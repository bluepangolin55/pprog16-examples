import java.util.concurrent.atomic.*;

class TASLockWait {

	AtomicInteger locked = new AtomicInteger(0);

	public void lock(){
		while(locked.getAndSet(1) == 1){
			synchronized(this){
				try{
					wait();
				} catch(InterruptedException e){
				}
			}
		}
	}

	public void unlock(){
		locked.set(0);
		synchronized(this){
			notify();
		}
	}

}