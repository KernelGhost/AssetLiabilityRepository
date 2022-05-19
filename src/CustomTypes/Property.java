package CustomTypes;

import java.math.BigDecimal;

public class Property {
	private String EntityID = "";								// Stores the ID of the associated entity
	private String Address = "";								// Stores the address of the property
	private BigDecimal PurchaseAmount = BigDecimal.ZERO;		// Stores the purchase amount of the property
	private BigDecimal SolicitorFeesBuy = BigDecimal.ZERO;		// Stores solicitor fees (buy)
	private BigDecimal SolicitorFeesSell = BigDecimal.ZERO;		// Stores solicitor fees (sell)
	private BigDecimal GovtChargesBuy = BigDecimal.ZERO;		// Stores government charges (buy)
	private BigDecimal GovtChargesSell = BigDecimal.ZERO;		// Stores government charges (sell)
	private BigDecimal AgentFeesBuy = BigDecimal.ZERO;			// Stores agent fees (buy)
	private BigDecimal AgentFeesSell = BigDecimal.ZERO;			// Stores agent fees (sell)
	private BigDecimal SoldAmount = BigDecimal.ZERO;			// Stores the amount the property was sold for
	private BigDecimal CapGainsTax = BigDecimal.ZERO;			// Stores the capital gains tax paid on sale of the property
	private BigDecimal CurrentValue = BigDecimal.ZERO;			// Stores the current value of the property
	
	// INITIALISE
	public Property(
			String EntityID,
			String Address,
			BigDecimal PurchaseAmount,
			BigDecimal SolicitorFeesBuy,
			BigDecimal SolicitorFeesSell,
			BigDecimal GovtChargesBuy,
			BigDecimal GovtChargesSell,
			BigDecimal AgentFeesBuy,
			BigDecimal AgentFeesSell,
			BigDecimal SoldAmount,
			BigDecimal CapGainsTax,
			BigDecimal CurrentValue) {
		this.EntityID = EntityID;
		this.Address = Address;
		this.PurchaseAmount = PurchaseAmount;
		this.SolicitorFeesBuy = SolicitorFeesBuy;
		this.SolicitorFeesSell = SolicitorFeesSell;
		this.GovtChargesBuy = GovtChargesBuy;
		this.GovtChargesSell = GovtChargesSell;
		this.AgentFeesBuy = AgentFeesBuy;
		this.AgentFeesSell = AgentFeesSell;
		this.SoldAmount = SoldAmount;
		this.CapGainsTax = CapGainsTax;
		this.CurrentValue = CurrentValue;
	}
	
	// GET
	public String GetEntityID() {
		return this.EntityID;
	}
	
	public String GetAddress() {
		return this.Address;
	}
	
	public BigDecimal GetPurchaseAmount() {
		return this.PurchaseAmount;
	}
	
	public BigDecimal GetSolicitorFeesBuy() {
		return this.SolicitorFeesBuy;
	}
	
	public BigDecimal GetSolicitorFeesSell() {
		return this.SolicitorFeesSell;
	}
	
	public BigDecimal GetGovernmentChargesBuy() {
		return this.GovtChargesBuy;
	}
	
	public BigDecimal GetGovernmentChargesSell() {
		return this.GovtChargesSell;
	}
	
	public BigDecimal GetAgentFeesBuy() {
		return this.AgentFeesBuy;
	}
	
	public BigDecimal GetAgentFeesSell() {
		return this.AgentFeesSell;
	}
	
	public BigDecimal GetSoldAmount() {
		return this.SoldAmount;
	}
	
	public BigDecimal GetCapitalGainsTax() {
		return this.CapGainsTax;
	}
	
	public BigDecimal GetCurrentValue() {
		return this.CurrentValue;
	}
	
	// SET
	public void SetEntityID(String EntityID) {
		this.EntityID = EntityID;
	}
	
	public void SetAddress(String Address) {
		this.Address = Address;
	}
	
	public void SetPurchaseAmount(BigDecimal PurchaseAmount) {
		this.PurchaseAmount = PurchaseAmount;
	}
	
	public void SetSolicitorFeesBuy(BigDecimal SolicitorFeesBuy) {
		this.SolicitorFeesBuy = SolicitorFeesBuy;
	}
	
	public void SetSolicitorFeesSell(BigDecimal SolicitorFeesSell) {
		this.SolicitorFeesSell = SolicitorFeesSell;
	}
	
	public void SetGovernmentChargesBuy(BigDecimal GovtChargesBuy) {
		this.GovtChargesBuy = GovtChargesBuy;
	}
	
	public void SetGovernmentChargesSell(BigDecimal GovtChargesSell) {
		this.GovtChargesSell = GovtChargesSell;
	}
	
	public void SetAgentFeesBuy(BigDecimal AgentFeesBuy) {
		this.AgentFeesBuy = AgentFeesBuy;
	}
	
	public void SetAgentFeesSell(BigDecimal AgentFeesSell) {
		this.AgentFeesSell = AgentFeesSell;
	}
	
	public void SetSoldAmount(BigDecimal SoldAmount) {
		this.SoldAmount = SoldAmount;
	}
	
	public void SetCapitalGainsTax(BigDecimal CapGainsTax) {
		this.CapGainsTax = CapGainsTax;
	}
	
	public void SetCurrentValue(BigDecimal CurrentValue) {
		this.CurrentValue = CurrentValue;
	}
}
