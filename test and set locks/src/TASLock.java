import java.util.concurrent.atomic.*;

class TASLock {

	AtomicInteger locked = new AtomicInteger(0);

	public void lock(){
		while(locked.getAndSet(1) == 1){
			Thread.yield();
		}
	}

	public void unlock(){
		locked.set(0);
	}

}