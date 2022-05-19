package CustomTypes;

import java.math.BigDecimal;

public class Share {
	private String EntityID = "";								// Stores the ID of the associated entity
	private BigDecimal CurrentUnitValue = BigDecimal.ZERO;		// Stores the current share price
	
	// INITIALISE
	public Share(String EntityID, BigDecimal CurrentUnitValue) {
		this.EntityID = EntityID;
		this.CurrentUnitValue = CurrentUnitValue;
	}
	
	// GET
	public String GetEntityID() {
		return this.EntityID;
	}
	
	public BigDecimal GetCurrentUnitValue() {
		return this.CurrentUnitValue;
	}
	
	// SET
	public void SetEntityID(String EntityID) {
		this.EntityID = EntityID;
	}
	
	public void SetCurrentUnitValue(BigDecimal CurrentUnitValue) {
		this.CurrentUnitValue = CurrentUnitValue;
	}
}