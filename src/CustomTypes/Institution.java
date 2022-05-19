package CustomTypes;

public class Institution implements Comparable<Institution>{
	private String strName = "";		// Stores institution name
	private String strID = "";			// Stores institution ID
	private String strCode = "";		// Stores institution code
	private String strAddress = "";		// Stores institution address
	private String strComment = "";		// Stores institution comment
	
	// INITIALISE
	public Institution(
			String strName,
			String strID,
			String strCode,
			String strAddress,
			String strComment) {
		this.strName = strName;
		this.strID = strID;
		this.strCode = strCode;
		this.strAddress = strAddress;
		this.strComment = strComment;
	}
	
	// GET
	public String GetName() {
		return this.strName;
	}
	
	public String GetID() {
		return this.strID;
	}
	
	public String GetCode() {
		return this.strCode;
	}
	
	public String GetAddress() {
		return this.strAddress;
	}
	
	public String GetComment() {
		return this.strComment;
	}
	
	// SET
	public void SetName(String strName) {
		this.strName = strName;
	}
	
	public void SetID(String strID) {
		this.strID = strID;
	}
	
	public void SetCode(String strCode) {
		this.strCode = strCode;
	}
	
	public void SetAddress(String strAddress) {
		this.strAddress = strAddress;
	}
	
	public void SetComment(String strComment) {
		this.strComment = strComment;
	}
	
	// Used for sorting InstResults. Sorts by institution name.
	public int compareTo(Institution o) {
        return this.GetName().compareToIgnoreCase(o.GetName());
    }
}