package project1;

import java.util.ArrayList;

public class Patient implements PatientADT{
	private String id;
	private String name;
	private ArrayList<String> aces;

	//Constructs a new Patient
	public Patient()
	{
		id = null;
		name = null;
		aces = new ArrayList<String>();
	}
	
	//Constructs a new Patient with parameters
	public Patient(String id, String name)
	{
		this.id = id;
		this.name = name;
		aces = new ArrayList<String>();
	}
	
	//Sets id variable in Patient class
	public void setId(String id) {
		this.id = id;
		name = null;
		aces = new ArrayList<String>();
	}

	//Returns id variable in Patient class
	public String getId() {
		return id;
	}

	//Sets name variable in Patient class
	public void setName(String id) {
		name = id;
	}

	//Returns name variable in Patient class
	public String getName() {
		return name;
	}

	//Adds a string ace into aces array variable
	public void addACE(String ace) {
		if (!aces.contains(ace))
			aces.add(ace);
		
	}

	//Removes a string ace from aces array variable
	public void removeACE(String ace) {
		aces.remove(ace);
		
	}

	//Returns the array variable aces 
	public ArrayList<String> getACEs() {
		ArrayList<String> toReturn = aces;
		return toReturn;
	}

	//Formats the toString() method of Patient class
	public String toString() {
		String toReturn = this.getId() + "," + this.getName();
		toReturn += ","+aces.toString();
		return toReturn;
	}
	
	
}

