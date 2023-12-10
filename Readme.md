# ALR4J Documentation
## Database Structure
### <p style="background-color:#C0392B; color:white;">Domn</p>
- Contains definitions for various integer IDs used within other tables (e.g. entity types, institution types, entity status types, service types, transaction types, etc.).
- Should be treated as READ-ONLY.

<table>
	<tr>
		<th style="text-align:center;">Field</th>
		<th style="text-align:center;">Type</th>
		<th style="text-align:center;">Null</th>
        <th style="text-align:center;">Key</th>
        <th style="text-align:center;">Default</th>
        <th style="text-align:center;">Extra</th>
 	</tr>
 	<tr>
  		<td style="font-weight:bold; text-align:right;">DomnID</td>
   		<td style="text-align:center;">int(11)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;">MUL</td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">DomnName</td>
   		<td style="text-align:center;">varchar(50)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;">MUL</td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">DomnValue</td>
   		<td style="text-align:center;">varchar(255)</td>
        <td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
</table>

Example `Domn` Table:
<table>
	<tr>
		<th style="text-align:center;">DomnID</th>
		<th style="text-align:center;">DomnName</th>
		<th style="text-align:center;">DomnValue</th>
 	</tr>
 	<tr>
  		<td style="text-align:center;"><code>0</code></td>
   		<td style="text-align:center;"><code>EntityStatus</code></td>
		<td>Current</td>
 	</tr>
	<tr>
  		<td style="text-align:center;"><code>0</code></td>
   		<td style="text-align:center;"><code>EntityType</code></td>
		<td>Savings Account</td>
 	</tr>
	<tr>
  		<td style="text-align:center;"><code>0</code></td>
   		<td style="text-align:center;"><code>ServiceType</code></td>
		<td>Direct Debit (PP, PDC etc.)</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>0</code></td>
   		<td style="text-align:center;"><code>ShareTxnType</code></td>
		<td>Buy</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>0</code></td>
   		<td style="text-align:center;"><code>TxnCatg</code></td>
		<td>INT</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>1</code></td>
   		<td style="text-align:center;"><code>EntityStatus</code></td>
		<td>Closed</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>1</code></td>
   		<td style="text-align:center;"><code>EntityType</code></td>
		<td>Credit Card</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>1</code></td>
   		<td style="text-align:center;"><code>ServiceType</code></td>
		<td>Direct Credit (Salary, Rent, Dividend etc.)</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>1</code></td>
   		<td style="text-align:center;"><code>ShareTxnType</code></td>
		<td>Sell</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>1</code></td>
   		<td style="text-align:center;"><code>TxnCatg</code></td>
		<td>DIV</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>2</code></td>
   		<td style="text-align:center;"><code>EntityType</code></td>
		<td>Loan Account</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>2</code></td>
   		<td style="text-align:center;"><code>ServiceType</code></td>
		<td>Net Details (URL, UserId, Pwd etc.)</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>2</code></td>
   		<td style="text-align:center;"><code>ShareTxnType</code></td>
		<td>Dividend</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>2</code></td>
   		<td style="text-align:center;"><code>TxnCatg</code></td>
		<td>FEE</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>3</code></td>
   		<td style="text-align:center;"><code>EntityType</code></td>
		<td>Term Deposit</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>3</code></td>
   		<td style="text-align:center;"><code>ServiceType</code></td>
		<td>Telebank Details (Number, UserId, Pwd etc.)</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>3</code></td>
   		<td style="text-align:center;"><code>ShareTxnType</code></td>
		<td>DRP</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>3</code></td>
   		<td style="text-align:center;"><code>TxnCatg</code></td>
		<td>OTHER</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>4</code></td>
   		<td style="text-align:center;"><code>EntityType</code></td>
		<td>Superannuation</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>4</code></td>
   		<td style="text-align:center;"><code>ServiceType</code></td>
		<td>Card Details (Number, Expiry Date, PIN etc.)</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>4</code></td>
   		<td style="text-align:center;"><code>TxnCatg</code></td>
		<td>PAYMENT</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>5</code></td>
   		<td style="text-align:center;"><code>EntityType</code></td>
		<td>Property</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>5</code></td>
   		<td style="text-align:center;"><code>ServiceType</code></td>
		<td>Other</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>5</code></td>
   		<td style="text-align:center;"><code>TxnCatg</code></td>
		<td>CREDIT</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>6</code></td>
   		<td style="text-align:center;"><code>EntityType</code></td>
		<td>Shares/Managed Funds</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>6</code></td>
   		<td style="text-align:center;"><code>TxnCatg</code></td>
		<td>DEP</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>7</code></td>
   		<td style="text-align:center;"><code>EntityType</code></td>
		<td>Insurance</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>7</code></td>
   		<td style="text-align:center;"><code>TxnCatg</code></td>
		<td>POS</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>8</code></td>
   		<td style="text-align:center;"><code>TxnCatg</code></td>
		<td>XFER</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>9</code></td>
   		<td style="text-align:center;"><code>TxnCatg</code></td>
		<td>DEBIT</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>10</code></td>
   		<td style="text-align:center;"><code>TxnCatg</code></td>
		<td>SRVCHG</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>11</code></td>
   		<td style="text-align:center;"><code>TxnCatg</code></td>
		<td>ATM</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>12</code></td>
   		<td style="text-align:center;"><code>TxnCatg</code></td>
		<td>CHECK</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>13</code></td>
   		<td style="text-align:center;"><code>TxnCatg</code></td>
		<td>CASH</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>14</code></td>
   		<td style="text-align:center;"><code>TxnCatg</code></td>
		<td>DIRECTDEP</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>15</code></td>
   		<td style="text-align:center;"><code>TxnCatg</code></td>
		<td>DIRECTDEBIT</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>16</code></td>
   		<td style="text-align:center;"><code>TxnCatg</code></td>
		<td>REPEATPMT</td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>17</code></td>
   		<td style="text-align:center;"><code>TxnCatg</code></td>
		<td>TAX</td>
 	</tr>
</table>

