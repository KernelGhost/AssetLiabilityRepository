package CustomTypes;

import java.math.BigDecimal;

public class Insurance {
	private String EntityID = "";								// Stores the ID of the associated entity
	private BigDecimal InsuredAmount = BigDecimal.ZERO;			// Stores the amount insured by the insurance policy
	private BigDecimal InsurancePremiumPA = BigDecimal.ZERO;	// Stores the insurance premium paid per annum
	
	// INITIALISE
	public Insurance(String EntityID, BigDecimal InsuredAmount, BigDecimal InsurancePremiumPA) {
		this.EntityID = EntityID;
		this.InsuredAmount = InsuredAmount;
		this.InsurancePremiumPA = InsurancePremiumPA;
	}
	
	// GET
	public String GetEntityID() {
		return this.EntityID;
	}
	
	public BigDecimal GetInsuredAmount() {
		return this.InsuredAmount;
	}
	
	public BigDecimal GetPremium() {
		return this.InsurancePremiumPA;
	}
	
	// SET
	public void SetEntityID(String EntityID) {
		this.EntityID = EntityID;
	}
	
	public void SetInsuredAmount(BigDecimal InsuredAmount) {
		this.InsuredAmount = InsuredAmount;
	}
	
	public void SetPremium(BigDecimal InsurancePremiumPA) {
		this.InsurancePremiumPA = InsurancePremiumPA;
	}
}
