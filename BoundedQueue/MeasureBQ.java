public class MeasureBQ implements Runnable{

	Thread[] enqThreads;
	Thread[] deqThreads;
	
	int numberOfIterations;
	int noOfThreads;
	int queueSize;
	BoundedQueue bQueue;

	public MeasureBQ(int noOfThreads, int queueSize,int numberOfIterations){

		this.noOfThreads = noOfThreads;
		this.queueSize = queueSize;
		this.numberOfIterations = numberOfIterations;

		bQueue = new BoundedQueue(queueSize);

		enqThreads = new Thread[noOfThreads];
		deqThreads = new Thread[noOfThreads];

		for(int i =0 ; i< noOfThreads; ++i){
			enqThreads[i] = new Thread(this);
			enqThreads[i].setName("enq"+i);
			deqThreads[i] = new Thread(this);
			deqThreads[i].setName("deq"+i);
		}
	}

	public void startThreads(){
		for(int i =0 ; i< noOfThreads; ++i){
			enqThreads[i].start();
			deqThreads[i].start();
		}
		for(int i =0 ; i< noOfThreads; ++i){
			try{
				enqThreads[i].join();
				deqThreads[i].join();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}

	//@Override
	public void run(){
		System.out.println("Current Thread Name : "+Thread.currentThread().getName());
		for(int i = 0 ;  i < numberOfIterations ; ++i){	
			if(Thread.currentThread().getName().contains("enq")){
				bQueue.enqMethod(new Integer(i));
			}else{
				bQueue.deqMethod();
			}
		}
		System.out.println("Current Thread Name exiting : "+Thread.currentThread().getName());
	}
}