```SQL
CREATE TABLE `Domn` (
  `DomnId` int(11) NOT NULL,
  `DomnName` varchar(50) NOT NULL,
  `DomnValue` varchar(255) NOT NULL,
  KEY `DomnId` (`DomnId`),
  KEY `DomnName` (`DomnName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `Domn` VALUES (0,'EntityStatus','Current'),(0,'EntityType','Savings Account'),(0,'ServiceType','Direct Debit (PP, PDC etc.)'),(0,'ShareTxnType','Buy'),(0,'TxnCatg','INT'),(1,'EntityStatus','Closed'),(1,'EntityType','Credit Card'),(1,'ServiceType','Direct Credit (Salary, Rent, Dividend etc.)'),(1,'ShareTxnType','Sell'),(1,'TxnCatg','DIV'),(2,'EntityType','Loan Account'),(2,'ServiceType','Net Details (URL, UserId, Pwd etc.)'),(2,'ShareTxnType','Dividend'),(2,'TxnCatg','FEE'),(3,'EntityType','Term Deposit'),(3,'ServiceType','Telebank Details (Number, UserId, Pwd etc.)'),(3,'ShareTxnType','DRP'),(3,'TxnCatg','OTHER'),(4,'EntityType','Superannuation'),(4,'ServiceType','Card Details (Number, Expiry Date, PIN etc.)'),(4,'TxnCatg','PAYMENT'),(5,'EntityType','Property'),(5,'ServiceType','Other'),(5,'TxnCatg','CREDIT'),(6,'EntityType','Shares/Managed Funds'),(6,'TxnCatg','DEP'),(7,'EntityType','Insurance'),(7,'TxnCatg','POS'),(8,'TxnCatg','XFER'),(9,'TxnCatg','DEBIT'),(10,'TxnCatg','SRVCHG'),(11,'TxnCatg','ATM'),(12,'TxnCatg','CHECK'),(13,'TxnCatg','CASH'),(14,'TxnCatg','DIRECTDEP'),(15,'TxnCatg','DIRECTDEBIT'),(16,'TxnCatg','REPEATPMT'),(17,'TxnCatg','TAX');
