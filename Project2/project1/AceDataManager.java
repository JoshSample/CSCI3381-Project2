package project1;


import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class AceDataManager implements AceDataManagerADT{

	Map<String,PatientADT> adm = new HashMap<String,PatientADT>();
	private String fileName;
	private String fileName2;
	private static final String ACE_LIST = "Physical abuse,Sexual abuse,Emotional abuse,Physical neglect,Emotional neglect,Exposure to domestic violence,"+
			"Household substance abuse,Household mental illness,Parental separation or divorce,Incarcerated household member";

	//Constructs AceDataManager with fileName variable assigned to parameter
	public AceDataManager(String fn,String fn2) 
	{
		fileName = fn;
		fileName2 = fn2;
		readFile();
	}

	//Adds patient variable into adm HashMap variable
	public void addPatient(PatientADT p) {
		adm.put(p.getId(), p);

	}
	
	public void removePatient(String id) {
		adm.remove(id);
	}

	//Returns a patient or null if patient is not in the HashMap
	public PatientADT getPatient(String id) {
		if(adm.containsKey(id))
		{
			return adm.get(id);
		}
		return null;
	}

	//Returns an ArrayList<String> of risk factors based on aces in ArrayList<String> aces
	public ArrayList<String> getRiskFactors(ArrayList<String> aces) {
		ArrayList<String> riskfactors = new ArrayList<String>();
		if(aces.contains("Physical abuse"))
		{
			riskfactors.add("Potential risk of substance abuse and/or aggressive behavior from Physical abuse");
		}
		if(aces.contains("Sexual abuse"))
		{
			riskfactors.add("Potential risk of personality disorder, mood disorder and/or anxiety disorder from Sexual abuse");
		}
		if(aces.contains("Emotional abuse"))
		{
			riskfactors.add("Potential risk of substance abuse, mood disorder and/or anxiety disorder from Emotional abuse");
		}
		if(aces.contains("Physical neglect"))
		{
			riskfactors.add("Potential risk of substance abuse and/or chronic diseases from Physical neglect");
		}
		if(aces.contains("Emotional neglect"))
		{
			riskfactors.add("Potential risk of substance abuse, mood disorder and/or anxiety disorder from Emotional neglect");
		}
		if(aces.contains("Exposure to domestic violence"))
		{
			riskfactors.add("Potential risk of substance abuse, mood disorder, anxiety disorder, and/or aggressive behavior from Exposure to domestic violence");
		}
		if(aces.contains("Household substance abuse"))
		{
			riskfactors.add("Potential risk of chronic diseases, substance abuse and/or mood disorder from Household substance abuse exposure");
		}
		if(aces.contains("Household mental illness"))
		{
			riskfactors.add("Potential risk of mood disorder and/or chronic diseases from Household mental illness");
		}
		if(aces.contains("Parental separation"))
		{
			riskfactors.add("Potential risk of chronic diseases, substance abuse, mood disorder and/or anxiety disorder from Parental separation");
		}
		if(aces.contains("Incarcerated household member"))
		{
			riskfactors.add("Potential risk of substance abuse, mood disorder, and/or chronic diseases from Incarcerated household member");
		}
		//Judges severity of symptoms based on quantity of aces
		if(riskfactors.size() < 2)
		{
			riskfactors.add("Low range of symptom severity. Suggest short-term individual therapy.");
			return riskfactors;
		}
		if(riskfactors.size() < 6)
		{
			riskfactors.add("Medium range of symptom severity. Suggest treatment plan, group therapy, and individual thearpy.");
			return riskfactors;
		}
		riskfactors.add("High range of symptom severity. Suggest multidisciplinary team and adminstration to medical institution for intensive care.");
		return riskfactors;

	}
	public String[] getAceList () {
		return ACE_LIST.split(",");
	}

	//Reads lines in data.txt and adds the formated information into AceDataManager class that called the method
	private void readFile () {
		BufferedReader lineReader = null;
		try {
			FileReader fr = new FileReader(fileName);
			lineReader = new BufferedReader(fr);
			String line = null;
			while ((line = lineReader.readLine())!=null) {
				String[] token = line.split(",");
				String id = token[0];
				String name = token[1];
				this.addPatient(new Patient(id,name));
				PatientADT temp = this.getPatient(id);
				for( int i = 2; i < token.length; i++)
				{
					temp.addACE(token[i]);
				}			
			}
		} catch (Exception e) {
			System.err.println("there was a problem with the file reader, try different read type.");
			try {
				lineReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(fileName.substring(1))));
				String line = null;
				while ((line = lineReader.readLine())!=null) {
					String[] token = line.split(",");
					String id = token[0];
					String name = token[1];
					this.addPatient(new Patient(id,name));
					PatientADT temp = this.getPatient(id);
					for( int i = 2; i < token.length; i++)
					{
						temp.addACE(token[i]);
					}			
				}
			} catch (Exception e2) {
				System.err.println("there was a problem with the file reader, try again.  either no such file or format error");
			} finally {
				if (lineReader != null)
					try {
						lineReader.close();
					} catch (IOException e2) {
						System.err.println("could not close BufferedReader");
					}
			}			
		} finally {
			if (lineReader != null)
				try {
					lineReader.close();
				} catch (IOException e) {
					System.err.println("could not close BufferedReader");
				}
		}
	} // end of readFile method


	//Writes data from the aces HashMap into the data.txt file
	private void doWrite(String fn, String fn2) {
		PatientADT temp = new Patient();
		ArrayList<String> temp2 = new ArrayList<String>();
		try
		{
			//File file = new File(fn);
//			FileWriter fw = new FileWriter(file.getAbsolutePath());
			
			FileWriter fw = new FileWriter(fn);
			BufferedWriter myOutfile = new BufferedWriter(fw);			

			for(String key: adm.keySet()){
				temp = adm.get(key);
				temp2 = temp.getACEs();
				myOutfile.write ( temp.getId() + "," + temp.getName());
				for(int i = 0; i < temp2.size(); i++) 
				{
					myOutfile.write ("," + temp2.get(i) );

				}
				myOutfile.write ("\n");

			}

			myOutfile.flush();
			myOutfile.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("Didn't save to " + fn+" try again");
			try
			{
				//File file = new File(fn);
//				FileWriter fw = new FileWriter(file.getAbsolutePath());
				
				FileWriter fw = new FileWriter(fn2);
				BufferedWriter myOutfile = new BufferedWriter(fw);			

				for(String key: adm.keySet()){
					temp = adm.get(key);
					temp2 = temp.getACEs();
					myOutfile.write ( temp.getId() + "," + temp.getName());
					for(int i = 0; i < temp2.size(); i++) 
					{
						myOutfile.write ("," + temp2.get(i) );

					}
					myOutfile.write ("\n");

				}

				myOutfile.flush();
				myOutfile.close();
			}
			catch (Exception e2) {
				e.printStackTrace();
				System.err.println("Didn't save to " + fn);
			}			
		}
	}	

	//Calls the doWrite file of the AceDataManager class with the private variable fileName passed
	public void writeToFile() 
	{
		doWrite(fileName,fileName2);

	}

	//Calls the doWrite file of the AceDataManager class with the parameter variable fn passed
	public void writeToFile(String fn, String fn2) 
	{
		doWrite(fn,fn2);

	}

	//Formats the toString() method of Patient class
	public String toString() 
	{
		String toReturn = null;
		ArrayList<String> temp = new ArrayList<String>();
		PatientADT tempPatient = new Patient();
		for(String key: adm.keySet()){
			if(toReturn == null)
			{
				toReturn = "Id " + key +" :: "+ adm.get(key);
				tempPatient = this.getPatient(key);
				temp = tempPatient.getACEs();
				toReturn += " " + this.getRiskFactors(temp) + "\n";
				continue;
			}
			toReturn += "Id " + key +" :: "+ adm.get(key);
			tempPatient = this.getPatient(key);
			temp = tempPatient.getACEs();
			toReturn += " " + this.getRiskFactors(temp) + "\n";

		}

		return toReturn;
	}

	public void writeToFile(String fn) {
		doWrite(fn,null);
		
	}
}
