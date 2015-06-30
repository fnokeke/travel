#
# tables.py
#

if __name__ == '__main__':

	from connect import Connect 

	# automatically sets default schema
	handler = Connect() 

	######
	#### employee  
	######
	new_table = "EMPLOYEE"

	# drop 
	if handler.table_exists(new_table):
		query = "DROP TABLE %s" % new_table
		handler.run_query(query)

	# create
	query = """
		CREATE TABLE PROFILE.%s (
			firstname VARCHAR(30),
			lastname VARCHAR(30),
			middlename VARCHAR(30),
			birthdate VARCHAR(12),
			phone VARCHAR(30),
			gender CHAR(1),
			email VARCHAR(50) UNIQUE NOT NULL, 
			traveler_profile VARCHAR(30) NOT NULL,
			CONSTRAINT pk_traveler_profile PRIMARY KEY(traveler_profile)
		)""" % new_table
	handler.run_query(query)

	# insert 
	query = """
		INSERT INTO PROFILE.%s (firstname, lastname, email, traveler_profile)
			SELECT distinct firstname, lastname, email, traveler_profile	
			FROM PROFILE.BIGTABLE_EMPLOYEE
		""" % new_table
	handler.run_query(query)
	print "Table '%s' successfully created." % (new_table)


	######
	#### employee_detail
	######
	new_table = "EMPLOYEE_DETAIL"

	# drop 
	if handler.table_exists(new_table):
		query = "DROP TABLE %s" % new_table
		handler.run_query(query)

	# create
	query = """
		CREATE TABLE PROFILE.%s (
			traveler_profile VARCHAR(50) NOT NULL, 
			birthdate VARCHAR(12),
			gender CHAR(1),
			title CHAR(10),
			phone_code VARCHAR(5),
			phone VARCHAR(15),
			work_phone_code VARCHAR(5),
			work_phone VARCHAR(15),
			address_1 VARCHAR(40),
			address_2 VARCHAR(40),
			city VARCHAR(20),
			postalcode VARCHAR(10),
			country VARCHAR(20),
			CONSTRAINT fk_traveler_profile FOREIGN KEY (traveler_profile)
			REFERENCES EMPLOYEE(traveler_profile)
			ON DELETE RESTRICT
		)""" % (new_table)
	handler.run_query(query)

	# insert 
	query = """
		INSERT INTO PROFILE.%s (
				traveler_profile,
				birthdate,
				gender,
				title,
				phone_code,
				phone,
				work_phone_code,
				work_phone,
				address_1,
				address_2,
				city,
				postalcode,
				country
		) SELECT 
				travelerprofileid,
				pii_birth_date,
				pii_gender_code,
				title,
				phonecountrycode,
				phonenumber,
				work_phone_country_code,
				work_phone,
				address1,
				address2,
				city,
				postalcode,
				country
			FROM PROFILE.BIGTABLE_EMPLOYEE_DETAIL
		""" % (new_table)
	handler.run_query(query)
	print "Table '%s' successfully created." % (new_table)


	######
	#### membership
	######
	new_table = "MEMBERSHIP"

	# drop 
	if handler.table_exists(new_table):
		query = "DROP TABLE %s" % new_table
		handler.run_query(query)

	# create
	query = """
		CREATE TABLE PROFILE.%s (
			traveler_profile VARCHAR(30),
			id BIGINT, 
			agency_code CHAR(5),
			loyalty_type SMALLINT,
			provider_code CHAR(5),
			loyalty_no VARCHAR(50),
			CONSTRAINT fk_traveler_profile FOREIGN KEY (traveler_profile)
			REFERENCES EMPLOYEE(traveler_profile)
			ON DELETE RESTRICT
		)""" % new_table
	handler.run_query(query)

	# insert 
	query = """
		INSERT INTO PROFILE.%s (
			SELECT traveler_profile, id, agency_code, loyalty_type, provider_code, loyalty_no 
			FROM PROFILE.BIGTABLE_EMPLOYEE
		)""" % new_table
	handler.run_query(query)
	print "Table '%s' successfully created." % (new_table)
