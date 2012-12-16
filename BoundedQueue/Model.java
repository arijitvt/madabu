import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Model{

	ReentrantLock modelLock = new ReentrantLock();
	class Row{
		State preCondition;
		State postCondition;
		String mutatorName;
		
		public Row(State preCondition, State postCondition, String mutatorName){
			this.preCondition = preCondition;
			this.postCondition = postCondition;
			this.mutatorName = mutatorName;	
		}
	}

	ArrayList<Row> transitionRow = new ArrayList<Row>();

	public void addTransition(State preCondition, State postCondition, String mutatorName){
		Row row = new Row(preCondition, postCondition, mutatorName);	

/************************
		State preCon = row.preCondition;
		State postCon = row.postCondition;
		System.out.print(preCon.isEmpty+","+preCon.size+","+preCon.capacity+"\t");
		System.out.print(row.mutatorName+"\t");
		System.out.println(postCon.isEmpty+","+postCon.size+","+postCon.capacity+"\t");

/************************/
		transitionRow.add(row);
	}

	public void showModel(){
		System.out.println("Showing Model : "+transitionRow.size());
		for(Row row : transitionRow){
			State preCon = row.preCondition;
			State postCon = row.postCondition;
			System.out.print("["+"<"+preCon.isEmpty+","+preCon.size+","+preCon.capacity+">\t");
			System.out.print(row.mutatorName+"\t");
			System.out.println("<"+postCon.isEmpty+","+postCon.size+","+postCon.capacity+">"+"]");
		
		}
	}

	public void generateAbstractStateAndShow(){
		for(Row row : transitionRow){
			State preState = row.preCondition;
			State postState = row.postCondition;
			System.out.print("["+getState(preState)+"\t");
			System.out.print(row.mutatorName+"\t");
			System.out.println(getState(postState)+"]");
		
		}
	}

	private String getState(State s){
		if(Boolean.parseBoolean(s.isEmpty) && Integer.parseInt(s.capacity) > 0 && Integer.parseInt(s.size)  == 0){
			return "s1";
		}else if(!Boolean.parseBoolean(s.isEmpty) && Integer.parseInt(s.capacity) > 0 && Integer.parseInt(s.size)  > 0){
			return "s2";
		}
		return "Unknown";
	}
}

