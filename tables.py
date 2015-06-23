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
			middlename VARCHAR(30),
			birthdate VARCHAR(12),
			phone VARCHAR(30),
			gender CHAR(1),
			email VARCHAR(50) UNIQUE NOT NULL, 
			traveler_profile VARCHAR(30) NOT NULL,
			CONSTRAINT pk_traveler_profile PRIMARY KEY(traveler_profile)
		)""" 
	handler.run_query(query)

	# insert 
	query = """
		INSERT INTO PROFILE.EMPLOYEE (firstname, lastname, email, traveler_profile)
			SELECT distinct firstname, lastname, email, traveler_profile	
			FROM PROFILE.BIGTABLE_EMPLOYEE
		"""
	handler.run_query(query)
	print "Table '%s' successfully created." % (new_table)


	######
	#### employee_detail table 
	######

	# drop 
	new_table = "EMPLOYEE_DETAIL"
	if handler.table_exists(new_table):
		query = "DROP TABLE %s" % new_table
		handler.run_query(query)

	# create
	query = """
		CREATE TABLE PROFILE.EMPLOYEE_DETAIL (
			email VARCHAR(50) NOT NULL, 
			birthdate VARCHAR(12),
			phone VARCHAR(30),
			gender CHAR(1),
			CONSTRAINT pk_email PRIMARY KEY(email)
		)""" 
	handler.run_query(query)

	# insert 
	query = """
		INSERT INTO PROFILE.EMPLOYEE_DETAIL (email, birthdate, phone, gender)
			SELECT distinct email, birthdate, phone, gender
			FROM PROFILE.BIGTABLE_EMPLOYEE_DETAIL
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
			loyalty_no VARCHAR(50),
			CONSTRAINT fk_traveler_profile FOREIGN KEY (traveler_profile)
			REFERENCES EMPLOYEE(traveler_profile)
			ON DELETE RESTRICT
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
