public reviser Logger extends BoundedQueue{
	Model model = new Model();
	
	public Logger(int capacity){
		super(capacity);
	}
	
	public  void enqMethod(int newItem){
		System.out.println("Enquing : "+newItem);
		State preState=extractState();
		try{
			super.enqMethod(newItem);
		}finally{
			State postState = extractState();
			model.addTransition(preState,postState,"");
		}
	}

	public  int deqMethod(){		
		State preState = extractState();
		int retVal;
		try{
			retVal = super.deqMethod();
			System.out.println("Dequing : "+retVal);
		}finally{
			State postState = extractState();
			model.addTransition(preState,postState,"");
		}
		return retVal;
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
		return s;
	}
}

