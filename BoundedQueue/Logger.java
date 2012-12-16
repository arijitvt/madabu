import java.util.concurrent.locks.ReentrantLock;

public reviser Logger extends BoundedQueue{


	Model model = Main.model;

	ReentrantLock eLock = new ReentrantLock();
	ReentrantLock dLock = new ReentrantLock();

	
	public Logger(int capacity){
		super(capacity);
	}
	
	public void enqMethod(Integer newItem){
		//System.out.println("Enquing : "+newItem);
		eLock.lock();
	//	synchronized(model){
	//	model.modelLock.lock();
		State preState=extractState();
		try{
			super.enqMethod(newItem);
		}finally{
			State postState = extractState();
			model.addTransition(preState,postState,"ENQ");
	//		model.modelLock.unlock();
			eLock.unlock();
		}
	//	}
	}

	public  Integer deqMethod(){
		dLock.lock();
		int retVal;
	//	synchronized(model){
	//	model.modelLock.lock();
		State preState = extractState();
		try{
			retVal = super.deqMethod();			
		}finally{
			State postState = extractState();
			model.addTransition(preState,postState,"DEQ");
			dLock.unlock();
			//model.modelLock.unlock();
		}
		//}
		return new Integer(retVal);
	}

	public int getCapacity(){
		return this.capacity;
	}

	public int getSize(){
		return this.size.get();
	}
	
	public boolean isEmpty(){
		return this.size.get() == 0? true : false;
	}

	public State extractState(){
		State s = new State();
		s.add("isEmpty",""+isEmpty());
		s.add("size",""+getSize());
		s.add("capacity",""+getCapacity());		
	//	System.out.println("IsEmpty : "+s.isEmpty+"\tCapacity: "+s.capacity+"\tSize "+s.size);
		return s;
	}
}

