#
# tables.py
#

def create_table(handler, new_table, cols, old_table, pk=''):
	
	new_table = new_table.upper()
	old_table = old_table.upper()

	try: 

		# drop table if exists
		# query = "select tabname from syscat.tables where tabschema='PROFILE' and tabname='%s'" % 
						(new_table)
		# res = handler.run_query(query)
		# if res:
		# 		query = """DROP TABLE PROFILE.%s""" % new_table
		# 		handler.run_query(query)

		# create
		query = """
			CREATE TABLE PROFILE.%s AS (
				SELECT %s 
				FROM PROFILE.%s
			) WITH NO DATA;""" % (new_table, cols, old_table)
		handler.run_query(query)

		# add primary key
		if pk != '':
			query = """ALTER TABLE PROFILE.%s ADD PRIMARY KEY (%s)""" % (new_table, pk)
			handler.run_query(query)

		# insert data
		query = """
			INSERT INTO PROFILE.%s
				SELECT DISTINCT %s
				FROM PROFILE.%s""" % (new_table, cols, old_table)
		handler.run_query(query)
		return "%s successfully created" % (new_table)
	except:
		return "Error: %s" % handler.get_query_error()
	

if __name__ == '__main__':

	from connect import Connect 

	# automatically sets default schema
	handler = Connect() 

	# tester
	print create_table(handler, "testemploy", "firstname, lastname, email", "BIGTABLE_EMPLOYEE", "email")
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
