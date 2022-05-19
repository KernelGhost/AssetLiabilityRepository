package CustomTypes;
import java.math.BigDecimal;
import java.util.Date;

public class ShareTransaction implements Comparable<ShareTransaction> {
	private Date Date;											// Stores the date of the transaction
	private String TxnID = "";									// Stores the ID of the share transaction
	private String EntityID = "";								// Stores the entity ID
	private String TxnSubtype = "";								// Stores the share transaction subtype
	private String Description = "";							// Stores the description
	private BigDecimal SharePrice = BigDecimal.ZERO;			// Stores the share price
	private BigDecimal BrokerageCharges = BigDecimal.ZERO;		// Stores the brokerage charges
	private BigDecimal OtherCharges = BigDecimal.ZERO;			// Stores the government charges
	private BigDecimal CapGainsTax = BigDecimal.ZERO;			// Stores the capital gains tax
	private BigDecimal DividendAmount = BigDecimal.ZERO;		// Stores the franked dividend amount
	private BigDecimal FrankingPercentage = BigDecimal.ZERO;	// Stores the franking percentage
	private float NumberShares = (float) 0;						// Stores the number of shares
	
	// INITIALISE
	public ShareTransaction(
			Date Date,
			String TxnID,
			String EntityID,
			String TxnSubtype,
			String Description,
			BigDecimal SharePrice,
			BigDecimal BrokerageCharges,
			BigDecimal OtherCharges,
			BigDecimal CapGainsTax,
			BigDecimal DividendAmount,
			BigDecimal FrankingPercentage,
			float NumberShares) {
		this.Date = Date;
		this.TxnID = TxnID;
		this.EntityID = EntityID;
		this.TxnSubtype = TxnSubtype;
		this.Description = Description;
		this.SharePrice = SharePrice;
		this.BrokerageCharges = BrokerageCharges;
		this.OtherCharges = OtherCharges;
		this.CapGainsTax = CapGainsTax;
		this.DividendAmount = DividendAmount;
		this.FrankingPercentage = FrankingPercentage;
		this.NumberShares = NumberShares;
	}
	
	// GET
	public Date GetDate() {
		return this.Date;
	}
	
	public String GetTransactionID() {
		return this.TxnID;
	}
	
	public String GetEntityID() {
		return this.EntityID;
	}
	
	public String GetTransactionSubtype() {
		return this.TxnSubtype;
	}
	
	public String GetDescription() {
		return this.Description;
	}
	
	public BigDecimal GetSharePrice() {
		return this.SharePrice;
	}
	
	public BigDecimal GetBrokerageCharges() {
		return this.BrokerageCharges;
	}
	
	public BigDecimal GetOtherCharges() {
		return this.OtherCharges;
	}
	
	public BigDecimal GetCapitalGainsTax() {
		return this.CapGainsTax;
	}
	
	public BigDecimal GetDividendAmount() {
		return this.DividendAmount;
	}
	
	public BigDecimal GetFrankingPercentage() {
		return this.FrankingPercentage;
	}
	
	public float GetNumberShares() {
		return this.NumberShares;
	}
	
	// SET
	public void SetDate(Date Date) {
		this.Date = Date;
	}
	
	public void SetTransactionID(String TxnID) {
		this.TxnID = TxnID;
	}
	
	public void SetEntityID(String EntityID) {
		this.EntityID = EntityID;
	}
	
	public void SetTransactionSubtype(String TxnSubtype) {
		this.TxnSubtype = TxnSubtype;
	}
	
	public void SetDescription(String Description) {
		this.Description = Description;
	}
	
	public void SetSharePrice(BigDecimal SharePrice) {
		this.SharePrice = SharePrice;
	}
	
	public void SetBrokerageCharges(BigDecimal BrokerageCharges) {
		this.BrokerageCharges = BrokerageCharges;
	}
	
	public void SetOtherCharges(BigDecimal OtherCharges) {
		this.OtherCharges = OtherCharges;
	}
	
	public void SetCapitalGainsTax(BigDecimal CapGainsTax) {
		this.CapGainsTax = CapGainsTax;
	}
	
	public void SetDividendAmount(BigDecimal DividendAmount) {
		this.DividendAmount = DividendAmount;
	}
	
	public void SetFrankingPercentage(BigDecimal FrankingPercentage) {
		this.FrankingPercentage = FrankingPercentage;
	}
	
	public void SetNumberShares(float NumberShares) {
		this.NumberShares = NumberShares;
	}

	// Used for sorting Transactions by date.
	public int compareTo(ShareTransaction o) {
		return this.GetDate().compareTo(o.GetDate());
	}
}