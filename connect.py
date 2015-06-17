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
		ibm_db.exec_immediate(self.conn, "set schema = " + schema)

	def run_query(self, query):
		return ibm_db.exec_immediate(self.conn, query) 

	def get_query_error(self):
		return ibm_db.stmt_errormsg()
