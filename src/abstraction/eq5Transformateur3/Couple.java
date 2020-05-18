package abstraction.eq5Transformateur3;

public class Couple<T> {
	private T v1, v2;
	public Couple(T v1, T v2) { 
		this.v1=v1;
		this.v2=v2;
	}
	public T get1() {
		return this.v1; 
	}
	public T get2() {
		return this.v2;
	}
	
}
