import xlrd 
import os
import unicodedata

def format(val):

	if type(val) == float:
		return int(val)

	if type(val) == unicode:
		val = unicodedata.normalize('NFKD', val).encode('ascii','ignore')

	if val.find("'") >= 0:
		val = val.replace("'", '"')

	return str(val)


#################################################
#               MAIN
#################################################
if __name__ == '__main__':

	from connect import Connect 

	# automatically sets default schema
	handler = Connect() 

	# dump data from excel sheet
	print "Dumping data from excel into DB..."
	datasource = os.getcwd() + '/xl/Profile_data_Advanced.xlsx'
	book = xlrd.open_workbook(datasource) 
	sheet = book.sheet_by_index(0)

	col_names = sheet.row_values(0)
	col_names = [str(x) for x in col_names]
	col_types = [type(x) for x in sheet.row_values(1)]

	# col length is the length of the longest entry in the col 
	col_len = []
	for i in range(sheet.ncols):
		col = sheet.col_values(i)
		if type(col[i]) != float:
			col = [str(x) for x in col]
			col_max_len = max( [len(x) for x in col] )
			# avoid max length of size 0
			if col_max_len == 0:
				col_max_len = 1
			col_len.append(col_max_len)
		else:
			col_len.append(15) # 15 float means that it'll be BIGINT for length

	# create huge table (97 columns)
	all_col_types = []
	query = []

	for i in range(len(col_names)):
		if col_types[i] == float:
			if col_len[i] >= 10: 
				query.append( col_names[i] + " BIGINT" )
				all_col_types.append("BIGINT")
			elif col_len[i] >= 5: 
				query.append( col_names[i] + " INTEGER" ) 
				all_col_types.append("INTEGER")
			elif col_len[i] >= 0: 
				query.append( col_names[i] + " SMALLINT")
				all_col_types.append("SMALLINT")
		else:
			query.append( col_names[i] + " VARCHAR(" + str(col_len[i]) + ")" )
			all_col_types.append("VARCHAR")

	tmp = ','.join(query[:-1])

	if handler.table_exists("BIGTABLE_EMPLOYEE_DETAIL"):
		handler.run_query("DROP TABLE PROFILE.BIGTABLE_EMPLOYEE_DETAIL")

	query = "CREATE TABLE PROFILE.BIGTABLE_EMPLOYEE_DETAIL (" + tmp + "," + query[-1] + ");"
	handler.run_query(query)

	# dump from excel into table
	rows_dumped = 0
	rows_with_bug = []
	for i in range(1, sheet.nrows):
		row = sheet.row_values(i)

		try: 
			row = [format(x) for x in row]
		except UnicodeEncodeError: 
			print "Unicode bug: ", row 

		# to avoid DB2 BIGINT insert errors, convert '' to 0
		for index, value in enumerate(row):
			if all_col_types[index] in ["BIGINT", "SMALLINT", "INTEGER"]:
				if value == '':
					row[index] = 0

		row = tuple(row)

		query = "INSERT INTO BIGTABLE_ADV_PROFILE VALUES" + str(row)  + ";"
		try:
			handler.run_query(query)
		except:
			rows_with_bug.append(i)
			print handler.get_query_error()

		rows_dumped += 1

	print "done."
	print "Inserted", rows_dumped, "rows."
	print "No of rows with bugs:", len(rows_with_bug)
