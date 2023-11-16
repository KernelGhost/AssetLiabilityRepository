import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import CustomTypes.CreditCard;
import CustomTypes.Entity;
import CustomTypes.Holder;
import CustomTypes.Institution;
import CustomTypes.Insurance;
import CustomTypes.Loan;
import CustomTypes.Property;
import CustomTypes.Service;
import CustomTypes.Share;
import CustomTypes.ShareTransaction;
import CustomTypes.TermDeposit;
import CustomTypes.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DatabaseHandler{
	private Connection connection;
	private String database_url;
	
	public DatabaseHandler(String URL) {
		database_url = URL;
	}
	
	public void Connect() throws SQLException, SQLInvalidAuthorizationSpecException {
		// Establish the connection with the database
		connection = DriverManager.getConnection(database_url);
	}
	
	public void Terminate() throws SQLException {
		// Terminate the connection with the database
		connection.close();
	}
	
	private void ClosePreparedStatement(PreparedStatement prepared_statement) {
		// Closing a prepared statement automatically closes the associated result set
		if (prepared_statement != null) {
			try {
				prepared_statement.close();
			} catch (SQLException e) {
				MessageBox.Error("There was an error closing a prepared statement when communicating with the database.", "Database Handler");
			}
		}
	}
	
	private Date ParseDate(String date_string) {
		Date Date;
		if (date_string == null) {
			Date = null;
		} else {
			try {
				Date = new SimpleDateFormat(Main.MariaDB_DATE_FORMAT).parse(date_string);
			} catch (ParseException e) {
				MessageBox.Error("There was a database related error parsing a date.", "Date Parser");
				Date = null;
			}
		}
		
		return Date;
	}
	
	public String[] GetAllEntityTypes() throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Domn WHERE DomnName = 'EntityType';");
		ResultSet result_set = statement.executeQuery();
		
		String AllEntities[] = null;
		int intCtr = 0;
		
		if (result_set.next()) {
			result_set.last();
			AllEntities = new String[result_set.getRow()];
			result_set.first();
			
			do {
				AllEntities[intCtr] = result_set.getString("DomnValue");
				intCtr++;
			} while (result_set.next());
		}
		
		ClosePreparedStatement(statement);
		
		return AllEntities;
	}
	
	public String[] GetAllStatusNames() throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Domn WHERE DomnName = 'EntityStatus';");
		ResultSet result_set = statement.executeQuery();
		
		String[] AllStatuses = null;
		int intCtr = 0;
		
		if (result_set.next()) {
			result_set.last();
			AllStatuses = new String[result_set.getRow()];
			result_set.first();
			
			do {
				AllStatuses[intCtr] = result_set.getString("DomnValue");
				intCtr++;
			} while (result_set.next());
		}
		
		ClosePreparedStatement(statement);
		
		return AllStatuses;
	}
	
	public String[] GetAllServiceTypes() throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Domn WHERE DomnName = 'ServiceType';");
		ResultSet result_set = statement.executeQuery();
		
		String[] ServiceTypes = null;
		int intCtr = 0;
		
		if (result_set.next()) {
			result_set.last();
			ServiceTypes = new String[result_set.getRow()];
			result_set.first();
			
			do {
				ServiceTypes[intCtr] = result_set.getString("DomnValue");
				intCtr++;
			} while (result_set.next());
		}
		
		ClosePreparedStatement(statement);
		
		return ServiceTypes;
	}
	
	public String[] GetAllTransactionCategories() throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Domn WHERE DomnName = 'TxnCatg';");
		ResultSet result_set = statement.executeQuery();
		
		String[] AllCategories = null;
		int intCtr = 0;
		
		if (result_set.next()) {
			result_set.last();
			AllCategories = new String[result_set.getRow()];
			result_set.first();
			do {
				AllCategories[intCtr] = result_set.getString("DomnValue");
				intCtr++;
			} while (result_set.next());
		}
		
		ClosePreparedStatement(statement);
		
		return AllCategories;
	}
	
	public String[] GetAllShareTransactionCategories() throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Domn WHERE DomnName = 'ShareTxnType';");
		ResultSet result_set = statement.executeQuery();
		
		String[] AllShareCategories = null;
		int intCtr = 0;
		
		if (result_set.next()) {
			result_set.last();
			AllShareCategories = new String[result_set.getRow()];
			result_set.first();
			do {
				AllShareCategories[intCtr] = result_set.getString("DomnValue");
				intCtr++;
			} while (result_set.next());
		}
		
		ClosePreparedStatement(statement);
		
		return AllShareCategories;
	}
	
	public Holder[] GetAllHolders() throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Holder;");
		ResultSet result_set = statement.executeQuery();
		
		Holder[] AllHolders = null;
		int intCtr = 0;
		
		if (result_set.next()) {
			result_set.last();
			AllHolders = new Holder[result_set.getRow()];
			result_set.first();
			
			do {
				Date HolderDOB = ParseDate(result_set.getString("Holder_DOB"));
				AllHolders[intCtr] = new Holder(
					result_set.getString("Holder_Name"),
					HolderDOB,
					result_set.getString("Holder_TFN"),
					result_set.getString("Holder_ID")
				);
				intCtr++;
			} while (result_set.next());
		}
		
		ClosePreparedStatement(statement);
		
		return AllHolders;
	}
	
	public Institution[] GetAllInstitutions() throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Institution;");
		ResultSet result_set = statement.executeQuery();
		
		Institution[] AllInstitutions = null;
		int intCtr = 0;
		
		if (result_set.next()) {
			result_set.last();
			AllInstitutions = new Institution[result_set.getRow()];
			result_set.first();
			
			do {
				AllInstitutions[intCtr] = new Institution(
					result_set.getString("Name"),
					result_set.getString("Institution_ID"),
					result_set.getString("Code"),
					result_set.getString("Address"),
					result_set.getString("Comments")
				);
				intCtr++;
			} while (result_set.next());
		}
		
		ClosePreparedStatement(statement);
		
		return AllInstitutions;
	}
	
	public String GetEntityTypeIDFromTypeName (String EntityTypeName) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Domn WHERE DomnName = 'EntityType' AND DomnValue = ?;");
		statement.setString(1, EntityTypeName);
		ResultSet result_set = statement.executeQuery();
		
		String EntityTypeID = null;
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			EntityTypeID = result_set.getString("DomnId");
		}
		
		ClosePreparedStatement(statement);
		
		return EntityTypeID;
	}
	
	public String GetStatusIDFromStatusName (String StatusName) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Domn WHERE DomnName = 'EntityStatus' AND DomnValue = ?;");
		statement.setString(1, StatusName);
		ResultSet result_set = statement.executeQuery();
		
		String StatusID = null;
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			StatusID = result_set.getString("DomnId");
		}
		
		ClosePreparedStatement(statement);
		
		return StatusID;
	}
	
	public String GetServiceTypeIDFromServiceType (String ServiceType) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Domn WHERE DomnName = 'ServiceType' AND DomnValue = ?;");
		statement.setString(1, ServiceType);
		ResultSet result_set = statement.executeQuery();
		
		String ServiceTypeID = null;
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			ServiceTypeID = result_set.getString("DomnId");
		}
		
		ClosePreparedStatement(statement);
		
		return ServiceTypeID;
	}
	
	public String[] GetLinkedEntityIDFromEntityID (String EntityID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM EntityHistory WHERE Entity_ID = ?;");
		statement.setString(1, EntityID);
		ResultSet result_set = statement.executeQuery();
		
		String[] LinkedEntities = null;
		int intCtr = 0;
		
		if (result_set.next()) {
			result_set.last();
			LinkedEntities = new String[result_set.getRow()];
			result_set.first();
			
			do {
				LinkedEntities[intCtr] = result_set.getString("Linked_Entity_ID");
				intCtr++;
			} while (result_set.next());
		}
		
		ClosePreparedStatement(statement);
		
		return LinkedEntities;
	}
	
	public String[] GetEntityIDFromLinkedEntityID (String LinkedEntityID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM EntityHistory WHERE Linked_Entity_ID = ?;");
		statement.setString(1, LinkedEntityID);
		ResultSet result_set = statement.executeQuery();
		
		String[] Entities = null;
		int intCtr = 0;
		
		if (result_set.next()) {
			result_set.last();
			Entities = new String[result_set.getRow()];
			result_set.first();
			
			do {
				Entities[intCtr] = result_set.getString("Entity_ID");
				intCtr++;
			} while (result_set.next());
		}
		
		ClosePreparedStatement(statement);
		
		return Entities;
	}
	
	public Holder GetHolderFromName(String HolderName) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Holder WHERE Holder_Name = ?;");
		statement.setString(1, HolderName);
		ResultSet result_set = statement.executeQuery();
		
		Holder holder_result = null;
		
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			Date HolderDOB = ParseDate(result_set.getString("Holder_DOB"));
			holder_result = new Holder(
				result_set.getString("Holder_Name"),
				HolderDOB,
				result_set.getString("Holder_TFN"),
				result_set.getString("Holder_ID")
			);
		}
		
		ClosePreparedStatement(statement);
		
		return holder_result;
	}
	
	public Institution GetInstitutionFromName(String InstitutionName) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Institution WHERE Name = ?;");
		statement.setString(1, InstitutionName);
		ResultSet result_set = statement.executeQuery();
		
		Institution institution_result = null;
		
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			institution_result = new Institution(
				result_set.getString("Name"),
				result_set.getString("Institution_ID"),
				result_set.getString("Code"),
				result_set.getString("Address"),
				result_set.getString("Comments")
			);
		}
		
		ClosePreparedStatement(statement);
		
		return institution_result;
	}
	
	public Entity GetEntityFromID (String EntityID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM HolderEntity INNER JOIN Holder ON HolderEntity.Holder_ID = Holder.Holder_ID WHERE Entity_ID = ?;");
		statement.setString(1, EntityID);
		ResultSet result_set = statement.executeQuery();
		
		ArrayList<String> ArrListHolderNames = new ArrayList<String>();
		
		if (result_set.next()) {
			do {
				ArrListHolderNames.add(result_set.getString("Holder_Name"));
			} while (result_set.next());
		} else {
			ArrListHolderNames = null;
		}
		
		ClosePreparedStatement(statement);
		
		String[] HolderNames = ArrListHolderNames.toArray(new String[0]);
		
		statement = connection.prepareStatement("SELECT * FROM Entity WHERE Entity_ID = ?;");
		statement.setString(1, EntityID);
		result_set = statement.executeQuery();
		
		Entity entity_result = null;
		
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			Date OpeningDate = ParseDate(result_set.getString("Entity_Start_Date"));
			Date ClosingDate = ParseDate(result_set.getString("Entity_End_Date"));
			entity_result = new Entity(
				HolderNames,
				result_set.getString("Entity_ID"),
				GetEntityTypeNameFromEntityTypeID(result_set.getString("Entity_Type")),
				GetInstitutionFromInstitutionID(result_set.getString("Institution_ID")).GetName(),
				GetStatusNameFromStatusID(result_set.getString("Entity_Status")),
				result_set.getString("Entity_Number"),
				OpeningDate,
				ClosingDate,
				result_set.getString("Description"),
				result_set.getString("Comments")
			);
		}
		
		ClosePreparedStatement(statement);
		
		return entity_result;
	}
	
	public Holder GetHolderFromHolderID (String HolderID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Holder WHERE Holder_ID = ?;");
		statement.setString(1, HolderID);
		ResultSet result_set = statement.executeQuery();
		
		Holder result = null;
		
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			Date HolderDOB = ParseDate(result_set.getString("Holder_DOB"));
			result = new Holder(
				result_set.getString("Holder_Name"),
				HolderDOB,
				result_set.getString("Holder_TFN"),
				result_set.getString("Holder_ID")
			);
			
		}
		
		ClosePreparedStatement(statement);
		
		return result;
	}
	
	public Institution GetInstitutionFromInstitutionID (String InstitutionID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Institution WHERE Institution_ID = ?;");
		statement.setString(1, InstitutionID);
		ResultSet result_set = statement.executeQuery();
		
		Institution result = null;
		
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			result = new Institution(
					result_set.getString("Name"),
					result_set.getString("Institution_ID"),
					result_set.getString("Code"),
					result_set.getString("Address"),
					result_set.getString("Comments"));
		}
		
		ClosePreparedStatement(statement);
		
		return result;
	}
	
	public String GetStatusNameFromStatusID (String StatusID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Domn WHERE DomnName = 'EntityStatus' AND DomnId = ?;");
		statement.setString(1, StatusID);
		ResultSet result_set = statement.executeQuery();
		
		String strStatusName = null;
		
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			strStatusName = result_set.getString("DomnValue");
		}
		
		ClosePreparedStatement(statement);
		
		return strStatusName;
	}
	
	public String GetEntityTypeNameFromEntityTypeID (String EntityTypeID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Domn WHERE DomnName = 'EntityType' AND DomnId = ?;");
		statement.setString(1, EntityTypeID);
		ResultSet result_set = statement.executeQuery();
		
		String strEntityTypeName = null;
		
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			strEntityTypeName = result_set.getString("DomnValue");
		}
		
		ClosePreparedStatement(statement);
		
		return strEntityTypeName;
	}
	
	public String GetServiceTypeNameFromServiceTypeID (String ServiceTypeID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Domn WHERE DomnName = 'ServiceType' AND DomnId = ?;");
		statement.setString(1, ServiceTypeID);
		ResultSet result_set = statement.executeQuery();
		
		String strServiceTypeName = null;
		
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			strServiceTypeName = result_set.getString("DomnValue");
		}
		
		ClosePreparedStatement(statement);
		
		return strServiceTypeName;
	}
	
	public Service GetService (String ServiceID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Service WHERE Service_ID = ?;");
		statement.setString(1, ServiceID);
		ResultSet result_set = statement.executeQuery();
		
		Service service = null;
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			String strServiceTypeName = GetServiceTypeNameFromServiceTypeID(result_set.getString("Service_Type"));
			
			Date date = ParseDate(result_set.getString("Expiry_Date"));
			service = new Service(
					strServiceTypeName,
					result_set.getString("Description"),
					result_set.getString("Entity_ID"),
					result_set.getString("Frequency"),
					result_set.getString("User_ID"),
					result_set.getString("Pwd_PIN"),
					result_set.getString("Contact"),
					date,
					result_set.getString("Service_ID")
			);
		}
		
		ClosePreparedStatement(statement);
		
		return service;
	}
	
	public Service[] GetServicesFromEntityID(String EntityID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Service WHERE Entity_ID = ?;");
		statement.setString(1, EntityID);
		ResultSet result_set = statement.executeQuery();
		
		String ServiceIDs[] = null;
		Service Services[] = null;
		int intCtr = 0;
		
		if (result_set.next()) {
			result_set.last();
			ServiceIDs = new String[result_set.getRow()];
			Services = new Service[result_set.getRow()];
			result_set.first();
			
			do {
				ServiceIDs[intCtr] = result_set.getString("Service_ID");
				intCtr++;
			} while (result_set.next());
			
			ClosePreparedStatement(statement);
			
			for (intCtr = 0; intCtr < ServiceIDs.length; intCtr++) {
				Services[intCtr] = GetService(ServiceIDs[intCtr]);
			}
		}
		
		ClosePreparedStatement(statement);
		
		return Services;
	}
	
	public CreditCard GetCreditCardFromEntityID(String EntityID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM CreditCard WHERE Entity_ID = ?;");
		statement.setString(1, EntityID);
		ResultSet result_set = statement.executeQuery();
		
		CreditCard credit_card = null;
		
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			credit_card = new CreditCard(
					result_set.getString("Entity_ID"),
					new BigDecimal(result_set.getString("Card_Limit")).setScale(2, RoundingMode.HALF_EVEN)
					);
		}
		
		ClosePreparedStatement(statement);
		
		return credit_card;
	}
	
	public Insurance GetInsuranceFromEntityID(String EntityID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Insurance WHERE Entity_ID = ?;");
		statement.setString(1, EntityID);
		ResultSet result_set = statement.executeQuery();
		
		Insurance insurance_policy = null;
		
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			insurance_policy = new Insurance(
					result_set.getString("Entity_ID"),
					new BigDecimal(result_set.getString("Insured_Amount")).setScale(2, RoundingMode.HALF_EVEN),
					new BigDecimal(result_set.getString("Insurance_Premium_PA")).setScale(2, RoundingMode.HALF_EVEN)
					);
		}
		
		ClosePreparedStatement(statement);
		
		return insurance_policy;
	}
	
	public Property GetPropertyFromEntityID (String EntityID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Property WHERE Entity_ID = ?;");
		statement.setString(1, EntityID);
		ResultSet result_set = statement.executeQuery();
		
		Property property = null;
		
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			BigDecimal PurchaseAmount = new BigDecimal(result_set.getString("Purchase_Amount")).setScale(2, RoundingMode.HALF_EVEN);
			BigDecimal SoldAmount = new BigDecimal(result_set.getString("Sold_Amount")).setScale(2, RoundingMode.HALF_EVEN);
			BigDecimal CurrentValue = new BigDecimal(result_set.getString("Current_Value")).setScale(2, RoundingMode.HALF_EVEN);
			BigDecimal SolicitorFeesBuy = new BigDecimal(result_set.getString("Solicitors_Fees_Buy")).setScale(2, RoundingMode.HALF_EVEN);
			BigDecimal SolicitorFeesSell = new BigDecimal(result_set.getString("Solicitors_Fees_Sell")).setScale(2, RoundingMode.HALF_EVEN);
			BigDecimal GovtChargesBuy = new BigDecimal(result_set.getString("Govt_Charges_Buy")).setScale(2, RoundingMode.HALF_EVEN);
			BigDecimal GovtChargesSell = new BigDecimal(result_set.getString("Govt_Charges_Sell")).setScale(2, RoundingMode.HALF_EVEN);
			BigDecimal AgentFeesBuy = new BigDecimal(result_set.getString("Agent_Fees_Buy")).setScale(2, RoundingMode.HALF_EVEN);
			BigDecimal AgentFeesSell = new BigDecimal(result_set.getString("Agent_Fees_Sell")).setScale(2, RoundingMode.HALF_EVEN);
			BigDecimal CapGainsTax = new BigDecimal(result_set.getString("Capital_Gains_Tax")).setScale(2, RoundingMode.HALF_EVEN);
			
			property = new Property(
					result_set.getString("Entity_ID"),
					result_set.getString("Address"),
					PurchaseAmount,
					SolicitorFeesBuy,
					SolicitorFeesSell,
					GovtChargesBuy,
					GovtChargesSell,
					AgentFeesBuy,
					AgentFeesSell,
					SoldAmount,
					CapGainsTax,
					CurrentValue
					);
		}
		
		ClosePreparedStatement(statement);
		
		return property;
	}
	
	public TermDeposit GetTermDepositFromEntityID (String EntityID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM TermDeposit WHERE Entity_ID = ?;");
		statement.setString(1, EntityID);
		ResultSet result_set = statement.executeQuery();
		
		TermDeposit term_deposit = null;
		
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			BigDecimal InterestAmount = new BigDecimal(result_set.getString("Interest_Amount")).setScale(2, RoundingMode.HALF_EVEN);
			BigDecimal GovtCharges = new BigDecimal(result_set.getString("Other_Charges")).setScale(2, RoundingMode.HALF_EVEN);
			BigDecimal BankFees = new BigDecimal(result_set.getString("Bank_Fees")).setScale(2, RoundingMode.HALF_EVEN);
			BigDecimal InterestRate = new BigDecimal(result_set.getString("Interest_Rate")).setScale(2, RoundingMode.HALF_EVEN);
			
			term_deposit = new TermDeposit(
					result_set.getString("Entity_ID"),
					new BigDecimal(result_set.getString("Opening_Balance")).setScale(2, RoundingMode.HALF_EVEN),
					InterestAmount,
					GovtCharges,
					BankFees,
					InterestRate
					);
		}
		
		ClosePreparedStatement(statement);
		
		return term_deposit;
	}
	
	public Loan GetLoanFromEntityID (String EntityID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Loan WHERE Entity_ID = ?;");
		statement.setString(1, EntityID);
		ResultSet result_set = statement.executeQuery();
		
		Loan loan = null;
		
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			loan = new Loan(
					result_set.getString("Entity_ID"),
					new BigDecimal(result_set.getString("Loan_Amount")).setScale(2, RoundingMode.HALF_EVEN),
					new BigDecimal(result_set.getString("Current_Value")).setScale(2, RoundingMode.HALF_EVEN)
					);
		}
		
		ClosePreparedStatement(statement);
		
		return loan;
	}
	
	public Share GetShareFromEntityID (String EntityID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Shares WHERE Entity_ID = ?;");
		statement.setString(1, EntityID);
		ResultSet result_set = statement.executeQuery();
		
		Share share = null;
		
		if (result_set.next()) {
			share = new Share(
					result_set.getString("Entity_ID"),
					new BigDecimal(result_set.getString("Current_Unit_Value")).setScale(2, RoundingMode.HALF_EVEN)
					);
		}
		
		ClosePreparedStatement(statement);
		
		return share;
	}

	public String[] Search(String Holder, String Status, String Entity, String Institution, String Holder_ID, String Status_ID, String Entity_Type_ID, String Institution_ID) throws SQLException {
		// Returns IDs of entities matching specified a holder(s), institution, entity type and entity status (returns null if none)
		Boolean boolFirstArg = false;
		String intArrResults[] = null;
		
		String Query = "SELECT * FROM Entity";
		String sqlInstitution = "Institution_ID = '" + Institution_ID + "'";
		String sqlEntity = "Entity_Type = '" + Entity_Type_ID + "'";
		String sqlStatus = "Entity_Status = '" + Status_ID + "'";
		String sqlHolder = "Holder_ID = '" + Holder_ID + "'";
		
		if (!Holder.equals("All")) {
			Query = Query + " INNER JOIN Holderentity ON Entity.Entity_ID = HolderEntity.Entity_ID";
			boolFirstArg = true;
			Query = Query + " WHERE " + sqlHolder;
		}
		
		if (!Status.equals("All")) {
			if (!boolFirstArg) {
				boolFirstArg = true;
				Query = Query + " WHERE " + sqlStatus;
			} else {
				Query = Query + " AND " + sqlStatus;
			}
		}
		
		if (!Entity.equals("All")) {
			if (!boolFirstArg) {
				boolFirstArg = true;
				Query = Query + " WHERE " + sqlEntity;
			} else {
				Query = Query + " AND " + sqlEntity;
			}
		}
		
		if (!Institution.equals("All")) {
			if (!boolFirstArg) {
				boolFirstArg = true;
				Query = Query + " WHERE " + sqlInstitution;
			} else {
				Query = Query + " AND " + sqlInstitution;
			}
		}
		
		Query = Query + ";";
		
		PreparedStatement statement = connection.prepareStatement(Query);
		ResultSet result_set = statement.executeQuery();
		
		if (result_set.next()) {
			result_set.last();
			intArrResults = new String[result_set.getRow()];
			result_set.first();
			
			int intCtr = 0;
			do {
				intArrResults[intCtr] = result_set.getString("Entity_ID");
				intCtr++;
			} while (result_set.next());
		}
		
		ClosePreparedStatement(statement);
		
		return intArrResults;
	}
	
	public boolean DoesEntityUnderHolderExist (String HolderID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM HolderEntity WHERE Holder_ID = ?;");
		statement.setString(1, HolderID);
		ResultSet result_set = statement.executeQuery();
		
		boolean boolExists = false;
		
		if (result_set.next()) {
			boolExists = true;
		}
		
		ClosePreparedStatement(statement);
		
		return boolExists;
	}
	
	public boolean DoesEntityUnderInstitutionExist (String InstitutionID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Entity WHERE Institution_ID = ?;");
		statement.setString(1, InstitutionID);
		ResultSet result_set = statement.executeQuery();
		
		boolean boolExists = false;
		
		if (result_set.next()) {
			boolExists = true;
		}
		
		ClosePreparedStatement(statement);
		
		return boolExists;
	}
	
	public boolean DoesInstitutionNameExist (String InstitutionName) throws SQLException {		
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Institution WHERE Name = ?;");
		statement.setString(1, InstitutionName);
		ResultSet result_set = statement.executeQuery();
		
		boolean boolExists = false;
		
		if (result_set.next()) {
			boolExists = true;
		}
		
		ClosePreparedStatement(statement);
		
		return boolExists;
	}
	
	public boolean DoesHolderTFNExist (String HolderTFN) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Holder WHERE Holder_TFN = ?;");
		statement.setString(1, HolderTFN);
		ResultSet result_set = statement.executeQuery();
		
		boolean boolExists = false;
		
		if (result_set.next()) {
			boolExists = true;
		}
		
		ClosePreparedStatement(statement);
		
		return boolExists;
	}
	
	public boolean DoesHolderNameExist (String HolderName) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Holder WHERE Holder_Name = ?;");
		statement.setString(1, HolderName);
		ResultSet result_set = statement.executeQuery();
		
		boolean boolExists = false;
		
		if (result_set.next()) {
			boolExists = true;
		}
		
		ClosePreparedStatement(statement);
		
		return boolExists;
	}
	
	public boolean DoesInstitutionCodeExist (String InstitutionCode) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Institution WHERE Code = ?;");
		statement.setString(1, InstitutionCode);
		ResultSet result_set = statement.executeQuery();
		
		boolean boolExists = false;
		
		if (result_set.next()) {
			boolExists = true;
		}
		
		ClosePreparedStatement(statement);
		
		return boolExists;
	}
	
	public String NewEntity (Entity new_entity) throws SQLException {
		String SQLStatement;
		
		// Only format the closing date if a date was provided
		if (new_entity.GetCloseDate() != null) {
			SQLStatement = "INSERT INTO Entity (Entity_Start_Date, Entity_End_Date, Entity_Status, Entity_Number, Entity_Type, Institution_ID, Description, Comments) VALUES (STR_TO_DATE(?, '%d/%m/%Y'), STR_TO_DATE(?, '%d/%m/%Y'), ?, ?, ?, ?, ?, ?);";
		} else {
			SQLStatement = "INSERT INTO Entity (Entity_Start_Date, Entity_End_Date, Entity_Status, Entity_Number, Entity_Type, Institution_ID, Description, Comments) VALUES (STR_TO_DATE(?, '%d/%m/%Y'), ?, ?, ?, ?, ?, ?, ?);";
		}
		
		PreparedStatement statement = connection.prepareStatement(SQLStatement);
		
		DateFormat dateFormat = new SimpleDateFormat(Main.CE_DATE_FORMAT);
		
		statement.setString(1, dateFormat.format(new_entity.GetOpenDate()));
		
		if (new_entity.GetCloseDate() == null) {
			statement.setNull(2, java.sql.Types.DATE);
		} else {
			statement.setString(2, dateFormat.format(new_entity.GetCloseDate()));
		}
		
		statement.setBoolean(3, new_entity.GetEntityStatusName().equals(Main.strClosed));	// (0 --> Current, 1 --> Closed)
		
		statement.setString(4, new_entity.GetEntityNumber());
		
		statement.setString(5, GetEntityTypeIDFromTypeName(new_entity.GetEntityTypeName()));
		
		statement.setString(6, GetInstitutionFromName(new_entity.GetInstitutionName()).GetID());
		
		if (new_entity.GetDescription() != null) {
			statement.setString(7, new_entity.GetDescription());
		} else {
			statement.setNull(7, java.sql.Types.NULL);
		}
		
		if (new_entity.GetComment() != null) {
			statement.setString(8, new_entity.GetComment());
		} else {
			statement.setNull(8, java.sql.Types.NULL);
		}
		
		statement.executeUpdate();
		
		ClosePreparedStatement(statement);
		
		statement = connection.prepareStatement("SELECT LAST_INSERT_ID();");
		ResultSet result_set = statement.executeQuery();
		
		String NewEntityID = null;
		
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			NewEntityID = result_set.getString("LAST_INSERT_ID()");
		}
		
		ClosePreparedStatement(statement);
		
		if (NewEntityID != null) {
			for (int intCtr = 0; intCtr < new_entity.GetHolderNames().length; intCtr++) {
				AddHolderToEntity(GetHolderFromName(new_entity.GetHolderNames()[intCtr]).GetID(), NewEntityID);
			}
		}
		
		return NewEntityID;
	}
	
	public void NewCreditCard (CreditCard new_card) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("INSERT INTO CreditCard (Entity_ID, Card_Limit) VALUES (?, ?);");
		statement.setString(1, new_card.GetEntityID());
		statement.setString(2, new_card.GetCardLimit().toString());
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void NewLoanAccount (Loan new_loan) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Loan (Entity_ID, Loan_Amount, Current_Value) VALUES (?, ?, ?);");
		statement.setString(1, new_loan.GetEntityID());
		statement.setString(2, new_loan.GetLoanAmount().toString());
		statement.setString(3, new_loan.GetCurrentValue().toString());
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void NewTermDeposit (TermDeposit new_termdeposit) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("INSERT INTO TermDeposit (Entity_ID, Opening_Balance, Interest_Amount, Other_Charges, Bank_Fees, Interest_Rate) VALUES (?, ?, ?, ?, ?, ?);");
		statement.setString(1, new_termdeposit.GetEntityID());
		statement.setString(2, new_termdeposit.GetOpeningBalance().toString());
		statement.setString(3, new_termdeposit.GetInterestAmount().toString());
		statement.setString(4, new_termdeposit.GetOtherCharges().toString());
		statement.setString(5, new_termdeposit.GetBankFees().toString());
		statement.setString(6, new_termdeposit.GetInterestRate().toString());
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void NewProperty (Property new_property) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Property (Entity_ID, Address, Purchase_Amount, Solicitors_Fees_Buy, Solicitors_Fees_Sell, Govt_Charges_Buy, Govt_Charges_Sell, Agent_Fees_Buy, Agent_Fees_Sell, Sold_Amount, Capital_Gains_Tax, Current_Value) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
		statement.setString(1, new_property.GetEntityID());
		statement.setString(2, new_property.GetAddress());
		statement.setString(3, new_property.GetPurchaseAmount().toString());
		statement.setString(4, new_property.GetSolicitorFeesBuy().toString());
		statement.setString(5, new_property.GetSolicitorFeesSell().toString());
		statement.setString(6, new_property.GetGovernmentChargesBuy().toString());
		statement.setString(7, new_property.GetGovernmentChargesSell().toString());
		statement.setString(8, new_property.GetAgentFeesBuy().toString());
		statement.setString(9, new_property.GetAgentFeesSell().toString());
		statement.setString(10, new_property.GetSoldAmount().toString());
		statement.setString(11, new_property.GetCapitalGainsTax().toString());
		statement.setString(12, new_property.GetCurrentValue().toString());
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void NewSharesManagedFunds (Share new_share) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Shares (Entity_ID, Current_Unit_Value) VALUES (?, ?);");
		statement.setString(1, new_share.GetEntityID());
		statement.setString(2, new_share.GetCurrentUnitValue().toString());
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void NewTransaction(Transaction new_transaction) throws SQLException {
		String SQLStatement = "INSERT INTO EntityTxn (Entity_ID, Txn_Date, Txn_Catg, Txn_DR, Txn_CR, Txn_Description) VALUES (?, STR_TO_DATE(?, '%d/%m/%Y'), ?, ?, ?, ?);";
		PreparedStatement statement = connection.prepareStatement(SQLStatement);
		DateFormat dateFormat = new SimpleDateFormat(Main.CE_DATE_FORMAT);
		
		statement.setString(1, new_transaction.GetEntityID());
		statement.setString(2, dateFormat.format(new_transaction.GetDate()));
		statement.setString(3, GetTransactionCategoryID(new_transaction.GetTransactionCategory()));
		statement.setString(4, new_transaction.GetDebit().toString());
		statement.setString(5, new_transaction.GetCredit().toString());
		statement.setString(6, new_transaction.GetDescription());
		
		statement.executeUpdate();
		
		ClosePreparedStatement(statement);
	}
	
	public void NewShareTransaction(ShareTransaction new_sharetransaction) throws SQLException {
		String SQLStatement = "INSERT INTO EntityShrTxn (Date, Description, Share_Price, Brokerage_Charges, Other_Charges, Dividend_Amount, Franking_Percentage, Entity_ID, Txn_SubType, Number_Shares, Capital_Gains_Tax) VALUES (STR_TO_DATE(?, '%d/%m/%Y'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement statement = connection.prepareStatement(SQLStatement);
		DateFormat dateFormat = new SimpleDateFormat(Main.CE_DATE_FORMAT);
		
		statement.setString(1, dateFormat.format(new_sharetransaction.GetDate()));
		
		if (new_sharetransaction.GetDescription() == null) {
			statement.setNull(2, java.sql.Types.VARCHAR);
		} else {
			statement.setString(2, new_sharetransaction.GetDescription());
		}
		
		statement.setString(3, new_sharetransaction.GetSharePrice().toString());
		
		statement.setString(4, new_sharetransaction.GetBrokerageCharges().toString());
		
		statement.setString(5, new_sharetransaction.GetOtherCharges().toString());
		
		statement.setString(6, new_sharetransaction.GetDividendAmount().toString());
		
		statement.setString(7, new_sharetransaction.GetFrankingPercentage().toString());
		
		statement.setString(8, new_sharetransaction.GetEntityID());
		
		statement.setString(9, GetShareTransactionCategoryID(new_sharetransaction.GetTransactionSubtype()));
		
		statement.setString(10, Float.toString(new_sharetransaction.GetNumberShares()));
		
		statement.setString(11, new_sharetransaction.GetCapitalGainsTax().toString());
		
		statement.executeUpdate();
		
		ClosePreparedStatement(statement);
	}
	
	public void NewInsurance (Insurance new_policy) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Insurance (Entity_ID, Insured_Amount, Insurance_Premium_PA) VALUES (?, ?, ?);");
		statement.setString(1, new_policy.GetEntityID());
		statement.setString(2, new_policy.GetInsuredAmount().toString());
		statement.setString(3, new_policy.GetPremium().toString());
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void NewHolder(Holder new_holder) throws SQLException {
		DateFormat dateFormat = new SimpleDateFormat(Main.CE_DATE_FORMAT);
		
		// Only format the date if a date was provided
		String SQLStatement = "";
		if (new_holder.GetDOB() == null) {
			SQLStatement = "INSERT INTO Holder (Holder_Name, Holder_DOB, Holder_TFN) VALUES (?, ?, ?);";
		} else {
			SQLStatement = "INSERT INTO Holder (Holder_Name, Holder_DOB, Holder_TFN) VALUES (?, STR_TO_DATE(?, '%d/%m/%Y'), ?);";
		}
		
		PreparedStatement statement = connection.prepareStatement(SQLStatement);
		
		statement.setString(1, new_holder.GetName());
		
		if (new_holder.GetDOB() == null) {
			statement.setNull(2, java.sql.Types.DATE);
		} else {
			statement.setString(2, dateFormat.format(new_holder.GetDOB()));
		}
		
		if (new_holder.GetTFN() == null) {
			statement.setNull(3, java.sql.Types.INTEGER);
		} else {
			statement.setString(3, new_holder.GetTFN());
		}
		
		statement.executeUpdate();
		
		ClosePreparedStatement(statement);
	}
	
	public void NewInstitution(Institution new_institution) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Institution (Code, Name, Address, Comments) VALUES (?, ?, ?, ?);");
		
		statement.setString(1, new_institution.GetCode());
		
		statement.setString(2, new_institution.GetName());
		
		if (new_institution.GetAddress() == null) {
			statement.setNull(3, java.sql.Types.VARCHAR);
		} else {
			statement.setString(3, new_institution.GetAddress());
		}
		
		if (new_institution.GetComment() == null) {
			statement.setNull(4, java.sql.Types.VARCHAR);
		} else {
			statement.setString(4, new_institution.GetComment());
		}
		
		statement.executeUpdate();
		
		ClosePreparedStatement(statement);
	}
	
	public void NewService (Service new_service) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("INSERT INTO Service (Description, Entity_ID, User_ID, Pwd_PIN, Contact, Expiry_Date, Service_Type, Frequency) VALUES (?, ?, ?, ?, ?, STR_TO_DATE(?, '%d/%m/%Y'), ?, ?);");
		
		DateFormat dateFormat = new SimpleDateFormat(Main.CE_DATE_FORMAT);
		
		statement.setString(1, new_service.GetDescription());
		
		statement.setString(2, new_service.GetEntityID());
		
		if (new_service.GetUserID() == null) {
			statement.setNull(3, java.sql.Types.VARCHAR);
		} else {
			statement.setString(3, new_service.GetUserID());
		}
		
		if (new_service.GetPassword() == null) {
			statement.setNull(4, java.sql.Types.VARCHAR);
		} else {
			statement.setString(4, new_service.GetPassword());
		}
		
		if (new_service.GetContact() == null) {
			statement.setNull(5, java.sql.Types.VARCHAR);
		} else {
			statement.setString(5, new_service.GetContact());
		}
		
		if (new_service.GetExpiry() == null) {
			statement.setNull(6, java.sql.Types.DATE);
		} else {
			statement.setString(6, dateFormat.format(new_service.GetExpiry()));
		}
		
		statement.setString(7, GetServiceTypeIDFromServiceType(new_service.GetServiceType()));
		
		if (new_service.GetFrequency() == null) {
			statement.setNull(8, java.sql.Types.VARCHAR);
		} else {
			statement.setString(8, new_service.GetFrequency());
		}
		
		statement.executeUpdate();
		
		ClosePreparedStatement(statement);
		
		//statement = connection.prepareStatement("SELECT LAST_INSERT_ID();");
		//ResultSet result_set = statement.executeQuery();
		
		//String NewServiceID = null;
		
		//if (result_set.next()) {
		//	// Since there should only be one matching row, we don't bother looping through the results
		//	NewServiceID = result_set.getString("LAST_INSERT_ID()");
		//}
		
		//ClosePreparedStatement(statement);
	}
	
	public void NewLinkedEntity (String EntityID, String LinkedEntityID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("INSERT INTO EntityHistory (Entity_ID, Linked_Entity_ID) VALUES (?, ?);");
		statement.setString(1, EntityID);
		statement.setString(2, LinkedEntityID);
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void AddHolderToEntity (String HolderID, String EntityID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("INSERT INTO HolderEntity (Holder_ID, Entity_ID) VALUES (?, ?);");
		statement.setString(1, HolderID);
		statement.setString(2, EntityID);
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void EditCreditCard(CreditCard edited_creditcard) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("UPDATE CreditCard SET Card_Limit = ? WHERE Entity_ID = ?;");
		statement.setString(1, edited_creditcard.GetCardLimit().toString());
		statement.setString(2, edited_creditcard.GetEntityID());
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void EditInsurance(Insurance edited_insurance) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("UPDATE Insurance SET Insured_Amount = ?, Insurance_Premium_PA = ? WHERE Entity_ID = ?;");
		statement.setString(1, edited_insurance.GetInsuredAmount().toString());
		statement.setString(2, edited_insurance.GetPremium().toString());
		statement.setString(3, edited_insurance.GetEntityID());
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void EditLoan(Loan edited_loan) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("UPDATE Loan SET Loan_Amount = ?, Current_Value = ? WHERE Entity_ID = ?;");
		statement.setString(1, edited_loan.GetLoanAmount().toString());
		statement.setString(2, edited_loan.GetCurrentValue().toString());
		statement.setString(3, edited_loan.GetEntityID());
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void EditProperty(Property edited_property) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("UPDATE Property SET "
				+ "Address = ?, "
				+ "Purchase_Amount = ?, "
				+ "Sold_Amount = ?, "
				+ "Current_Value = ?, "
				+ "Solicitors_Fees_Buy = ?, "
				+ "Solicitors_Fees_Sell = ?, "
				+ "Govt_Charges_Buy = ?, "
				+ "Govt_Charges_Sell = ?, "
				+ "Agent_Fees_Buy = ?, "
				+ "Agent_Fees_Sell = ?, "
				+ "Capital_Gains_Tax = ? "
				+ "WHERE Entity_ID = ?;");
		statement.setString(1, edited_property.GetAddress());
		statement.setString(2, edited_property.GetPurchaseAmount().toString());
		statement.setString(3, edited_property.GetSoldAmount().toString());
		statement.setString(4, edited_property.GetCurrentValue().toString());
		statement.setString(5, edited_property.GetSolicitorFeesBuy().toString());
		statement.setString(6, edited_property.GetSolicitorFeesSell().toString());
		statement.setString(7, edited_property.GetGovernmentChargesBuy().toString());
		statement.setString(8, edited_property.GetGovernmentChargesSell().toString());
		statement.setString(9, edited_property.GetAgentFeesBuy().toString());
		statement.setString(10, edited_property.GetAgentFeesSell().toString());
		statement.setString(11, edited_property.GetCapitalGainsTax().toString());
		statement.setString(12, edited_property.GetEntityID());
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void EditShare(Share edited_share) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("UPDATE Shares SET Current_Unit_Value = ? WHERE Entity_ID = ?;");
		statement.setString(1, edited_share.GetCurrentUnitValue().toString());
		statement.setString(2, edited_share.GetEntityID());
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void EditTermDeposit(TermDeposit edited_termdeposit) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("UPDATE TermDeposit SET "
				+ "Opening_Balance = ?, "
				+ "Interest_Amount = ?, "
				+ "Other_Charges = ?, "
				+ "Bank_Fees = ?, "
				+ "Interest_Rate = ? "
				+ "WHERE Entity_ID = ?;");
		statement.setString(1, edited_termdeposit.GetOpeningBalance().toString());
		statement.setString(2, edited_termdeposit.GetInterestAmount().toString());
		statement.setString(3, edited_termdeposit.GetOtherCharges().toString());
		statement.setString(4, edited_termdeposit.GetBankFees().toString());
		statement.setString(5, edited_termdeposit.GetInterestRate().toString());
		statement.setString(6, edited_termdeposit.GetEntityID());
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void EditEntity(Entity edited_entity) throws SQLException {
		DateFormat dateFormat = new SimpleDateFormat(Main.CE_DATE_FORMAT);
		
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM HolderEntity WHERE Entity_ID = ?;");
		statement.setString(1, edited_entity.GetEntityID());
		ResultSet result_set = statement.executeQuery();
		
		String[] NewHolders = new String[edited_entity.GetHolderNames().length];
		String[] ExistingHolders = null;
		int intCtr = 0;
		
		if (result_set.next()) {
			result_set.last();
			ExistingHolders = new String[result_set.getRow()];
			result_set.first();
			
			do {
				ExistingHolders[intCtr] = result_set.getString("Holder_ID");
				intCtr++;
			} while (result_set.next());
		}
		
		ClosePreparedStatement(statement);
		
		for (intCtr = 0; intCtr < edited_entity.GetHolderNames().length; intCtr++) {
			NewHolders[intCtr] = GetHolderFromName(edited_entity.GetHolderNames()[intCtr]).GetID();
		}
		
		for (intCtr = 0; intCtr < ExistingHolders.length; intCtr++) {
			DeleteHolderFromEntity(ExistingHolders[intCtr], edited_entity.GetEntityID());
		}
		
		for (intCtr = 0; intCtr < NewHolders.length; intCtr++) {
			AddHolderToEntity(NewHolders[intCtr], edited_entity.GetEntityID());
		}
		
		// Only format the date if a date was provided
		String SQLStatement;
		if (edited_entity.GetCloseDate() == null) {
			SQLStatement = "UPDATE Entity SET Entity_Start_Date = STR_TO_DATE(?, '%d/%m/%Y'), Entity_End_Date = ?, Entity_Status = ?, Entity_Number = ?, Institution_ID = ?, Description = ?, Comments = ? WHERE Entity_ID = ?;";
		} else {
			SQLStatement = "UPDATE Entity SET Entity_Start_Date = STR_TO_DATE(?, '%d/%m/%Y'), Entity_End_Date = STR_TO_DATE(?, '%d/%m/%Y'), Entity_Status = ?, Entity_Number = ?, Institution_ID = ?, Description = ?, Comments = ? WHERE Entity_ID = ?;";
		}
		
		statement = connection.prepareStatement(SQLStatement);
		
		statement.setString(1, dateFormat.format(edited_entity.GetOpenDate()));
		
		if (edited_entity.GetCloseDate() == null) {
			statement.setNull(2, java.sql.Types.DATE);
		} else {
			statement.setString(2, dateFormat.format(edited_entity.GetCloseDate()));
		}
		
		statement.setBoolean(3, edited_entity.GetEntityStatusName().equals(Main.strClosed));
		
		statement.setString(4, edited_entity.GetEntityNumber());
		
		statement.setString(5, GetInstitutionFromName(edited_entity.GetInstitutionName()).GetID());
		
		if (edited_entity.GetDescription() != null) {
			statement.setString(6, edited_entity.GetDescription());
		} else {
			statement.setNull(6, java.sql.Types.NULL);
		}
		
		if (edited_entity.GetComment() != null) {
			statement.setString(7, edited_entity.GetComment());
		} else {
			statement.setNull(7, java.sql.Types.NULL);
		}
		
		statement.setString(8, edited_entity.GetEntityID());
		
		statement.executeUpdate();
		
		ClosePreparedStatement(statement);
	}
	
	public void EditHolder(Holder edited_holder) throws SQLException {
		DateFormat dateFormat = new SimpleDateFormat(Main.CE_DATE_FORMAT);
		
		// Only format the date if a date was provided
		String SQLStatement;
		if (edited_holder.GetDOB() == null) {
			SQLStatement = "UPDATE Holder SET Holder_Name = ?, Holder_DOB = ?, Holder_TFN = ? WHERE Holder_ID = ?;";
		} else {
			SQLStatement = "UPDATE Holder SET Holder_Name = ?, Holder_DOB = STR_TO_DATE(?, '%d/%m/%Y'), Holder_TFN = ? WHERE Holder_ID = ?;";
		}
		
		PreparedStatement statement = connection.prepareStatement(SQLStatement);
		
		statement.setString(1, edited_holder.GetName());
		
		if (edited_holder.GetDOB() == null) {
			statement.setNull(2, java.sql.Types.DATE);
		} else {
			statement.setString(2, dateFormat.format(edited_holder.GetDOB()));
		}
		
		if (edited_holder.GetTFN() == null) {
			statement.setNull(3, java.sql.Types.INTEGER);
		} else {
			statement.setString(3, edited_holder.GetTFN());
		}
		
		statement.setString(4, edited_holder.GetID());
		
		statement.executeUpdate();
		
		ClosePreparedStatement(statement);
	}
	
	public void EditInstitution(Institution edited_institution) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("UPDATE Institution SET Code = ?, Name = ?, Address = ?, Comments = ? WHERE Institution_ID = ?;");
		
		statement.setString(1, edited_institution.GetCode());
		
		statement.setString(2, edited_institution.GetName());
		
		if (edited_institution.GetAddress() == null) {
			statement.setNull(3, java.sql.Types.VARCHAR);
		} else {
			statement.setString(3, edited_institution.GetAddress());
		}
		
		if (edited_institution.GetComment() == null) {
			statement.setNull(4, java.sql.Types.VARCHAR);
		} else {
			statement.setString(4, edited_institution.GetComment());
		}
		
		statement.setString(5, edited_institution.GetID());
		
		statement.executeUpdate();
		
		ClosePreparedStatement(statement);
	}
	
	public void EditService(Service edited_service) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("UPDATE Service SET Service_ID = ?, Description = ?, Entity_ID = ?, User_ID = ?, Pwd_PIN = ?, Contact = ?, Expiry_Date = STR_TO_DATE(?, '%d/%m/%Y'), Service_Type = ?, Frequency = ? WHERE Service_ID = ?;");
		
		DateFormat dateFormat = new SimpleDateFormat(Main.CE_DATE_FORMAT);
		
		statement.setString(1, edited_service.GetServiceID());
		
		statement.setString(2, edited_service.GetDescription());
		
		statement.setString(3, edited_service.GetEntityID());
		
		if (edited_service.GetUserID() == null) {
			statement.setNull(4, java.sql.Types.VARCHAR);
		} else {
			statement.setString(4, edited_service.GetUserID());
		}
		
		if (edited_service.GetPassword() == null) {
			statement.setNull(5, java.sql.Types.VARCHAR);
		} else {
			statement.setString(5, edited_service.GetPassword());
		}
		
		if (edited_service.GetContact() == null) {
			statement.setNull(6, java.sql.Types.VARCHAR);
		} else {
			statement.setString(6, edited_service.GetContact());
		}
		
		if (edited_service.GetExpiry() == null) {
			statement.setNull(7, java.sql.Types.DATE);
		} else {
			statement.setString(7, dateFormat.format(edited_service.GetExpiry()));
		}
		
		statement.setString(8, GetServiceTypeIDFromServiceType(edited_service.GetServiceType()));
		
		if (edited_service.GetFrequency() == null) {
			statement.setNull(9, java.sql.Types.VARCHAR);
		} else {
			statement.setString(9, edited_service.GetFrequency());
		}
		
		statement.setString(10, edited_service.GetServiceID());
		
		statement.executeUpdate();
		
		ClosePreparedStatement(statement);
	}
	
	public void EditTransaction(Transaction edited_transaction) throws SQLException {
		DateFormat dateFormat = new SimpleDateFormat(Main.CE_DATE_FORMAT);
		String SQLStatement = "UPDATE EntityTxn SET Txn_Date = STR_TO_DATE(?, '%d/%m/%Y'), Txn_Catg = ?, Txn_Description = ?, Txn_DR = ?, Txn_CR = ? WHERE Txn_ID = ?;";
		PreparedStatement statement = connection.prepareStatement(SQLStatement);
		
		statement.setString(1, dateFormat.format(edited_transaction.GetDate()));
		
		statement.setString(2, GetTransactionCategoryID(edited_transaction.GetTransactionCategory()));
		
		if (edited_transaction.GetDescription() != null) {
			statement.setString(3, edited_transaction.GetDescription());
		} else {
			statement.setNull(3, java.sql.Types.NULL);
		}
		
		statement.setString(4, edited_transaction.GetDebit().toString());
		
		statement.setString(5, edited_transaction.GetCredit().toString());
		
		statement.setString(6, edited_transaction.GetTransactionID());
		
		statement.executeUpdate();
		
		ClosePreparedStatement(statement);
	}
	
	public void EditShareTransaction(ShareTransaction edited_sharetransaction) throws SQLException {
		DateFormat dateFormat = new SimpleDateFormat(Main.CE_DATE_FORMAT);
		String SQLStatement = "UPDATE EntityShrTxn SET "
				+ "Date = STR_TO_DATE(?, '%d/%m/%Y'), "
				+ "Txn_SubType = ?, "
				+ "Share_Price = ?, "
				+ "Brokerage_Charges = ?, "
				+ "Other_Charges = ?, "
				+ "Dividend_Amount = ?, "
				+ "Franking_Percentage = ?, "
				+ "Capital_Gains_Tax = ?, "
				+ "Number_Shares = ?, "
				+ "Description = ? "
				+ "WHERE Shr_Txn_ID = ?;";
		PreparedStatement statement = connection.prepareStatement(SQLStatement);
		
		statement.setString(1, dateFormat.format(edited_sharetransaction.GetDate()));
		
		statement.setString(2, GetShareTransactionCategoryID(edited_sharetransaction.GetTransactionSubtype()));
		
		statement.setString(3, edited_sharetransaction.GetSharePrice().toString());
		
		statement.setString(4, edited_sharetransaction.GetBrokerageCharges().toString());
		
		statement.setString(5, edited_sharetransaction.GetOtherCharges().toString());
		
		statement.setString(6, edited_sharetransaction.GetDividendAmount().toString());
		
		statement.setString(7, edited_sharetransaction.GetFrankingPercentage().toString());
		
		statement.setString(8, edited_sharetransaction.GetCapitalGainsTax().toString());
		
		statement.setString(9, Float.toString(edited_sharetransaction.GetNumberShares()));
		
		if (edited_sharetransaction.GetDescription() != null) {
			statement.setString(10, edited_sharetransaction.GetDescription());
		} else {
			statement.setNull(10, java.sql.Types.NULL);
		}
		
		statement.setString(11, edited_sharetransaction.GetTransactionID());
		
		statement.executeUpdate();
		
		ClosePreparedStatement(statement);
	}
	
	public void DeleteAllTransactions (String EntityID) throws SQLException {
		Entity entity = GetEntityFromID(EntityID);
		PreparedStatement statement;
		if (entity.GetEntityTypeName().equals(Main.ENTITY_TYPES[6])) {
			// Shares
			statement = connection.prepareStatement("DELETE FROM EntityShrTxn WHERE Entity_ID = ?;");
			
		} else {
			// Other
			statement = connection.prepareStatement("DELETE FROM EntityTxn WHERE Entity_ID = ?;");
		}
		
		statement.setString(1, EntityID);
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void DeleteHolderFromEntity(String HolderID, String EntityID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("DELETE FROM HolderEntity WHERE Holder_ID = ? AND Entity_ID = ?;");
		statement.setString(1, HolderID);
		statement.setString(2, EntityID);
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void DeleteEntityHolders (String EntityID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("DELETE FROM HolderEntity WHERE Entity_ID = ?;");
		statement.setString(1, EntityID);
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void DeleteEntityType (String EntityID) throws SQLException {
		/* Not the most elegant solution, but since the entity should only exist in one of the
		 * below tables, we can run the delete command on all of them. */
		
		// Credit Card
		PreparedStatement statement = connection.prepareStatement("DELETE FROM CreditCard WHERE Entity_ID = ?;");
		statement.setString(1, EntityID);
		statement.executeUpdate();
		ClosePreparedStatement(statement);
		
		// Insurance
		statement = connection.prepareStatement("DELETE FROM Insurance WHERE Entity_ID = ?;");
		statement.setString(1, EntityID);
		statement.executeUpdate();
		ClosePreparedStatement(statement);
		
		// Loan
		statement = connection.prepareStatement("DELETE FROM Loan WHERE Entity_ID = ?;");
		statement.setString(1, EntityID);
		statement.executeUpdate();
		ClosePreparedStatement(statement);
		
		// Property
		statement = connection.prepareStatement("DELETE FROM Property WHERE Entity_ID = ?;");
		statement.setString(1, EntityID);
		statement.executeUpdate();
		ClosePreparedStatement(statement);
		
		// Shares
		statement = connection.prepareStatement("DELETE FROM Shares WHERE Entity_ID = ?;");
		statement.setString(1, EntityID);
		statement.executeUpdate();
		ClosePreparedStatement(statement);
		
		// TermDeposit
		statement = connection.prepareStatement("DELETE FROM TermDeposit WHERE Entity_ID = ?;");
		statement.setString(1, EntityID);
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void DeleteEntity (String EntityID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("DELETE FROM Entity WHERE Entity_ID = ?;");
		statement.setString(1, EntityID);
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void DeleteInstitution (String InstID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("DELETE FROM Institution WHERE Institution_ID = ?;");
		statement.setString(1, InstID);
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void DeleteHolder (String HolderID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("DELETE FROM Holder WHERE Holder_ID = ?;");
		statement.setString(1, HolderID);
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void DeleteEntityHistory (String EntityID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("DELETE FROM EntityHistory WHERE Linked_Entity_ID = ? OR Entity_ID = ?;");
		statement.setString(1, EntityID);
		statement.setString(2, EntityID);
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void DeleteService (String ServiceID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("DELETE FROM Service WHERE Service_ID = ?;");
		statement.setString(1, ServiceID);
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void DeleteLinkedEntity (String EntityID, String LinkedEntityID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("DELETE FROM EntityHistory WHERE Entity_ID = ? AND Linked_Entity_ID = ?;");
		statement.setString(1, EntityID);
		statement.setString(2, LinkedEntityID);
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void DeleteTransaction(String TxnID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("DELETE FROM EntityTxn WHERE Txn_ID = ?;");
		statement.setString(1, TxnID);
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public void DeleteShareTransaction(String ShrTxnID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("DELETE FROM EntityShrTxn WHERE Shr_Txn_ID = ?;");
		statement.setString(1, ShrTxnID);
		statement.executeUpdate();
		ClosePreparedStatement(statement);
	}
	
	public BigDecimal GetDebits (String EntityID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT SUM(Txn_DR) FROM EntityTxn WHERE Entity_ID = ?;");
		statement.setString(1, EntityID);
		ResultSet result_set = statement.executeQuery();
		
		BigDecimal TotalDebits = null;
		
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			if (result_set.getString("SUM(Txn_DR)") != null) {
				TotalDebits = new BigDecimal(result_set.getString("SUM(Txn_DR)"));
				TotalDebits.setScale(2, RoundingMode.HALF_EVEN);
			} else {
				TotalDebits = new BigDecimal("0");
			}
		}
		
		ClosePreparedStatement(statement);
		
		return TotalDebits;
	}
	
	public BigDecimal GetCredits (String EntityID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT SUM(Txn_CR) FROM EntityTxn WHERE Entity_ID = ?;");
		statement.setString(1, EntityID);
		ResultSet result_set = statement.executeQuery();
		
		BigDecimal TotalCredits = null;
		
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			if (result_set.getString("SUM(Txn_CR)") != null) {
				TotalCredits = new BigDecimal(result_set.getString("SUM(Txn_CR)"));
				TotalCredits.setScale(2, RoundingMode.HALF_EVEN);
			} else {
				TotalCredits = new BigDecimal("0");
			}
		}
		
		ClosePreparedStatement(statement);
		
		return TotalCredits;
	}
	
	public String GetTransactionCategoryName (String TxnCatgID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Domn WHERE DomnName = 'TxnCatg' AND DomnId = ?;");
		statement.setString(1, TxnCatgID);
		ResultSet result_set = statement.executeQuery();
		
		String TxnCatgName = null;
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			TxnCatgName = result_set.getString("DomnValue");
		}
		
		ClosePreparedStatement(statement);
		
		return TxnCatgName;
	}
	
	public String GetShareTransactionCategoryName (String ShrTxnSubTypeID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Domn WHERE DomnName = 'ShareTxnType' AND DomnId = ?;");
		statement.setString(1, ShrTxnSubTypeID);
		ResultSet result_set = statement.executeQuery();
		
		String ShrTxnCatgName = null;
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			ShrTxnCatgName = result_set.getString("DomnValue");
		}
		
		ClosePreparedStatement(statement);
		
		return ShrTxnCatgName;
	}
	
	public String GetTransactionCategoryID (String TxnCatgName) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Domn WHERE DomnName = 'TxnCatg' AND DomnValue = ?;");
		statement.setString(1, TxnCatgName);
		ResultSet result_set = statement.executeQuery();
		
		String TxnCatgID = null;
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			TxnCatgID = result_set.getString("DomnId");
		}
		
		ClosePreparedStatement(statement);
		
		return TxnCatgID;
	}
	
	public String GetShareTransactionCategoryID (String ShrTxnSubType) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM Domn WHERE DomnName = 'ShareTxnType' AND DomnValue = ?;");
		statement.setString(1, ShrTxnSubType);
		ResultSet result_set = statement.executeQuery();
		
		String ShrTxnCatgID = null;
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			ShrTxnCatgID = result_set.getString("DomnId");
		}
		
		ClosePreparedStatement(statement);
		
		return ShrTxnCatgID;
	}
	
	public Transaction[] GetTransactions (String EntityID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM EntityTxn WHERE Entity_ID = ?;");
		statement.setString(1, EntityID);
		ResultSet result_set = statement.executeQuery();
		
		Transaction[] AllTransactions = null;
		int intCtr = 0;
		
		if (result_set.next()) {
			result_set.last();
			AllTransactions = new Transaction[result_set.getRow()];
			result_set.first();
			
			do {
				AllTransactions[intCtr] = GetTransaction(result_set.getString("Txn_ID"));
				intCtr++;
			} while (result_set.next());
		}
		
		ClosePreparedStatement(statement);
		
		return AllTransactions;
	}
	
	public ShareTransaction[] GetShareTransactions (String EntityID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM EntityShrTxn WHERE Entity_ID = ?;");
		statement.setString(1, EntityID);
		ResultSet result_set = statement.executeQuery();
		
		ShareTransaction[] AllShareTransactions = null;
		int intCtr = 0;
		
		if (result_set.next()) {
			result_set.last();
			AllShareTransactions = new ShareTransaction[result_set.getRow()];
			result_set.first();
			
			do {
				AllShareTransactions[intCtr] = GetShareTransaction(result_set.getString("Shr_Txn_ID"));
				intCtr++;
			} while (result_set.next());
		}
		
		ClosePreparedStatement(statement);
		
		return AllShareTransactions;
	}
	
	public ShareTransaction GetShareTransaction(String ShrTxnID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM EntityShrTxn WHERE Shr_Txn_ID = ?;");
		statement.setString(1, ShrTxnID);
		ResultSet result_set = statement.executeQuery();
		
		ShareTransaction shrtransaction = null;
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			Date Date = ParseDate(result_set.getString("Date"));
			shrtransaction = new ShareTransaction(
					Date,
					result_set.getString("Shr_Txn_ID"),
					result_set.getString("Entity_ID"),
					GetShareTransactionCategoryName(result_set.getString("Txn_SubType")),
					result_set.getString("Description"),
					new BigDecimal(result_set.getString("Share_Price")).setScale(2, RoundingMode.HALF_EVEN),
					new BigDecimal(result_set.getString("Brokerage_Charges")).setScale(2, RoundingMode.HALF_EVEN),
					new BigDecimal(result_set.getString("Other_Charges")).setScale(2, RoundingMode.HALF_EVEN),
					new BigDecimal(result_set.getString("Capital_Gains_Tax")).setScale(2, RoundingMode.HALF_EVEN),
					new BigDecimal(result_set.getString("Dividend_Amount")).setScale(2, RoundingMode.HALF_EVEN),
					new BigDecimal(result_set.getString("Franking_Percentage")).setScale(2, RoundingMode.HALF_EVEN),
					Float.parseFloat(result_set.getString("Number_Shares"))
					);
		}
		
		ClosePreparedStatement(statement);
		
		return shrtransaction;
	}
	
	public Transaction GetTransaction(String TxnID) throws SQLException {
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM EntityTxn WHERE Txn_ID = ?;");
		statement.setString(1, TxnID);
		ResultSet result_set = statement.executeQuery();
		
		Transaction transaction = null;
		if (result_set.next()) {
			// Since there should only be one matching row, we don't bother looping through the results
			Date Date = ParseDate(result_set.getString("Txn_Date"));
			transaction = new Transaction(
					result_set.getString("Txn_ID"),
					result_set.getString("Entity_ID"),
					Date,
					GetTransactionCategoryName(result_set.getString("Txn_Catg")),
					result_set.getString("Txn_Description"),
					new BigDecimal(result_set.getString("Txn_DR")).setScale(2, RoundingMode.HALF_EVEN),
					new BigDecimal (result_set.getString("Txn_CR")).setScale(2, RoundingMode.HALF_EVEN)
					);
		}
		
		ClosePreparedStatement(statement);
		
		return transaction;
	}
}