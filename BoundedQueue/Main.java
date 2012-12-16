public class Main{
	public static Model model = new Model();

	public static void main(String args[]){	

// Number of threads
// Size of the queue
// Iterations done by each threads
		MeasureBQ mBQueue = new MeasureBQ(10,10,2);	
		mBQueue.startThreads();
		model.showModel();
		model.generateAbstractStateAndShow();
	}
}
