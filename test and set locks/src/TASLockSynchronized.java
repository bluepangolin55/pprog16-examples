import java.util.concurrent.atomic.*;

class TASLockSynchronized {

	int locked = 0;

	public synchronized void lock(){
		while(locked == 1){
			try{
				wait();
			} catch(InterruptedException e){}
		}
		locked = 1;
	}

	public synchronized void unlock(){
		locked = 0;
		notify();
	}

}