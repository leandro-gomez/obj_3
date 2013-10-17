package personas;

public class Persona {
	public String name;
	public Persona friend1;
	public Persona friend2;
	public Persona friend3;

	public Persona(String name) {
		this.name = name;		
	}

	public static void main(String[] args) {
		Persona p = new Persona("Juan");
		Persona p1 = new Persona("Pedro");
		Persona p2 = new Persona("Diego");
		Persona p3 = new Persona("Lean");
		p.friend1 = p1;
		p.friend1 = p2;
		p.friend3 = p3;
		p1.friend1 = p2;
		p1.friend2 = p2;

	}

	public String toString() {
		return this.name;
	}

}
