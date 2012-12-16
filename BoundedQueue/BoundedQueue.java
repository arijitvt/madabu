
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedQueue {
	ReentrantLock enqLock,deqLock;
	Condition notEmptyCondition, notFullCondition;
	AtomicInteger size;
	Node head,tail;
	int capacity;
	
	public BoundedQueue(int capacity){
		this.capacity = capacity;
		head = new Node(null);
		tail = head;
		size = new AtomicInteger(0);
		enqLock = new ReentrantLock();
		notFullCondition = enqLock.newCondition();
		deqLock= new ReentrantLock();
		notEmptyCondition = deqLock.newCondition();
	}
	
	public void enqMethod(Integer value){
//		System.out.println("Enq by thread : "+Thread.currentThread().getName());
		boolean mustWakeDequer = false;
		enqLock.lock();
		try{
			while(size.get() == capacity){
				try {
					notFullCondition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Node e = new Node(value);
			tail.next = tail = e;
			if(size.getAndIncrement() == 0){
				mustWakeDequer = true;
			}
		}finally{
			enqLock.unlock();
		}
		if(mustWakeDequer){
			deqLock.lock();
			try{
				notEmptyCondition.signalAll();				
			}finally{
				deqLock.unlock();
			}
		}
	}
	
	public Integer deqMethod(){
//		System.out.println("Deq by thread : "+Thread.currentThread().getName());
		Integer result = null;
		boolean mustWakeEnquer = false;
		deqLock.lock();
		try{
			while(size.get() == 0){
				try {
					notEmptyCondition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(size.getAndDecrement() == capacity){
				mustWakeEnquer = true;
			}
			result = head.next.value;
			head = head.next;
		}finally{
			deqLock.unlock();
		}
		
		if(mustWakeEnquer){
			enqLock.lock();
			try{
				notFullCondition.signalAll();
			}finally{
				enqLock.unlock();
			}
		}
		return result;
	}
}
