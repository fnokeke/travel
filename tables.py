#
# tables.py
#

if __name__ == '__main__':

	from connect import Connect 

	# automatically sets default schema
	handler = Connect() 

	# tester
	# new_table, cols, old_table, primary-key
	handler.create_table(
			"smallerboy", 
			"firstname, lastname, email", 
			"BIGTABLE_EMPLOYEE") 

	exit(1)

	######
	#### employee table 
	######
	handler.run_query("""
	 DROP TABLE PROFILE.EMPLOYEE""")

	# create
	handler.run_query(
	"""
		CREATE TABLE PROFILE.EMPLOYEE AS (
			SELECT firstname, lastname, email
			FROM PROFILE.BIGTABLE_EMPLOYEE
		) WITH NO DATA;
	"""
	)

	# add primary key
	handler.run_query(
	"""
		ALTER TABLE PROFILE.EMPLOYEE ADD PRIMARY KEY (email);
	"""
	)
	

	# insert
	handler.run_query(
	"""
		INSERT INTO PROFILE.EMPLOYEE
			SELECT DISTINCT firstname, lastname, email
			FROM PROFILE.BIGTABLE_EMPLOYEE;
	"""
	)


	#######
	#### membership table
	######

	handler.run_query("""
	 DROP TABLE PROFILE.MEMBERSHIP""")

	# create
	handler.run_query(
	"""
		CREATE TABLE PROFILE.MEMBERSHIP AS (
			SELECT id, traveler_profile, agency_code, loyalty_type, provider_code, loyalty_no
			FROM PROFILE.BIGTABLE_EMPLOYEE
		) WITH NO DATA;
	"""
	)

	# add primary key
	# handler.run_query(
	# """
		# ALTER TABLE PROFILE.MEMBERSHIP ADD PRIMARY KEY (traveler_profile);
	# """
	# )
	

	# insert
	handler.run_query(
	"""
		INSERT INTO PROFILE.MEMBERSHIP
			SELECT id, traveler_profile, agency_code, loyalty_type, provider_code, loyalty_no
			FROM PROFILE.BIGTABLE_EMPLOYEE;
	"""
	)


	print "small tables created."
