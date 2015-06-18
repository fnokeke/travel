#
# tables.py
#

if __name__ == '__main__':

	from connect import Connect 

	# automatically sets default schema
	handler = Connect() 

	#### employee table ###
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

	# membership table

	print "small tables created."
