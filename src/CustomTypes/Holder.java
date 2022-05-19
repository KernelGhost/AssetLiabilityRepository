package CustomTypes;

import java.util.Date;

public class Holder implements Comparable<Holder>{
	private String strName = "";	// Stores the holder name
	private Date DOB;				// Stores the holder DOB as a string
	private String strTFN = "";		// Stores the holder TFN
	private String strID = "";		// Stores the holder ID
	
	// INITIALISE
	public Holder(
			String strName,
			Date DOB,
			String strTFN,
			String strID) {
		this.strName = strName;
		this.DOB = DOB;
		this.strTFN = strTFN;
		this.strID = strID;
	}
	
	// GET
	public String GetName() {
		return this.strName;
	}
	
	public Date GetDOB() {
		return this.DOB;
	}
	
	public String GetTFN() {
		return this.strTFN;
	}
	
	public String GetID() {
		return this.strID;
	}
	
	// SET
	public void GetName(String strName) {
		this.strName = strName;
	}
	
	public void SetDOB(Date DOB) {
		this.DOB = DOB;
	}
	
	public void SetTFN(String strTFN) {
		this.strTFN = strTFN;
	}
	
	public void SetID(String strID) {
		this.strID = strID;
	}
	
	/* Used for sorting HolderResults. Sorts by holder name. */
	public int compareTo(Holder o) {
        return this.GetName().compareToIgnoreCase(o.GetName());
    }
}