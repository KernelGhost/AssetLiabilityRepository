package CustomTypes;
import java.util.Date;

public class Service implements Comparable<Service>{
	private String strID = "";				// Stores the service ID
	private String strDescription = "";		// Stores the service description
	private String strEntity = "";			// Stores the parent entity ID
	private String strFrequency = "";		// Stores the service frequency
	private String strUserID = "";			// Stores the service user ID
	private String strPassword = "";		// Stores the service password
	private String strContact = "";			// Stores the service contact
	private Date Expiry;					// Stores the service expiry date as a string
	private String strServType = "";		// Stores the service type
	
	// INITIALISE
	public Service(String servicetype,
					  String description,
					  String entity_id,
					  String frequency,
					  String userid,
					  String password,
					  String contact,
					  Date expiry,
					  String ID) {
		this.strServType = servicetype;
		this.strDescription = description;
		this.strEntity = entity_id;
		this.strFrequency = frequency;
		this.strUserID = userid;
		this.strPassword = password;
		this.strContact = contact;
		this.Expiry = expiry;
		this.strID = ID;
	}
	
	// GET
	public String GetServiceID() {
		return this.strID;
	}
	
	public String GetDescription() {
		return this.strDescription;
	}
	
	public String GetEntityID() {
		return this.strEntity;
	}
	
	public String GetFrequency() {
		return this.strFrequency;
	}
	
	public String GetUserID() {
		return this.strUserID;
	}
	
	public String GetPassword() {
		return this.strPassword;
	}
	
	public String GetContact() {
		return this.strContact;
	}
	
	public Date GetExpiry() {
		return this.Expiry;
	}
	
	public String GetServiceType () {
		return this.strServType;
	}
	
	// SET
	public void SetServiceID(String strID) {
		this.strID = strID;
	}
	
	public void SetDescription(String strDescription) {
		this.strDescription = strDescription;
	}
	
	public void SetInstitutionName(String strEntityID) {
		this.strEntity = strEntityID;
	}
	
	public void SetFrequency(String strFrequency) {
		this.strFrequency = strFrequency;
	}
	
	public void SetUserID(String strUserID) {
		this.strUserID = strUserID;
	}
	
	public void SetPassword(String strPassword) {
		this.strPassword = strPassword;
	}
	
	public void SetContact(String strContact) {
		this.strContact = strContact;
	}
	
	public void SetExpiry(Date Expiry) {
		this.Expiry = Expiry;
	}
	
	public void SetServiceType (String strServType) {
		this.strServType = strServType;
	}
	
	/* Used for sorting ServResults. Sorts by service type. */
	public int compareTo(Service o) {
		return this.GetServiceType().compareToIgnoreCase(o.GetServiceType());
    }
}