
public class Pair<A, B> {
	
	private A first;
	private B second;
	
	
	public Pair()
	{
		this.first = null;
		this.second = null;
		
	}
	public Pair(A first, B second)
	{
		this.first = first;
		this.second =second;
	}
	
	public A getFirst()
	{
		return first;
	}
	
	public B getSecond()
	{
		return second;
	}
	

}
