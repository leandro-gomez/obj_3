package personas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import org.aspectj.lang.JoinPoint;
import utils.Utils;

public aspect PersonaAspect {

	public ArrayList<Persona> personas = new ArrayList<Persona>();
	public int Persona.referenceCount = 0;
	public HashMap<Persona, ArrayList<String>> Persona.references = new HashMap<Persona, ArrayList<String>>();

	// POINTCUTS

	// When a instance variable is set
	pointcut fieldWrite(Persona target, Persona newValue) : 
		set(Persona personas.Persona..*) && args(newValue) && target(target);

	// When a Person is created
	pointcut personCreated(Persona aPerson) :
		initialization(Persona+.new(..)) && this(aPerson);

	// When a static main function is called
	pointcut mainCalled():
		call(static void Persona.main(..));

	pointcut runApplicationCalled():
		call(void Application.run());

	// ADVICES

	/**
	 * When a instance variable of type Person in class Person is set, then in
	 * the referenced Person is annotate who reference it. If the set field is
	 * referencing another Person instance, that references is removed from the
	 * old Person instance referenced.
	 * 
	 * @param person
	 * @param newReferenced
	 */
	before(Persona person, Persona newReferenced) : fieldWrite(person, newReferenced) {
		String field = getReferencerFieldName(thisJoinPoint);
		removeOldReferenced(person, field);
		addReferencer(newReferenced, person, field);
	}

	/**
	 * After call Persona main method, print all person references
	 */
	after(): mainCalled(){
		this.printPeople();
	}

	/*
	 * The aspect class knows all Person instances.
	 */
	after(Persona person) : personCreated(person) {
		this.personas.add(person);
	}

	/*
	 * After the Application run method is called, it shows all person instances
	 * and their references. (See PersonTest class to show its effects.)
	 */
	after(): runApplicationCalled(){
		this.printPeople();
	}

	// METHODS

	public static void removeOldReferenced(Persona person, String field) {
		Persona oldReferenced = getOldReferenced(person, field);
		if (oldReferenced != null) {
			removeReferencer(oldReferenced, person, field);
		}
	}

	public static void removeReferencer(Persona person, Persona referender,
			String field) {
		minusReferencesCount(person);
		ArrayList<String> fields = getReferences(person).get(referender);
		fields.remove(field);
		if (fields.isEmpty()) {
			getReferences(person).remove(referender);
		}
	}

	public static void addReferencer(Persona person, Persona referencer,
			String field) {
		if (!isReferencedBy(person, referencer)) {
			initializeReferencesBy(person, referencer);
		}
		if (!isReferendedByInField(person, referencer, field)) {
			plusReferencesCount(person);
		}
		addReferenceInField(person, referencer, field);
		printPersonReference(person, referencer, field);
	}

	public static void addReferenceInField(Persona person, Persona referencer,
			String field) {
		getReferences(person).get(referencer).add(field);
	}

	public static Persona getOldReferenced(Persona person, String field) {
		return (Persona) Utils.getField(person, field);
	}

	public static String getReferencerFieldName(JoinPoint joinPoint) {
		return joinPoint.getSignature().getName();
	}

	public static void initializeReferencesBy(Persona person, Persona referender) {
		getReferences(person).put(referender, new ArrayList<String>());
	}

	public static boolean isReferendedByInField(Persona person,
			Persona referender, String field) {
		return getReferences(person).get(referender).contains(field);
	}

	public static boolean isReferencedBy(Persona person, Persona referender) {
		return getReferences(person).containsKey(referender);
	}

	public static void plusReferencesCount(Persona person) {
		person.referenceCount++;
	}

	public static void minusReferencesCount(Persona person) {
		person.referenceCount--;
	}

	/*
	 * Pretty prints :)
	 */

	public static void printPersonReference(Persona person, Persona referender,
			String field) {
		Utils.prettyPrint(Utils.ANSI_YELLOW, referender
				+ " has a references of " + person + " in field " + field);
	}

	public void printPeople() {
		for (Persona person : this.personas) {
			Set<Persona> referenders = getReferences(person).keySet();
			if (referenders.isEmpty()) {
				Utils.prettyPrint(Utils.ANSI_RED, person + " is not referended");
			} else {
				Utils.prettyPrint(Utils.ANSI_GREEN, person + " is referenced "
						+ person.referenceCount + " time(s) by:");
				this.printReferenders("", referenders, person);
			}
		}
	}

	private void printReferenders(String indent, Set<Persona> referenders,
			Persona referended) {
		for (Persona person_key : referenders) {
			Utils.prettyPrint(Utils.ANSI_CYAN, indent + "  " + person_key
					+ " in field(s):");
			this.printFields("  ", getReferences(referended).get(person_key));
		}
	}

	private void printFields(String indent, ArrayList<String> fields) {
		for (String field : fields) {
			Utils.prettyPrint(Utils.ANSI_YELLOW, indent + "  " + field);
		}
	}

	public static HashMap<Persona, ArrayList<String>> getReferences(
			Persona person) {
		return person.references;
	}

}