#
# tables.py
#

if __name__ == '__main__':

	from connect import Connect 

	# automatically sets default schema
	handler = Connect() 

	# create smaller tables
	handler.run_query("""
	 DROP TABLE PROFILE.EMPLOYEE""")

	handler.run_query("""
			CREATE TABLE PROFILE.EMPLOYEE (
				firstname VARCHAR(30) NOT NULL, 
				lastname VARCHAR(30) NOT NULL, 
				email VARCHAR(50) NOT NULL PRIMARY KEY
			)""")
	
	handler.run_query("""
		INSERT INTO PROFILE.EMPLOYEE
			SELECT DISTINCT firstname, lastname, email
			FROM PROFILE.BIGTABLE_EMPLOYEE
	 """)

	print "small tables created."
