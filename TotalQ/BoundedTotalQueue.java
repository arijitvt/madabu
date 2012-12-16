
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedTotalQueue {
	ReentrantLock enqLock,deqLock;
	Condition notEmptyCondition, notFullCondition;
	AtomicInteger size;
	Node head,tail;
	int capacity;
	
	public BoundedTotalQueue(int capacity){
		this.capacity = capacity;
		head = new Node(null);
		tail = head;
		size = new AtomicInteger(0);
		enqLock = new ReentrantLock();
		notFullCondition = enqLock.newCondition();
		deqLock= new ReentrantLock();
		notEmptyCondition = deqLock.newCondition();
	}
	
	public void enqMethod(Integer value) throws EmptyException{
		enqLock.lock();
		try{
			while(size.get() == capacity){
				throw new EmptyException("Queue is Full");
			}
			Node e = new Node(value);
			tail.next = tail = e;
			size.getAndIncrement();
			
		}finally{
			enqLock.unlock();
		}
	}
	
	public Integer deqMethod() throws EmptyException{
		Integer result = null;
		deqLock.lock();
		try{
			while(size.get() == 0){
				throw new EmptyException("Queue is Empty");
			}
			size.getAndDecrement() ;
			result = head.next.value;
			head = head.next;
		}finally{
			deqLock.unlock();
		}
		return result;
	}
}
