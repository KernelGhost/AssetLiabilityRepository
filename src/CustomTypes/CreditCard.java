package CustomTypes;

import java.math.BigDecimal;

public class CreditCard {
	private String EntityID = "";						// Stores the ID of the associated entity
	private BigDecimal CardLimit = BigDecimal.ZERO;		// Stores the limit of the credit card
	
	// INITIALISE
	public CreditCard(String EntityID, BigDecimal CardLimit) {
		this.EntityID = EntityID;
		this.CardLimit = CardLimit;
	}
	
	// GET
	public String GetEntityID() {
		return this.EntityID;
	}
	
	public BigDecimal GetCardLimit() {
		return this.CardLimit;
	}
	
	// SET
	public void SetEntityID(String EntityID) {
		this.EntityID = EntityID;
	}
	
	public void SetCardLimit(BigDecimal CardLimit) {
		this.CardLimit = CardLimit;
	}
}