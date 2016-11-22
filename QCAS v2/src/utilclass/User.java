package utilclass;

public class User {
	public String name = "Shelly";
	public int id = 0;
	
	public User(String name, int id) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.id = id;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
}
