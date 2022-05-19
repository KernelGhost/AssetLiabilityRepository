package CustomTypes;

import java.util.Date;

public final class DisplayEntity implements Comparable<DisplayEntity> {
	private String strHolder = "";		// Stores the holder of the entity (singular)
	private String strEntType = "";		// Stores the entity type
	private String strInst = "";		// Stores the parent institution of the entity
	private String strStat = "";		// Stores the entity status
	private String strEntNum = "";		// Stores the entity number
	private String strCurVal = "";		// Stores the current value of the entity
	private Date OpenDate;				// Stores the opening date of the entity as a string
	private Date CloseDate;				// Stores the closing date of the entity as a string
	private String strID = "";			// Stores the entity ID
	
	// INITIALISE
    public DisplayEntity(
    		String strHolder,
			String strEntType,
			String strInst,
			String strStat,
			String strEntNum,
			String strCurVal,
			Date strODate,
			Date strCDate,
			String strID) {
		  this.strHolder = strHolder;
		  this.strEntType = strEntType;
		  this.strInst = strInst;
		  this.strStat = strStat;
		  this.strEntNum = strEntNum;
		  this.strCurVal = strCurVal;
		  this.OpenDate = strODate;
		  this.CloseDate = strCDate;
		  this.strID = strID;
    }
    
    // GET
    public String GetEntityID() {
    	return this.strID;
    }
    
    public String GetHolder() {
    	return this.strHolder;
    }
    
    public String GetEntityType() {
    	return this.strEntType;
    }
    
    public String GetInstitutionName() {
    	return this.strInst;
    }
    
    public String GetEntityStatus() {
    	return this.strStat;
    }
    
    public String GetEntityNumber() {
    	return this.strEntNum;
    }
    
    public String GetCurrentValue() {
    	return this.strCurVal;
    }
    
    public Date GetOpenDate() {
    	return this.OpenDate;
    }
    
    public Date GetCloseDate() {
    	return this.CloseDate;
    }
    
    // SET
    public void SetEntityID(String EntityID) {
    	this.strID = EntityID;
    }
    
    public void SetHolder(String Holder) {
    	this.strHolder = Holder;
    }
    
    public void SetEntityType(String EntityType) {
    	this.strEntType = EntityType;
    }
    
    public void SetInstitutionName(String Institution) {
    	this.strInst = Institution;
    }
    
    public void SetEntityStatus(String Status) {
    	this.strStat = Status;
    }
    
    public void SetEntityNumber(String EntityNumber) {
    	this.strEntNum = EntityNumber;
    }
    
    public void SetCurrentValue(String CurrentValue) {
    	this.strCurVal = CurrentValue;
    }
    
    public void SetOpenDate(Date OpenDate) {
    	this.OpenDate = OpenDate;
    }
    
    public void SetCloseDate (Date CloseDate) {
    	this.CloseDate = CloseDate;
    }
    
    /* Used for sorting EntityResults. First sorts by holder name, then by entity type, then by
     * parent institution and finally by entity status. */
    public int compareTo(DisplayEntity o) {
        int result1 = this.GetHolder().compareToIgnoreCase(o.GetHolder());
        int result2 = this.GetEntityType().compareToIgnoreCase(o.GetEntityType());
        int result3 = this.GetInstitutionName().compareToIgnoreCase(o.GetInstitutionName());
        int result4 = o.GetEntityStatus().compareToIgnoreCase(this.GetEntityStatus());			// Caveat: Flipped to put open entities above closed entities
        
        if (result1 == 0) {
        	if (result2 == 0) {
        		if (result3 == 0) {
        			return result4;
        		} else {
        			return result3;
        		}
        	} else {
        		return result2;
        	}
        } else {
        	return result1;
        }
    }
}