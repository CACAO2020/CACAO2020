package abstraction.eq7Distributeur2;


public class Pair<T1,T2> {
	
	private T1 p1;
	private T2 p2;
	
	public Pair(T1 p1, T2 p2)
	{
	  this.p1 = p1;
	  this.p2 = p2;
	}
	
	public void setValue(T1 a, T2 b)
	{
	  this.p1 = a;
	  this.p2 = b;
	} 
	
	public T1 getFirstValue() {
		return this.p1;
	}
	
	public T2 getSecondValue() {
		return this.p2;
	}
	  
} 