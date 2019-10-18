package project1;
/*
 * CSCI 3381 -- Solution to Project #1
 * 
 */


public class TesterClass {

	public static void main(String[] args) {

		// create a new AceDataManager and preload with data from file
		AceDataManagerADT myData = new AceDataManager("./project1/data.txt","./data.txt");
		System.out.println(myData);
		String[] aceList = myData.getAceList();
		for (int i = 0; i < aceList.length; i++) {
			System.out.println(aceList[i]);
		}
		
		// retrieve a Patient and if not found create new patient, add an Ace
		Patient aPat = (Patient) myData.getPatient("741");
		if (aPat==null) {
			aPat = new Patient("741","Susie");
			myData.addPatient(aPat);			
		}
		aPat.addACE("Parental separation or divorce");
		myData.removePatient("5309");
		System.out.println(myData);
		
		// write the data to the file, so the next run will have any updated data
		// this will only be done once in a program and always when the program quits
		myData.writeToFile();
	}

}
