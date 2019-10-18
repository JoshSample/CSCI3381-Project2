package project1;

import java.util.ArrayList;

public interface PatientADT {
	public void setId (String id);  // sets the unique identifier of this patient
	public String getId (); // gets the unique identifier of this patient
	public void setName (String id);  // sets the name of this patient
	public String getName (); // gets the name of this patient	
	public void addACE (String ace); //Adds an ACE to this patient, unless it already exists
	public void removeACE (String ace); //Removes an ACE from this patient
	public ArrayList<String> getACEs (); // returns an arraylist of this patient's ACEs
	public String toString(); //Returns a string representation of this patient
}

