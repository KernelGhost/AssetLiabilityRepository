package CustomTypes;

import java.math.BigDecimal;

public class TermDeposit {
	private String EntityID = "";							// Stores the ID of the associated entity
	private BigDecimal OpeningBalance = BigDecimal.ZERO;	// Stores the opening balance of the term deposit
	private BigDecimal InterestAmount = BigDecimal.ZERO;	// Stores the total interest earnt by the term deposit
	private BigDecimal OtherCharges = BigDecimal.ZERO;		// Stores any government charges
	private BigDecimal BankFees = BigDecimal.ZERO;			// Stores any bank fees
	private BigDecimal InterestRate = BigDecimal.ZERO;		// Stores the interest rate of the term deposit
	
	// INITIALISE
	public TermDeposit(
			String EntityID,
			BigDecimal OpeningBalance,
			BigDecimal InterestAmount,
			BigDecimal OtherCharges,
			BigDecimal BankFees,
			BigDecimal InterestRate) {
		this.EntityID = EntityID;
		this.OpeningBalance = OpeningBalance;
		this.InterestAmount = InterestAmount;
		this.OtherCharges = OtherCharges;
		this.BankFees = BankFees;
		this.InterestRate = InterestRate;
	}
	
	// GET
	public String GetEntityID() {
		return this.EntityID;
	}
	
	public BigDecimal GetOpeningBalance() {
		return this.OpeningBalance;
	}
	
	public BigDecimal GetInterestAmount() {
		return this.InterestAmount;
	}
	
	public BigDecimal GetOtherCharges() {
		return this.OtherCharges;
	}
	
	public BigDecimal GetBankFees() {
		return this.BankFees;
	}
	
	public BigDecimal GetInterestRate() {
		return this.InterestRate;
	}
	
	// SET
	public void SetEntityID(String EntityID) {
		this.EntityID = EntityID;
	}
	
	public void SetOpeningBalance(BigDecimal OpeningBalance) {
		this.OpeningBalance = OpeningBalance;
	}
	
	public void SetInterestAmount(BigDecimal InterestAmount) {
		this.InterestAmount = InterestAmount;
	}
	
	public void SetOtherCharges(BigDecimal OtherCharges) {
		this.OtherCharges = OtherCharges;
	}
	
	public void SetBankFees(BigDecimal BankFees) {
		this.BankFees = BankFees;
	}
	
	public void SetInterestRate(BigDecimal InterestRate) {
		this.InterestRate = InterestRate;
	}
}
