public class Main{
	public static Model model = new Model();

	public static void main(String args[]){	

// Number of threads
// Size of the queue
// Iterations done by each threads
		System.out.println("Args Length and value : "+args.length+":"+args[0]);
		MeasureBQ mBQueue = new MeasureBQ(100,10,20);	
		mBQueue.startThreads();
		model.showModel();
		model.generateAbstractStateAndShow();
	}
}
