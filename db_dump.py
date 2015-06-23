# db_dump.py

import xlrd 
import os
import unicodedata

def format(val):
	if type(x) == unicode: 
		val = unicodedata.normalize('NFKD', val).encode('ascii','ignore')
		val = val.strip('\\x')
		# O'Brian ==> O''Brian (for SQL insert)
		if val.find("'") >= 0:
			val = val.replace("'", '"')

	else:
		val = int(val) 

	return val


#################################################
#               MAIN
#################################################
if __name__ == '__main__':

	from connect import Connect 

	# automatically sets default schema
	handler = Connect() 

	# drop table
	handler.run_query("DROP TABLE PROFILE.BIGTABLE_EMPLOYEE")
			
	# create table
	handler.run_query(
		"""
			CREATE TABLE BIGTABLE_EMPLOYEE (
				firstname VARCHAR(30), 
				lastname VARCHAR(30), 
				email VARCHAR(50),
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
	handler.run_query(
 		"CREATE INDEX I_EMPLOYEE_1 ON BIGTABLE_EMPLOYEE(traveler_profile);")
	
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
		query = "INSERT INTO BIGTABLE_EMPLOYEE VALUES" + str(row)  + ";"
		try:
			handler.run_query(query)
		except:
			print row
			print "Query failed: " + query
			print handler.get_query_error()
		rows_dumped += 1

	print "done."
	print "Inserted", rows_dumped, "rows."
