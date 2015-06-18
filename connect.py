# connect.py

import ibm_db

class Connect:

	def __init__(self):
		self.connect_db()
		self.set_schema('PROFILE')

	def connect_db(self):
		dsn = ( 
				'DATABASE=travel;'
				'HOSTNAME=localhost;'
				'PORT=50000;'
				'PROTOCOL=TCPIP;'
				'UID=db2inst1;'
				'PWD=123456;'
				)
		self.conn = ibm_db.pconnect(dsn, "", "")
		print "DB connected."

	def set_schema(self, schema):
		self.schema = schema
		ibm_db.exec_immediate(self.conn, "set schema = " + self.schema)
		print "Schema: %s." % self.schema

	def run_query(self, query):
		stmt = ibm_db.exec_immediate(self.conn, query) 

		# don't fetch result otherwise error raised
		no_need_for_result = ["DROP", "CREATE", "INSERT"] 
		tmp_query = query.upper()

		no_need = False
		for x in no_need_for_result:
			if x in tmp_query:
				no_need = True
				break

		if no_need: 
			return []

		result = []
		row = True
		while row != False:
			row = ibm_db.fetch_assoc(stmt)
		 	if row != False:
				result.append(row)

		return result

	def get_query_error(self):
		error = ''
		if ibm_db.stmt_errormsg():
			error = ibm_db.stmt_errormsg()
		elif ibm_db.conn_errormsg():
			error = ibm_db.stmt_errormsg() 
		else:
			error = "Sorry no error details :("
		return error

	def table_exists(self, table_name):
		query = """
		SELECT tabname from syscat.tables 
		where tabschema='%s' and tabname='%s'""" % (self.schema, table_name)
		result = self.run_query(query)
		return result != []


	def create_table(self, new_table, cols, old_table, pk=''):
		
		new_table = new_table.upper()
		old_table = old_table.upper()

		# drop if exists
		if self.table_exists(new_table):
			query = "DROP TABLE %s" % new_table
			print query
			self.run_query(query)

		# create
		query = """
			CREATE TABLE PROFILE.%s AS (
				SELECT %s 
				FROM PROFILE.%s
			) WITH NO DATA;""" % (new_table, cols, old_table)
		self.run_query(query)

		# add primary key
		if pk != '':
			query = """ALTER TABLE PROFILE.%s ADD PRIMARY KEY (%s)""" % (new_table, pk)
			self.run_query(query)

		# insert data
		query = """
			INSERT INTO PROFILE.%s
				SELECT DISTINCT %s
				FROM PROFILE.%s""" % (new_table, cols, old_table)
		self.run_query(query)
		print "Table '%s' successfully created." % (new_table)
