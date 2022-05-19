package CustomTypes;
import java.util.Date;
import java.math.BigDecimal;

public class Transaction implements Comparable<Transaction> {
	private String TxnID = "";								// Stores the ID of the transaction
	private String EntityID = "";							// Stores the ID of the associated entity
	private Date Date;										// Stores the transaction date
	private String TxnCatg = "";							// Stores the transaction category
	private String Description = "";						// Stores the transaction description
	private BigDecimal Debit = BigDecimal.ZERO;				// Stores debits
	private BigDecimal Credit = BigDecimal.ZERO;			// Stores credits
	
	// INITIALISE
	public Transaction(
			String TxnID,
			String EntityID,
			Date Date,
			String TxnCatg,
			String Description,
			BigDecimal Debit,
			BigDecimal Credit) {
		this.TxnID = TxnID;
		this.EntityID = EntityID;
		this.Date = Date;
		this.TxnCatg = TxnCatg;
		this.Description = Description;
		this.Debit = Debit;
		this.Credit = Credit;
	}
	
	// GET
	public String GetTransactionID() {
		return this.TxnID;
	}
	
	public String GetEntityID() {
		return this.EntityID;
	}
	
	public Date GetDate() {
		return this.Date;
	}
	
	public String GetTransactionCategory() {
		return this.TxnCatg;
	}
	
	public String GetDescription() {
		return this.Description;
	}
	
	public BigDecimal GetDebit() {
		return this.Debit;
	}
	
	public BigDecimal GetCredit() {
		return this.Credit;
	}
	
	// SET
	public void SetTransactionID(String TxnID) {
		this.TxnID = TxnID;
	}
	
	public void SetEntityID(String EntityID) {
		this.EntityID = EntityID;
	}
	
	public void SetDate(Date Date) {
		this.Date = Date;
	}
	
	public void SetTransactionCategory(String TxnCatg) {
		this.TxnCatg = TxnCatg;
	}
	
	public void SetDescription(String Description) {
		this.Description = Description;
	}
	
	public void SetDebit(BigDecimal Debit) {
		this.Debit = Debit;
	}
	
	public void SetCredit(BigDecimal Credit) {
		this.Credit = Credit;
	}
	
	// Used for sorting Transactions by date.
	public int compareTo(Transaction o) {
		return this.GetDate().compareTo(o.GetDate());
	}	
}