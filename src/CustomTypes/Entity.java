package CustomTypes;

import java.util.Date;

public final class Entity {
	private String[] Holders;					// Stores the holder of the entity (singular)
	private String strEntityID = "";			// Stores the entity ID
	private String strType = "";				// Stores the entity type
	private String strInstitution = "";			// Stores the parent institution of the entity
	private String strStatus;					// Stores the entity status
	private String strNumber = "";				// Stores the entity number
	private Date OpenDate;						// Stores the opening date of the entity as a string
	private Date CloseDate;						// Stores the closing date of the entity as a string
	private String strDescription = "";			// Stores the entity description as a string
	private String strComments = "";			// Stores the entity comment as a string
	
	// INITIALISE
    public Entity(
    		String[] Holders,
    		String strEntityID,
			String strType,
			String strInstitution,
			String strStatus,
			String strNumber,
			Date OpenDate,
			Date CloseDate,
			String strDescription,
			String strComments) {
		  this.Holders = Holders;
		  this.strEntityID = strEntityID;
		  this.strType = strType;
		  this.strInstitution = strInstitution;
		  this.strStatus = strStatus;
		  this.strNumber = strNumber;
		  this.OpenDate = OpenDate;
		  this.CloseDate = CloseDate;
		  this.strDescription = strDescription;
		  this.strComments = strComments;
    }
    
    // GET
    public String[] GetHolderNames() {
    	return this.Holders;
    }
    
    public String GetEntityID() {
    	return this.strEntityID;
    }
    
    public String GetEntityTypeName() {
    	return this.strType;
    }
    
    public String GetInstitutionName() {
    	return this.strInstitution;
    }
    
    public String GetEntityStatusName() {
    	return this.strStatus;
    }
    
    public String GetEntityNumber() {
    	return this.strNumber;
    }
    
    public Date GetOpenDate() {
    	return this.OpenDate;
    }
    
    public Date GetCloseDate() {
    	return this.CloseDate;
    }
    
    public String GetDescription() {
    	return this.strDescription;
    }
    
    public String GetComment() {
    	return this.strComments;
    }
    
    // SET
    public void SetHolderNames(String[] Holders) {
    	this.Holders = Holders;
    }
    
    public void SetEntityID(String strEntityID) {
    	this.strEntityID = strEntityID;
    }
    
    public void SetEntityTypeName(String strType) {
    	this.strType = strType;
    }
    
    public void SetInstitutionName(String strInstitution) {
    	this.strInstitution = strInstitution;
    }
    
    public void SetEntityStatusName(String strStatus) {
    	this.strStatus = strStatus;
    }
    
    public void SetEntityNumber(String strNumber) {
    	this.strNumber = strNumber;
    }
    
    public void SetOpenDate(Date OpenDate) {
    	this.OpenDate = OpenDate;
    }
    
    public void SetCloseDate(Date CloseDate) {
    	this.CloseDate = CloseDate;
    }
    
    public void SetDescription(String strDescription) {
    	this.strDescription = strDescription;
    }
    
    public void SetComment(String strComments) {
    	this.strComments = strComments;
    }
}