package CustomTypes;

import java.math.BigDecimal;

public class Loan {
	private String EntityID = "";						// Stores the ID of the associated entity
	private BigDecimal LoanAmount = BigDecimal.ZERO; 	// Stores the principal of the loan
	private BigDecimal CurrentValue = BigDecimal.ZERO;	// Stores the amount remaining to be paid on the loan
	
	// INITIALISE
	public Loan(String EntityID, BigDecimal LoanAmount, BigDecimal CurrentValue) {
		this.EntityID = EntityID;
		this.LoanAmount = LoanAmount;
		this.CurrentValue = CurrentValue;
	}
	
	// SET
	public String GetEntityID() {
		return this.EntityID;
	}
	
	public BigDecimal GetLoanAmount() {
		return this.LoanAmount;
	}
	
	public BigDecimal GetCurrentValue() {
		return this.CurrentValue;
	}
	
	// GET
	public void SetEntityID(String EntityID) {
		this.EntityID = EntityID;
	}
	
	public void SetLoanAmount(BigDecimal LoanAmount) {
		this.LoanAmount = LoanAmount;
	}
	
	public void SetCurrentValue(BigDecimal CurrentValue) {
		this.CurrentValue = CurrentValue;
	}
}
