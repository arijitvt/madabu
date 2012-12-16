import java.util.*;

public class State{
	
	String isEmpty;
	String size;
	String capacity;

	public void add(String key, String value){
		if(key.equals("isEmpty")){
			isEmpty = value;
		}else if(key.equals("size")){
			size = value;
		}else if(key.equals("capacity")){
			capacity = value;
		}	
	}
}
