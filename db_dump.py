import xlrd 
import ibm_db
import os
import unicodedata

#################################################
#               MAIN
#################################################

def format(val):
	if type(x) == unicode: 
		val = unicodedata.normalize('NFKD', val).encode('ascii','ignore')
		val = val.strip('\\x')
		if val.find("'") >= 0:
			val = val.replace("'", '"')

	else:
		val = int(val) 

	return val

if __name__ == '__main__':

	# connect to db
	dsn = ( 
			'DATABASE=travel;'
			'HOSTNAME=localhost;'
			'PORT=50000;'
			'PROTOCOL=TCPIP;'
			'UID=db2inst1;'
			'PWD=123456;'
	)
	conn = ibm_db.pconnect(dsn, "", "")
	print "DB connected."

	# set schema 
	ibm_db.exec_immediate(conn, "set schema = 'PROFILE'")

	# drop table
	stmt = ibm_db.exec_immediate(conn, 
			"select tabname from syscat.tables where tabschema='PROFILE' and tabname='employee'")
	# if stmt:
	ibm_db.exec_immediate(conn, "DROP TABLE PROFILE.EMPLOYEE")

	# create table
	ibm_db.exec_immediate(conn, 
	"""
		CREATE TABLE employee (firstname VARCHAR(30) NOT NULL, 
		lastname VARCHAR(30) NOT NULL, 
		email VARCHAR(50) NOT NULL, 
		id	BIGINT, 
		agency_code CHAR(5),		
		traveler_profile BIGINT,
		loyalty_type SMALLINT, 
		provider_code	CHAR(5),
		loyalty_no VARCHAR(50)
		);
	"""
	)

	# create index
	ibm_db.exec_immediate(conn, 
 		"CREATE INDEX I_EMPLOYEE_1 ON employee(traveler_profile);")
	
	# dump data from excel sheet
	print "Dumping data from excel into DB..."
	datasource = os.getcwd() + '/xl/Profile_Dec_Basic_Data.xlsx' 
	book = xlrd.open_workbook(datasource) 
	sheet = book.sheet_by_index(0)

	rows_dumped = 0
	for i in range(1, sheet.nrows):
		row = sheet.row_values(i)

		try: 
			row = [format(x) for x in row]
		except UnicodeEncodeError: 
			print "Unicode bug: ", row 

		row = tuple(row)
		query = "INSERT INTO EMPLOYEE VALUES" + str(row)  + ";"
		try:
			ibm_db.exec_immediate(conn, query)
		except:
			print row
			print "Query failed: " + query
			print ibm_db.stmt_errormsg()
		rows_dumped += 1

	print "done."
	print "Inserted", rows_dumped, "rows."