```

### <p style="background-color:#D68910; color:white;">Holder</p>
- Holders are individuals with ownership over entities.
- Holder_IDs are unique and assigned upon holder creation.

<table>
	<tr>
		<th style="text-align:center;">Field</th>
		<th style="text-align:center;">Type</th>
		<th style="text-align:center;">Null</th>
        <th style="text-align:center;">Key</th>
        <th style="text-align:center;">Default</th>
        <th style="text-align:center;">Extra</th>
 	</tr>
 	<tr>
  		<td style="font-weight:bold; text-align:right;">Holder_ID</td>
   		<td style="text-align:center;">int(11)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;">PRI</td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;">auto_increment</td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Holder_Name</td>
   		<td style="text-align:center;">varchar(255)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Holder_DOB</td>
   		<td style="text-align:center;">date</td>
        <td style="text-align:center;">YES</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Holder_TFN</td>
   		<td style="text-align:center;">int(11)</td>
        <td style="text-align:center;">YES</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
</table>

Example `Holder` Table:
<table>
	<tr>
		<th style="text-align:center;">Holder_ID</th>
		<th style="text-align:center;">Holder_Name</th>
        <th style="text-align:center;">Holder_DOB</th>
        <th style="text-align:center;">Holder_TFN</th>
 	</tr>
 	<tr>
  		<td style="text-align:center;"><code>1</code></td>
   		<td>John Appleseed</td>
		<td>1959-06-23</td>
        <td><font color="red">NULL</font></td>
 	</tr>
	<tr>
  		<td style="text-align:center;"><code>2</code></td>
   		<td>Maria Database</td>
        <td><font color="red">NULL</font></td>
        <td>428573184</td>
 	</tr>
	<tr>
  		<td style="text-align:center;"><code>3</code></td>
   		<td>Edward E. Rawson</td>
        <td><font color="red">NULL</font></td>
        <td><font color="red">NULL</font></td>
 	</tr>
    <tr>
  		<td style="text-align:center;"><code>4</code></td>
   		<td>Samantha</td>
        <td>1963-04-04</td>
        <td>458294720</td>
 	</tr>
</table>

```SQL
CREATE TABLE `Holder` (
  `Holder_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Holder_Name` varchar(255) NOT NULL,
  `Holder_DOB` date DEFAULT NULL,
  `Holder_TFN` int(11) DEFAULT NULL,
  PRIMARY KEY (`Holder_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

### <p style="background-color:#D68910; color:white;">Institution</p>
- Institutions are bodies which issue entities to holders
- Institution_IDs are unique and assigned upon institution creation.

<table>
	<tr>
		<th style="text-align:center;">Field</th>
		<th style="text-align:center;">Type</th>
		<th style="text-align:center;">Null</th>
        <th style="text-align:center;">Key</th>
        <th style="text-align:center;">Default</th>
        <th style="text-align:center;">Extra</th>
 	</tr>
 	<tr>
  		<td style="font-weight:bold; text-align:right;">Institution_ID</td>
   		<td style="text-align:center;">int(11)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;">PRI</td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;">auto_increment</td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Code</td>
   		<td style="text-align:center;">varchar(5)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;">MUL</td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Name</td>
   		<td style="text-align:center;">varchar(255)</td>
        <td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Address</td>
   		<td style="text-align:center;">varchar(255)</td>
        <td style="text-align:center;">YES</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Comments</td>
   		<td style="text-align:center;">varchar(255)</td>
        <td style="text-align:center;">YES</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
</table>

Example `Institution` Table:
<table>
	<tr>
		<th style="text-align:center;">Institution_ID</th>
		<th style="text-align:center;">Code</th>
        <th style="text-align:center;">Name</th>
        <th style="text-align:center;">Address</th>
        <th style="text-align:center;">Comments</th>
 	</tr>
 	<tr>
  		<td style="text-align:center;"><code>1</code></td>
   		<td style="text-align:center;"><code>CBA</code></td>
		<td>Commonwealth Bank of Australia</td>
        <td><font color="red">NULL</font></td>
        <td><font color="red">NULL</font></td>
 	</tr>
	<tr>
  		<td style="text-align:center;"><code>2</code></td>
   		<td style="text-align:center;"><code>NAB</code></td>
        <td>National Australia Bank</td>
        <td>1 Bank Ave, Sydney</td>
        <td><font color="red">NULL</font></td>
 	</tr>
	<tr>
  		<td style="text-align:center;"><code>3</code></td>
   		<td style="text-align:center;"><code>WBC</code></td>
        <td>Westpac Banking Corporation</td>
        <td><font color="red">NULL</font></td>
        <td>I like this bank</td>
 	</tr>
</table>

```SQL
CREATE TABLE `Institution` (
  `Institution_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Code` varchar(5) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Address` varchar(255) DEFAULT NULL,
  `Comments` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Institution_ID`),
  KEY `Code` (`Code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

### <p style="background-color:#27AE60; color:white;">Entity</p>
- Entities are given to holders by institutions (e.g. credit cards, mortgages, stocks, properties, etc.).
- Entity_IDs are unique and assigned upon entity creation.

<table>
	<tr>
		<th style="text-align:center;">Field</th>
		<th style="text-align:center;">Type</th>
		<th style="text-align:center;">Null</th>
        <th style="text-align:center;">Key</th>
        <th style="text-align:center;">Default</th>
        <th style="text-align:center;">Extra</th>
 	</tr>
 	<tr>
  		<td style="font-weight:bold; text-align:right;">Entity_ID</td>
   		<td style="text-align:center;">int(11)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;">PRI</td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;">auto_increment</td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Institution_ID</td>
   		<td style="text-align:center;">int(11)</td>
        <td style="text-align:center;">NO</td>
        <td style="text-align:center;">MUL</td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Entity_Type</td>
   		<td style="text-align:center;">int(11)</td>
        <td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Entity_Number</td>
   		<td style="text-align:center;">varchar(255)</td>
        <td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Entity_Status</td>
   		<td style="text-align:center;">bit(1)</td>
        <td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Entity_Start_Date</td>
   		<td style="text-align:center;">date</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Entity_End_Date</td>
   		<td style="text-align:center;">date</td>
        <td style="text-align:center;">YES</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Description</td>
   		<td style="text-align:center;">varchar(255)</td>
        <td style="text-align:center;">YES</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Comments</td>
   		<td style="text-align:center;">varchar(255)</td>
        <td style="text-align:center;">YES</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
</table>

Example `Entity` Table:
<table>
	<tr>
		<th style="text-align:center;">Entity_ID</th>
		<th style="text-align:center;">Institution_ID</th>
		<th style="text-align:center;">Entity_Type</th>
		<th style="text-align:center;">Entity_Number</th>
		<th style="text-align:center;">Entity_Status</th>
		<th style="text-align:center;">Entity_Start_Date</th>
        <th style="text-align:center;">Entity_End_Date</th>
		<th style="text-align:center;">Description</th>
        <th style="text-align:center;">Comments</th>
 	</tr>
 	<tr>
  		<td style="text-align:center;"><code>98</code></td>
		<td style="text-align:center;"><code>1</code></td>
		<td>0</td>
		<td>582-482<br>38589372</td>
		<td>1</td>
		<td>2001-01-03</td>
		<td>2005-02-14</td>
		<td><font color="red">NULL</font></td>
        <td>Replacement</td>
 	</tr>
	<tr>
  		<td style="text-align:center;"><code>99</code></td>
		<td style="text-align:center;"><code>2</code></td>
		<td>0</td>
		<td>394-482<br>48295832</td>
		<td>0</td>
		<td>2001-04-06</td>
        <td><font color="red">NULL</font></td>
		<td><font color="red">NULL</font></td>
        <td><font color="red">NULL</font></td>
 	</tr>
	<tr>
  		<td style="text-align:center;"><code>100</code></td>
		<td style="text-align:center;"><code>3</code></td>
		<td>0</td>
		<td>375-284<br>47294729</td>
		<td>0</td>
		<td>2001-03-10</td>
        <td><font color="red">NULL</font></td>
		<td>My Card</td>
        <td><font color="red">NULL</font></td>
 	</tr>
</table>

```SQL
CREATE TABLE `Entity` (
  `Entity_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Institution_ID` int(11) NOT NULL,
  `Entity_Type` int(11) NOT NULL,
  `Entity_Number` varchar(255) NOT NULL,
  `Entity_Status` bit(1) NOT NULL,
  `Entity_Start_Date` date NOT NULL,
  `Entity_End_Date` date DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `Comments` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Entity_ID`),
  KEY `FK_Entity_Institution` (`Institution_ID`),
  CONSTRAINT `FK_Entity_Institution` FOREIGN KEY (`Institution_ID`) REFERENCES `Institution` (`Institution_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

### <p style="background-color:#27AE60; color:white;">Service</p>
- Services are provided to holders of an entity (e.g. internet banking).
- Service_IDs are unique and assigned upon service creation.

<table>
	<tr>
		<th style="text-align:center;">Field</th>
		<th style="text-align:center;">Type</th>
		<th style="text-align:center;">Null</th>
        <th style="text-align:center;">Key</th>
        <th style="text-align:center;">Default</th>
        <th style="text-align:center;">Extra</th>
 	</tr>
 	<tr>
  		<td style="font-weight:bold; text-align:right;">Service_ID</td>
   		<td style="text-align:center;">int(11)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;">PRI</td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;">auto_increment</td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Entity_ID</td>
   		<td style="text-align:center;">int(11)</td>
        <td style="text-align:center;">NO</td>
        <td style="text-align:center;">MUL</td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Service_Type</td>
   		<td style="text-align:center;">int(11)</td>
        <td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Expiry_Date</td>
   		<td style="text-align:center;">date</td>
        <td style="text-align:center;">YES</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">User_ID</td>
   		<td style="text-align:center;">varchar(255)</td>
        <td style="text-align:center;">YES</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Pwd_PIN</td>
   		<td style="text-align:center;">varchar(255)</td>
        <td style="text-align:center;">YES</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Frequency</td>
   		<td style="text-align:center;">varchar(255)</td>
        <td style="text-align:center;">YES</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Contact</td>
   		<td style="text-align:center;">varchar(255)</td>
        <td style="text-align:center;">YES</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Description</td>
   		<td style="text-align:center;">varchar(255)</td>
		<td style="text-align:center;">YES</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
</table>

Example `Service` Table:
<table>
	<tr>
		<th style="text-align:center;">Service_ID</th>
		<th style="text-align:center;">Entity_ID</th>
		<th style="text-align:center;">Service_Type</th>
		<th style="text-align:center;">Expiry_Date</th>
        <th style="text-align:center;">User_ID</th>
        <th style="text-align:center;">Pwd_PIN</th>
		<th style="text-align:center;">Frequency</th>
        <th style="text-align:center;">Contact</th>
		<th style="text-align:center;">Description</th>
 	</tr>
 	<tr>
  		<td style="text-align:center;"><code>1</code></td>
		<td style="text-align:center;"><code>15</code></td>
		<td>1</td>
		<td><font color="red">NULL</font></td>
        <td><font color="red">NULL</font></td>
        <td><font color="red">NULL</font></td>
		<td>Fortnightly</td>
        <td><font color="red">NULL</font></td>
		<td>Bob's Centrelink (293 384 323)</td>
 	</tr>
	<tr>
  		<td style="text-align:center;"><code>2</code></td>
		<td style="text-align:center;"><code>15</code></td>
		<td>1</td>
		<td>1999-04-23</td>
        <td><font color="red">NULL</font></td>
        <td><font color="red">NULL</font></td>
		<td>Fortnightly</td>
        <td><font color="red">NULL</font></td>
		<td><font color="red">NULL</font></td>
 	</tr>
	<tr>
  		<td style="text-align:center;"><code>3</code></td>
		<td style="text-align:center;"><code>16</code></td>
		<td>1</td>
		<td>1999-02-15</td>
        <td><font color="red">NULL</font></td>
        <td><font color="red">NULL</font></td>
        <td><font color="red">NULL</font></td>
        <td><font color="red">NULL</font></td>
		<td>Martin's Salary from CBA</td>
 	</tr>
</table>

```SQL
CREATE TABLE `Service` (
  `Service_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Entity_ID` int(11) NOT NULL,
  `Service_Type` int(11) NOT NULL,
  `Expiry_Date` date DEFAULT NULL,
  `User_ID` varchar(255) DEFAULT NULL,
  `Pwd_PIN` varchar(255) DEFAULT NULL,
  `Frequency` varchar(255) DEFAULT NULL,
  `Contact` varchar(255) DEFAULT NULL,
  `Description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Service_ID`),
  KEY `FK_Service_Entity` (`Entity_ID`),
  CONSTRAINT `FK_Service_Entity` FOREIGN KEY (`Entity_ID`) REFERENCES `Entity` (`Entity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

### <p style="background-color:#2471A3; color:white;">EntityHistory</p>
- `Entity_ID` represents the child/recipient/successor entity, whilst `Linked_Entity_ID` represents the parent/provider/predecessor entity.
- Neither `Entity_ID` or `Linked_Entity_ID` are unique since entities can have several parents or children.

<table>
	<tr>
		<th style="text-align:center;">Field</th>
		<th style="text-align:center;">Type</th>
		<th style="text-align:center;">Null</th>
        <th style="text-align:center;">Key</th>
        <th style="text-align:center;">Default</th>
        <th style="text-align:center;">Extra</th>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Entity_ID</td>
   		<td style="text-align:center;">int(11)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;">MUL</td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
 	<tr>
  		<td style="font-weight:bold; text-align:right;">Linked_Entity_ID</td>
   		<td style="text-align:center;">int(11)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;">MUL</td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
</table>

Example `EntityHistory` Table:
<table>
	<tr>
		<th style="text-align:center;">Entity_ID</th>
		<th style="text-align:center;">Linked_Entity_ID</th>
 	</tr>
 	<tr>
  		<td style="text-align:center;"><code>14</code></td>
		<td style="text-align:center;"><code>10</code></td>
 	</tr>
	<tr>
  		<td style="text-align:center;"><code>14</code></td>
		<td style="text-align:center;"><code>11</code></td>
 	</tr>
	<tr>
  		<td style="text-align:center;"><code>15</code></td>
		<td style="text-align:center;"><code>14</code></td>
 	</tr>
    <tr>
        <td style="text-align:center;"><code>15</code></td>
		<td style="text-align:center;"><code>13</code></td>
 	</tr>
</table>

```SQL
CREATE TABLE `EntityHistory` (
  `Entity_ID` int(11) NOT NULL,
  `Linked_Entity_ID` int(11) NOT NULL,
  KEY `Entity_ID` (`Entity_ID`),
  KEY `Linked_Entity_ID` (`Linked_Entity_ID`),
  CONSTRAINT `FK_EntityHistory_Entity` FOREIGN KEY (`Entity_ID`) REFERENCES `Entity` (`Entity_ID`),
  CONSTRAINT `FK_EntityHistory_Linked` FOREIGN KEY (`Linked_Entity_ID`) REFERENCES `Entity` (`Entity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

### <p style="background-color:#2471A3; color:white;">HolderEntity</p>
- Neither `Entity_ID` or `Holder_ID` are unique, since entities can have multiple holders and holders can own multiple entities.

<table>
	<tr>
		<th style="text-align:center;">Field</th>
		<th style="text-align:center;">Type</th>
		<th style="text-align:center;">Null</th>
        <th style="text-align:center;">Key</th>
        <th style="text-align:center;">Default</th>
        <th style="text-align:center;">Extra</th>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Entity_ID</td>
   		<td style="text-align:center;">int(11)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;">MUL</td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
 	<tr>
  		<td style="font-weight:bold; text-align:right;">Holder_ID</td>
   		<td style="text-align:center;">int(11)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;">MUL</td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
</table>

Example `HolderEntity` Table:
<table>
	<tr>
		<th style="text-align:center;">Entity_ID</th>
		<th style="text-align:center;">Holder_ID</th>
 	</tr>
 	<tr>
  		<td style="text-align:center;"><code>45</code></td>
		<td style="text-align:center;"><code>1</code></td>
 	</tr>
	<tr>
  		<td style="text-align:center;"><code>46</code></td>
		<td style="text-align:center;"><code>1</code></td>
 	</tr>
	<tr>
  		<td style="text-align:center;"><code>45</code></td>
		<td style="text-align:center;"><code>2</code></td>
 	</tr>
</table>

```SQL
CREATE TABLE `HolderEntity` (
  `Entity_ID` int(11) NOT NULL,
  `Holder_ID` int(11) NOT NULL,
  KEY `FK_HolderEntity_Entity` (`Entity_ID`),
  KEY `FK_HolderEntity_Holder` (`Holder_ID`),
  CONSTRAINT `FK_HolderEntity_Entity` FOREIGN KEY (`Entity_ID`) REFERENCES `Entity` (`Entity_ID`),
  CONSTRAINT `FK_HolderEntity_Holder` FOREIGN KEY (`Holder_ID`) REFERENCES `Holder` (`Holder_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

### <p style="background-color:#6C3483; color:white;">EntityShrTxn</p>
- Record of all transactions that occur for a given set of shares or managed funds (e.g. purchase, sale, dividends and dividend reinvestment).
- Not all fields are used depending on the transaction type (e.g. capital gains tax is only relevant when shares have been sold). Fields irrelevant to the type of transaction being stored are expected to be populated with zero values.

<table>
	<tr>
		<th style="text-align:center;">Field</th>
		<th style="text-align:center;">Type</th>
		<th style="text-align:center;">Null</th>
        <th style="text-align:center;">Key</th>
        <th style="text-align:center;">Default</th>
        <th style="text-align:center;">Extra</th>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Shr_Txn_ID</td>
   		<td style="text-align:center;">int(11)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;">PRI</td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;">auto_increment</td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Entity_ID</td>
   		<td style="text-align:center;">int(11)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;">MUL</td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
 	<tr>
  		<td style="font-weight:bold; text-align:right;">Date</td>
   		<td style="text-align:center;">date</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Txn_SubType</td>
   		<td style="text-align:center;">int(11)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Number_Shares</td>
   		<td style="text-align:center;">float</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Share_Price</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Dividend_Amount</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Franking_Percentage</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Capital_Gains_Tax</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Brokerage_Charges</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Other_Charges</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Description</td>
   		<td style="text-align:center;">varchar(255)</td>
		<td style="text-align:center;">YES</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
</table>

Example `EntityShrTxn` Table:
<table>
	<tr>
		<th style="text-align:center;">Shr_Txn_ID</th>
		<th style="text-align:center;">Entity_ID</th>
		<th style="text-align:center;">Date</th>
		<th style="text-align:center;">Txn_Subtype</th>
		<th style="text-align:center;">Number_Shares</th>
        <th style="text-align:center;">Share_Price</th>
		<th style="text-align:center;">Dividend_Amount</th>
		<th style="text-align:center;">Franking_Percentage</th>
		<th style="text-align:center;">Capital_Gains_Tax</th>
        <th style="text-align:center;">Brokerage_Charges</th>
        <th style="text-align:center;">Other_Charges</th>
		<th style="text-align:center;">Description</th>
 	</tr>
 	<tr>
  		<td style="text-align:center;"><code>1</code></td>
		<td style="text-align:center;"><code>1</code></td>
		<td>1/1/1970</td>
		<td>0</td>
		<td>100</td>
        <td>12.34</td>
		<td>0.00</td>
		<td>75.00</td>
		<td>0.00</td>
        <td>1.00</td>
        <td>0.00</td>
		<td><font color="red">NULL</font></td>
 	</tr>
	<tr>
		<td style="text-align:center;"><code>2</code></td>
		<td style="text-align:center;"><code>1</code></td>
  		<td>3/5/2002</td>
		<td>0</td>
		<td>50</td>
        <td>2.42</td>
		<td>0.00</td>
		<td>100.00</td>
		<td>0.00</td>
        <td>0.50</td>
        <td>0.00</td>
		<td><font color="red">NULL</font></td>
 	</tr>
	<tr>
		<td style="text-align:center;"><code>3</code></td>
		<td style="text-align:center;"><code>1</code></td>
  		<td>3/5/2008</td>
		<td>0</td>
		<td>100</td>
        <td>1.45</td>
		<td>0.00</td>
		<td>100.00</td>
		<td>0.00</td>
        <td>1.00</td>
        <td>0.00</td>
		<td><font color="red">NULL</font></td>
 	</tr>
</table>

```SQL
CREATE TABLE `EntityShrTxn` (
  `Shr_Txn_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Entity_ID` int(11) NOT NULL,
  `Date` date NOT NULL,
  `Txn_SubType` int(11) NOT NULL,
  `Number_Shares` float NOT NULL,
  `Share_Price` decimal(34,2) NOT NULL,
  `Dividend_Amount` decimal(34,2) NOT NULL,
  `Franking_Percentage` decimal(34,2) NOT NULL,
  `Capital_Gains_Tax` decimal(34,2) NOT NULL,
  `Brokerage_Charges` decimal(34,2) NOT NULL,
  `Other_Charges` decimal(34,2) NOT NULL,
  `Description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Shr_Txn_ID`),
  KEY `FK_EntityShrTxn_Entity` (`Entity_ID`),
  CONSTRAINT `FK_EntityShrTxn_Entity` FOREIGN KEY (`Entity_ID`) REFERENCES `Entity` (`Entity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

### <p style="background-color:#6C3483; color:white;">EntityTxn</p>
- Record of all transactions that occur for credit cards, savings accounts and superannuation funds.

<table>
	<tr>
		<th style="text-align:center;">Field</th>
		<th style="text-align:center;">Type</th>
		<th style="text-align:center;">Null</th>
        <th style="text-align:center;">Key</th>
        <th style="text-align:center;">Default</th>
        <th style="text-align:center;">Extra</th>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Txn_ID</td>
   		<td style="text-align:center;">int(11)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;">PRI</td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;">auto_increment</td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Entity_ID</td>
   		<td style="text-align:center;">int(11)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;">MUL</td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Txn_Date</td>
   		<td style="text-align:center;">date</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Txn_Catg</td>
   		<td style="text-align:center;">int(11)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Txn_DR</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Txn_CR</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Txn_Description</td>
   		<td style="text-align:center;">varchar(255)</td>
		<td style="text-align:center;">YES</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
</table>

Example `EntityTxn` Table:
<table>
	<tr>
		<th style="text-align:center;">Txn_ID</th>
		<th style="text-align:center;">Entity_ID</th>
		<th style="text-align:center;">Txn_Date</th>
		<th style="text-align:center;">Txn_Catg</th>
        <th style="text-align:center;">Txn_DR</th>
        <th style="text-align:center;">Txn_CR</th>
		<th style="text-align:center;">Txn_Description</th>
 	</tr>
 	<tr>
		<td style="text-align:center;"><code>1</code></td>
		<td style="text-align:center;"><code>2</code></td>
  		<td>1/1/2000</td>
   		<td>9</td>
        <td>41.64</td>
        <td>0.00</td>
		<td><font color="red">NULL</font></td>
 	</tr>
	<tr>
		<td style="text-align:center;"><code>2</code></td>
		<td style="text-align:center;"><code>4</code></td>
  		<td>5/2/2007</td>
        <td>5</td>
        <td>0.00</td>
        <td>67.42</td>
		<td><font color="red">NULL</font></td>
 	</tr>
	<tr>
		<td style="text-align:center;"><code>3</code></td>
		<td style="text-align:center;"><code>5</code></td>
  		<td>12/11/2019</td>
        <td>9</td>
        <td>23.54</td>
        <td>0.00</td>
		<td><font color="red">NULL</font></td>
 	</tr>
</table>

```SQL
CREATE TABLE `EntityTxn` (
  `Txn_ID` int(11) NOT NULL AUTO_INCREMENT,
  `Entity_ID` int(11) NOT NULL,
  `Txn_Date` date NOT NULL,
  `Txn_Catg` int(11) NOT NULL,
  `Txn_DR` decimal(34,2) NOT NULL,
  `Txn_CR` decimal(34,2) NOT NULL,
  `Txn_Description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Txn_ID`),
  KEY `FK_EntityTxn_Entity` (`Entity_ID`),
  CONSTRAINT `FK_EntityTxn_Entity` FOREIGN KEY (`Entity_ID`) REFERENCES `Entity` (`Entity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

### <p style="background-color:#34495E; color:white;">CreditCard</p>
- Holds additional information regarding credit cards.

<table>
	<tr>
		<th style="text-align:center;">Field</th>
		<th style="text-align:center;">Type</th>
		<th style="text-align:center;">Null</th>
        <th style="text-align:center;">Key</th>
        <th style="text-align:center;">Default</th>
        <th style="text-align:center;">Extra</th>
 	</tr>
 	<tr>
  		<td style="font-weight:bold; text-align:right;">Entity_ID</td>
   		<td style="text-align:center;">int(11)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;">PRI</td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Card_Limit</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
</table>

Example `CreditCard` Table:
<table>
	<tr>
		<th style="text-align:center;">Entity_ID</th>
		<th style="text-align:center;">Card_Limit</th>
 	</tr>
 	<tr>
  		<td style="text-align:center;"><code>34</code></td>
   		<td>5000.00</td>
 	</tr>
	<tr>
  		<td style="text-align:center;"><code>56</code></td>
        <td>10000.00</td>
 	</tr>
	<tr>
        <td style="text-align:center;"><code>89</code></td>
        <td>12000.00</td>
 	</tr>
</table>

```SQL
CREATE TABLE `CreditCard` (
  `Entity_ID` int(11) NOT NULL,
  `Card_Limit` decimal(34,2) NOT NULL,
  PRIMARY KEY (`Entity_ID`),
  CONSTRAINT `FK_CreditCard_Entity` FOREIGN KEY (`Entity_ID`) REFERENCES `Entity` (`Entity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

### <p style="background-color:#34495E; color:white;">Insurance</p>
- Holds additional information regarding insurance policies.

<table>
	<tr>
		<th style="text-align:center;">Field</th>
		<th style="text-align:center;">Type</th>
		<th style="text-align:center;">Null</th>
        <th style="text-align:center;">Key</th>
        <th style="text-align:center;">Default</th>
        <th style="text-align:center;">Extra</th>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Entity_ID</td>
   		<td style="text-align:center;">int(11)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;">PRI</td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Insured_Amount</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
 	<tr>
  		<td style="font-weight:bold; text-align:right;">Insurance_Premium_PA</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
</table>

Example `Insurance` Table:
<table>
	<tr>
		<th style="text-align:center;">Entity_ID</th>
		<th style="text-align:center;">Insured_Amount</th>
		<th style="text-align:center;">Insurance_Premium_PA</th>
 	</tr>
 	<tr>
		<td style="text-align:center;"><code>34</code></td>
		<td>100000.00</td>
  		<td>2000.00</td>
 	</tr>
	<tr>
		<td style="text-align:center;"><code>23</code></td>
		<td>50000.00</td>
  		<td>600.00</td>
 	</tr>
	<tr>
		<td style="text-align:center;"><code>65</code></td>
		<td>20000.00</td>
        <td>180.00</td>
 	</tr>
</table>

```SQL
CREATE TABLE `Insurance` (
  `Entity_ID` int(11) NOT NULL,
  `Insured_Amount` decimal(34,2) NOT NULL,
  `Insurance_Premium_PA` decimal(34,2) NOT NULL,
  PRIMARY KEY (`Entity_ID`),
  CONSTRAINT `FK_Insurance_Entity` FOREIGN KEY (`Entity_ID`) REFERENCES `Entity` (`Entity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

### <p style="background-color:#34495E; color:white;">Loan</p>
- Holds additional information regarding loans.
- `Current_Value` represents the amount remaining to be repaid.

<table>
	<tr>
		<th style="text-align:center;">Field</th>
		<th style="text-align:center;">Type</th>
		<th style="text-align:center;">Null</th>
        <th style="text-align:center;">Key</th>
        <th style="text-align:center;">Default</th>
        <th style="text-align:center;">Extra</th>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Entity_ID</td>
   		<td style="text-align:center;">int(11)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;">PRI</td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
 	<tr>
  		<td style="font-weight:bold; text-align:right;">Loan_Amount</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Current_Value</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
</table>

Example `Loan` Table:
<table>
	<tr>
		<th style="text-align:center;">Entity_ID</th>
		<th style="text-align:center;">Loan_Amount</th>
        <th style="text-align:center;">Current_Value</th>
 	</tr>
 	<tr>
		<td style="text-align:center;"><code>43</code></td>
  		<td>300000.00</td>
        <td>150000.00</td>
 	</tr>
	<tr>
		<td style="text-align:center;"><code>18</code></td>
  		<td>500000.00</td>
        <td>2000.00</td>
 	</tr>
</table>

```SQL
CREATE TABLE `Loan` (
  `Entity_ID` int(11) NOT NULL,
  `Loan_Amount` decimal(34,2) NOT NULL,
  `Current_Value` decimal(34,2) NOT NULL,
  PRIMARY KEY (`Entity_ID`),
  CONSTRAINT `FK_Loan_Entity` FOREIGN KEY (`Entity_ID`) REFERENCES `Entity` (`Entity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

### <p style="background-color:#34495E; color:white;">Property</p>
- Holds additional information regarding properties.
- If a property is yet to be sold, all fields relevant to the sale of a property (e.g. `Capital_Gains_Tax`) are expected to be zeroed.

<table>
	<tr>
		<th style="text-align:center;">Field</th>
		<th style="text-align:center;">Type</th>
		<th style="text-align:center;">Null</th>
        <th style="text-align:center;">Key</th>
        <th style="text-align:center;">Default</th>
        <th style="text-align:center;">Extra</th>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Entity_ID</td>
   		<td style="text-align:center;">int(11)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;">PRI</td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
 	<tr>
  		<td style="font-weight:bold; text-align:right;">Address</td>
   		<td style="text-align:center;">varchar(255)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Current_Value</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Purchase_Amount</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Sold_Amount</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Solicitors_Fees_Buy</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Solicitors_Fees_Sell</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Govt_Charges_Buy</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Govt_Charges_Sell</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Agent_Fees_Buy</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Agent_Fees_Sell</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Capital_Gains_Tax</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
</table>

Example `Property` Table:
<table>
	<tr>
		<th style="text-align:center;">Entity_ID</th>
		<th style="text-align:center;">Address</th>
		<th style="text-align:center;">Current_Value</th>
		<th style="text-align:center;">Purchase_Amount</th>
		<th style="text-align:center;">Sold_Amount</th>
        <th style="text-align:center;">Solicitors_Fees_Buy</th>
        <th style="text-align:center;">Solicitors_Fees_Sell</th>
        <th style="text-align:center;">Govt_Charges_Buy</th>
        <th style="text-align:center;">Govt_Charges_Sell</th>
        <th style="text-align:center;">Agent_Fees_Buy</th>
        <th style="text-align:center;">Agent_Fees_Sell</th>
        <th style="text-align:center;">Capital_Gains_Tax</th>
 	</tr>
 	<tr>
		<td style="text-align:center;"><code>34</code></td>
  		<td>24 Abbott Lane, Smithfield</td>
		<td>1400000.00</td>
   		<td>1200000.00</td>
		<td>0.00</td>
        <td>2000.00</td>
        <td>0.00</td>
        <td>20.00</td>
        <td>0.00</td>
        <td>300.00</td>
        <td>0.00</td>
        <td>0.00</td>
 	</tr>
	<tr>
		<td style="text-align:center;"><code>56</code></td>
  		<td>18 Edward Ln, Randwick</td>
		<td>2200000.00</td>
        <td>1800000.00</td>
		<td>0.00</td>
        <td>1000.00</td>
        <td>0.00</td>
        <td>10.00</td>
        <td>0.00</td>
        <td>150.00</td>
        <td>0.00</td>
        <td>0.00</td>
 	</tr>
</table>

```SQL
CREATE TABLE `Property` (
  `Entity_ID` int(11) NOT NULL,
  `Address` varchar(255) NOT NULL,
  `Current_Value` decimal(34,2) NOT NULL,
  `Purchase_Amount` decimal(34,2) NOT NULL,
  `Sold_Amount` decimal(34,2) NOT NULL,
  `Solicitors_Fees_Buy` decimal(34,2) NOT NULL,
  `Solicitors_Fees_Sell` decimal(34,2) NOT NULL,
  `Govt_Charges_Buy` decimal(34,2) NOT NULL,
  `Govt_Charges_Sell` decimal(34,2) NOT NULL,
  `Agent_Fees_Buy` decimal(34,2) NOT NULL,
  `Agent_Fees_Sell` decimal(34,2) NOT NULL,
  `Capital_Gains_Tax` decimal(34,2) NOT NULL,
  PRIMARY KEY (`Entity_ID`),
  CONSTRAINT `FK_Property_Entity` FOREIGN KEY (`Entity_ID`) REFERENCES `Entity` (`Entity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

### <p style="background-color:#34495E; color:white;">Shares</p>
- Holds additional information regarding shares/managed funds.

<table>
	<tr>
		<th style="text-align:center;">Field</th>
		<th style="text-align:center;">Type</th>
		<th style="text-align:center;">Null</th>
        <th style="text-align:center;">Key</th>
        <th style="text-align:center;">Default</th>
        <th style="text-align:center;">Extra</th>
 	</tr>
 	<tr>
  		<td style="font-weight:bold; text-align:right;">Entity_ID</td>
   		<td style="text-align:center;">int(11)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;">PRI</td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Current_Unit_Value</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
</table>

Example `Shares` Table:
<table>
	<tr>
		<th style="text-align:center;">Entity_ID</th>
        <th style="text-align:center;">Current_Unit_Value</th>
 	</tr>
 	<tr>
  		<td style="text-align:center;"><code>183</code></td>
        <td>3.55</td>
 	</tr>
	<tr>
  		<td style="text-align:center;"><code>188</code></td>
        <td>34.85</td>
 	</tr>
</table>

```SQL
CREATE TABLE `Shares` (
  `Entity_ID` int(11) NOT NULL,
  `Current_Unit_Value` decimal(34,2) NOT NULL,
  PRIMARY KEY (`Entity_ID`),
  CONSTRAINT `FK_Shares_Entity` FOREIGN KEY (`Entity_ID`) REFERENCES `Entity` (`Entity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

### <p style="background-color:#34495E; color:white;">TermDeposit</p>
- Holds additional information regarding term deposits.

<table>
	<tr>
		<th style="text-align:center;">Field</th>
		<th style="text-align:center;">Type</th>
		<th style="text-align:center;">Null</th>
        <th style="text-align:center;">Key</th>
        <th style="text-align:center;">Default</th>
        <th style="text-align:center;">Extra</th>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Entity_ID</td>
   		<td style="text-align:center;">int(11)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;">PRI</td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
 	<tr>
  		<td style="font-weight:bold; text-align:right;">Opening_Balance</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Interest_Rate</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Interest_Amount</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
	<tr>
  		<td style="font-weight:bold; text-align:right;">Bank_Fees</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
    <tr>
  		<td style="font-weight:bold; text-align:right;">Other_Charges</td>
   		<td style="text-align:center;">decimal(34,2)</td>
		<td style="text-align:center;">NO</td>
        <td style="text-align:center;"></td>
        <td style="text-align:center;">NULL</td>
        <td style="text-align:center;"></td>
 	</tr>
</table>

Example `TermDeposit` Table:
<table>
	<tr>
		<th style="text-align:center;">Entity_ID</th>
		<th style="text-align:center;">Opening_Balance</th>
		<th style="text-align:center;">Interest_Rate</th>
		<th style="text-align:center;">Interest_Amount</th>
		<th style="text-align:center;">Bank_Fees</th>
        <th style="text-align:center;">Other_Charges</th>
 	</tr>
 	<tr>
		<td style="text-align:center;"><code>24</code></td>
  		<td>8500.00</td>
		<td>5.25</td>
   		<td>160.00</td>
        <td>0.00</td>
        <td>0.00</td>
 	</tr>
 	<tr>
		<td style="text-align:center;"><code>57</code></td>
  		<td>20000.00</td>
		<td>4.65</td>
   		<td>1300.00</td>
        <td>0.00</td>
        <td>0.00</td>
 	</tr>
</table>

```SQL
CREATE TABLE `TermDeposit` (
  `Entity_ID` int(11) NOT NULL,
  `Opening_Balance` decimal(34,2) NOT NULL,
  `Interest_Rate` decimal(34,2) NOT NULL,
  `Interest_Amount` decimal(34,2) NOT NULL,
  `Bank_Fees` decimal(34,2) NOT NULL,
  `Other_Charges` decimal(34,2) NOT NULL,
  PRIMARY KEY (`Entity_ID`),
  CONSTRAINT `FK_TermDeposit_Entity` FOREIGN KEY (`Entity_ID`) REFERENCES `Entity` (`Entity_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## Debugging Cheat Sheet
MariaDB provides the backend database functionality for ALRv2. MariaDB installation instructions and database locations on other operating systems will vary. Below are some basic commands that can be used with MariaDB in order to assist any potential future debugging.

<table>
	<tr>
		<th>Command</th>
		<th>Description</th>
 	</tr>
 	<tr>
  		<td bgcolor="#145A32"><code>mysql.server start</code></td>
   		<td bgcolor="#145A32"><font color="#ffffff">Starts the MariaDB Service.</font></td>
 	</tr>
	<tr>
  		<td bgcolor="#145A32"><code>mysqladmin shutdown</code></td>
   		<td bgcolor="#145A32"><font color="#ffffff">Stops the MariaDB Service.</font></td>
 	</tr>
	<tr>
  		<td bgcolor="#641E16"><code>CREATE USER 'NAME_OF_USER'@'localhost' IDENTIFIED BY 'ADD_A_PASSWORD';</code></td>
   		<td bgcolor="#641E16"><font color="#ffffff">Creates a user and sets a password for them.</font></td>
 	</tr>
	<tr>
  		<td bgcolor="#641E16"><code>ALTER USER 'NAME_OF_USER'@'localhost' IDENTIFIED BY 'NEW_PASSWORD';</code></td>
   		<td bgcolor="#641E16"><font color="#ffffff">Changes the password for a user.</font></td>
 	</tr>
    <tr>
  		<td bgcolor="#641E16"><code>SELECT host, user, password FROM mysql.user;</code></td>
   		<td bgcolor="#641E16"><font color="#ffffff">Lists all users.</font></td>
 	</tr>
    <tr>
  		<td bgcolor="#641E16"><code>GRANT ALL privileges ON `ALR_DB`.* TO 'RohanBarar'@localhost;</code></td>
   		<td bgcolor="#641E16"><font color="#ffffff">Grants all privileges to a given user.</font></td>
 	</tr>
    <tr>
  		<td bgcolor="#6E2C00"><code>CREATE DATABASE DatabaseName;</code></td>
   		<td bgcolor="#6E2C00"><font color="#ffffff">Creates a database.</font></td>
 	</tr>
    <tr>
  		<td bgcolor="#6E2C00"><code>SHOW DATABASES;</code></td>
   		<td bgcolor="#6E2C00"><font color="#ffffff">Lists all databases.</font></td>
 	</tr>
    <tr>
  		<td bgcolor="#6E2C00"><code>USE database_name;</code></td>
   		<td bgcolor="#6E2C00"><font color="#ffffff">Selects a database for work.</font></td>
 	</tr>
    <tr>
  		<td bgcolor="#154360"><code>CREATE TABLE Book(id INT NOT NULL AUTO_INCREMENT, Name VARCHAR(100) NOT NULL, PRIMARY KEY (id));</code></td>
   		<td bgcolor="#154360"><font color="#ffffff">Creates a table within the selected database. Name, then data type followed by optional modifiers. NOT NULL is self explanatory. AUTO_INCREMENT will automatically increment the id whenever a new entry is added. PRIMARY KEY selects the column to be marked as primary.</font></td>
 	</tr>
    <tr>
  		<td bgcolor="#154360"><code>SHOW TABLES;</code></td>
   		<td bgcolor="#154360"><font color="#ffffff">Lists all tables within a database.</font></td>
 	</tr>
    <tr>
  		<td bgcolor="#154360"><code>DESC TableName;</code></td>
   		<td bgcolor="#154360"><font color="#ffffff">Describes a given table within a database.</font></td>
 	</tr>
    <tr>
  		<td bgcolor="#154360"><code>INSERT INTO book (id, name) VALUES(1, 'MariaDB Book'), (2, 'Example2');</code></td>
   		<td bgcolor="#154360"><font color="#ffffff">Inserts multiple records into the table, populating the specified columns with the data specified within that same order.</font></td>
 	</tr>
    <tr>
  		<td bgcolor="#154360"><code>SELECT * FROM book;</code></td>
   		<td bgcolor="#154360"><font color="#ffffff">Selects all records from the table book.</font></td>
 	</tr>
    <tr>
  		<td bgcolor="#154360"><code>UPDATE book SET name='Blah', author='haha' WHERE id = 1;</code></td>
   		<td bgcolor="#154360"><font color="#ffffff">Updates a selected record with new column information given a condition (in this case, the id number).</font></td>
 	</tr>
    <tr>
  		<td bgcolor="#154360"><code>Select * FROM book WHERE author = “Author2” AND name = “ass”;</code></td>
   		<td bgcolor="#154360"><font color="#ffffff">Selects all records within the table book where the author is Author2 and the name of the book is ass. OR can also be used instead of AND.</font></td>
 	</tr>
    <tr>
  		<td bgcolor="#154360"><code>SHOW CREATE TABLE Tablename;</code></td>
   		<td bgcolor="#154360"><font color="#ffffff">Output the SQL instructions used to recreate the table.</font></td>
 	</tr>
    <tr>
  		<td bgcolor="#154360"><code>ALTER TABLE Tablename ADD PRIMARY KEY(id)</code></td>
   		<td bgcolor="#154360"><font color="#ffffff">Add the column 'id' as the primary key for the table.</font></td>
 	</tr>
    <tr>
  		<td bgcolor="#154360"><code>ALTER TABLE Tablename MODIFY COLUMN Columnname &lt;datatype&gt; &lt;conditions&gt;</code></td>
   		<td bgcolor="#154360"><font color="#ffffff">Modify an existing column's datatype or conditions (e.g. NOT NULL, auto_increment, etc.)</font></td>
 	</tr>
    <tr>
  		<td bgcolor="#154360"><code>ALTER TABLE TargetTable ADD CONSTRAINT FK_TargetTable_SourceTable_ColumnName FOREIGN KEY (ColumnName) REFERENCES SourceTable(ColumnName);</code></td>
   		<td bgcolor="#154360"><font color="#ffffff">Creates a foreign key linking the specified column in the target table with the specified column in the source table.</font></td>
 	</tr>
    <tr>
  		<td bgcolor="#154360"><code>ALTER TABLE Tablename DROP FOREIGN KEY FK_Name;</code></td>
   		<td bgcolor="#154360"><font color="#ffffff">Deletes the foreign key 'FK_Name' from the specified table.</font></td>
 	</tr>
    <tr>
  		<td bgcolor="#4A235A"><code>mysqldump -u username -p database_name > data-dump.sql</code></td>
   		<td bgcolor="#4A235A"><font color="#ffffff">Requests the user's password before backing up the database to the selected path as a SQL file. Note this command should be run OUTSIDE the MariaDB shell.</font></td>
 	</tr>
    <tr>
  		<td bgcolor="#4A235A"><code>mysql new_database < data-dump.sql</code></td>
   		<td bgcolor="#4A235A"><font color="#ffffff">Restores a backup of a database. Note that the new_database should be created before running this import command. Note that a user will have to be granted privileges on this database again. Note this command should be run OUTSIDE the MariaDB shell.</font></td>
 	</tr>
</table>
