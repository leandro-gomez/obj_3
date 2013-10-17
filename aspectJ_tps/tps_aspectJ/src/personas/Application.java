package personas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import utils.Utils;

public class Application {

	private HashMap<String, Persona> people;

	public Application(String[] args) {
		this.people = new HashMap<String, Persona>();
		ArrayList<String> names = Utils.toArrayList(args);
		while (names.contains("end")) {
			names.remove("end");
		}
		for (String name : names) {
			this.createPerson(name);
		}
	}

	public void run() throws IllegalArgumentException, IllegalAccessException {
		this.printInitialMessage();
		this.printCreatePeopleInstructions();
		this.createPeopleLoop();
		this.printPersonFriendsInstructions();
		this.createRelationshipsLoop();
	}

	public static void main(String[] args) throws IllegalArgumentException,
			IllegalAccessException {

		Application application = new Application(args);
		application.run();

		// Persona.main(args);
	}

	private void createPeopleLoop() {
		while (true) {
			String peopleNames = "";
			boolean correct = true;
			try {
				BufferedReader bufferRead = new BufferedReader(
						new InputStreamReader(System.in));
				peopleNames = bufferRead.readLine();

			} catch (IOException e) {
				Utils.prettyPrint(Utils.ANSI_RED, "Something went wrong, please try again");				
				correct = false;
			}
			if (correct) {
				boolean enterEnd = this.createPeopleByString(peopleNames);
				if (enterEnd) {
					break;
				}
			}

		}
	}

	private boolean createPeopleByString(String peopleNames) {
		for (String name : peopleNames.split(" ")) {
			if (name.equals("end")) {
				return true;
			}
			if (!name.equals("")) {
				this.createPerson(name);
			}

		}
		return false;
	}

	private void createPerson(String name) {
		if (!this.people.containsKey(name)) {
			this.people.put(name, new Persona(name));			
			Utils.prettyPrint(Utils.ANSI_CYAN , "Created " + name);
		} else {
			Utils.prettyPrint(Utils.ANSI_CYAN , name + " already exists");			
		}
	}

	private void createRelationshipsLoop() throws IllegalArgumentException,
			IllegalAccessException {
		while (true) {
			String command = "";
			boolean correct = true;
			try {
				BufferedReader bufferRead = new BufferedReader(
						new InputStreamReader(System.in));
				command = bufferRead.readLine();

			} catch (IOException e) {
				Utils.prettyPrint(Utils.ANSI_RED,
						"Something went wrong, please try again");				
				correct = false;
			}
			if (correct) {
				ArrayList<String> commands = this.formatCommand(command);
				if (commands.size() == 1 && commands.get(0).equals("end")) {
					break;

				} else if (commands.size() != 3) {					
					Utils.prettyPrint(Utils.ANSI_RED, "Invalid command '" + command + "'.");
					Utils.prettyPrint(Utils.ANSI_CYAN,"Enter: <name> <friend[1|2|3]> <name>");					
					continue;
				}
				this.createRelationship(commands);

			}

		}
	}

	private ArrayList<String> formatCommand(String command) {
		ArrayList<String> commands = Utils.toArrayList(command.split(" "));
		while (commands.contains("")) {
			commands.remove("");
		}
		return commands;
	}

	private void createRelationship(ArrayList<String> commands)
			throws IllegalArgumentException, IllegalAccessException {
		if (this.people.containsKey((commands.get(0)))) {
			Persona person = this.people.get(commands.get(0));
			Field field;
			try {
				field = person.getClass().getDeclaredField(commands.get(1));
			} catch (NoSuchFieldException | SecurityException e) {
				Utils.prettyPrint(Utils.ANSI_RED, "Can't find field "
						+ commands.get(1));
				return;
			}
			if (this.people.containsKey(commands.get(2))) {
				Persona newFriend = this.people.get(commands.get(2));
				// TODO: This could be better, or not?			
				String fieldName = field.getName();
				if (fieldName.equals("friend1")) {
					person.friend1 = newFriend;
				} else if (fieldName.equals("friend2")) {
					person.friend2 = newFriend;
				} else if (fieldName.equals("friend3")) {
					person.friend3 = newFriend;
				} else {
					Utils.prettyPrint(Utils.ANSI_RED, "can't match "
							+ fieldName);
				}
				// field.set(person, newFriend); // This avoid the aspect!
				Utils.prettyPrint(Utils.ANSI_CYAN, person + " is friend of "
						+ newFriend);
			} else {
				Utils.prettyPrint(Utils.ANSI_RED,
						"Can't find " + commands.get(2) + " person.");
				return;
			}
		} else {
			Utils.prettyPrint(Utils.ANSI_RED, "Can't find " + commands.get(0)
					+ " person.");
		}
	}

	private void printPersonFriendsInstructions() {
		ArrayList<String> messages = new ArrayList<String>();
		messages.add("Now mark the relationships");
		messages.add("Each person can be friend of 3 persons. A new friend removes a old friend. :)");
		messages.add("Enter 'end' when you finish.");
		Utils.prettyPrint(Utils.ANSI_GREEN, messages);
		messages.clear();
		messages.add("Enter: <name> <friend[1|2|3]> <name>");
		messages.add("e.g.: lean friend1 daniel");
		messages.add("These are the valid names:");
		Utils.prettyPrint(Utils.ANSI_YELLOW, messages);
		messages.clear();
		for (String name : this.people.keySet()) {
			messages.add(name);
			messages.add(" -- ");
		}
		messages.add("\n");
		Utils.prettyPrint(Utils.ANSI_CYAN, messages, false);
	}

	private void printCreatePeopleInstructions() {
		ArrayList<String> messages = new ArrayList<String>();		
		messages.add("Please, enter the name of the people, if you repeat, it wont be re-created.");
		messages.add("Enter 'end' when you finish.");
		Utils.prettyPrint(Utils.ANSI_GREEN, messages);
	}

	private void printInitialMessage() {
		ArrayList<String> messages = new ArrayList<String>();
		messages.add("Hello, this is a RELP to use my project:");
		messages.add("Have fun!");
		Utils.prettyPrint(Utils.ANSI_GREEN, messages);
	}

}
