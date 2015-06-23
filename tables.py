#
# tables.py
#

if __name__ == '__main__':

	from connect import Connect 

	# automatically sets default schema
	handler = Connect() 

	######
	#### employee table 
	######

	# drop 
	new_table = "EMPLOYEE"
	if handler.table_exists(new_table):
		query = "DROP TABLE %s" % new_table
		handler.run_query(query)

	# create
	query = """
		CREATE TABLE PROFILE.EMPLOYEE (
			firstname VARCHAR(30),
			lastname VARCHAR(30),
			email VARCHAR(50) PRIMARY KEY NOT NULL,
			traveler_profile VARCHAR(30)
		)""" 
	handler.run_query(query)

	# insert 
	query = """
		INSERT INTO PROFILE.EMPLOYEE
			SELECT distinct firstname, lastname, email, traveler_profile	
			FROM PROFILE.BIGTABLE_EMPLOYEE
		"""
	handler.run_query(query)
	print "Table '%s' successfully created." % (new_table)


	######
	#### membership table 
	######

	# drop 
	new_table = "MEMBERSHIP"
	if handler.table_exists(new_table):
		query = "DROP TABLE %s" % new_table
		handler.run_query(query)

	# create
	query = """
		CREATE TABLE PROFILE.MEMBERSHIP (
			traveler_profile VARCHAR(30),
			id BIGINT, 
			agency_code CHAR(5),
			loyalty_type SMALLINT,
			provider_code CHAR(5),
			loyalty_no VARCHAR(50)
		)""" 
	handler.run_query(query)

	# insert 
	query = """
		INSERT INTO PROFILE.MEMBERSHIP (
			SELECT traveler_profile, id, agency_code, loyalty_type, provider_code, loyalty_no 
			FROM PROFILE.BIGTABLE_EMPLOYEE
		)"""
	handler.run_query(query)
	print "Table '%s' successfully created." % (new_table)
