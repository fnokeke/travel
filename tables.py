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
	######@param: new_table, cols, old_table, primary-key
	handler.create_table(
			"employee", 
			"firstname, lastname, email", 
			"BIGTABLE_EMPLOYEE",
			"email") 

	######
	#### membership table 
	######
	handler.create_table(
			"membership", 
			"id, traveler_profile, agency_code, loyalty_type, provider_code, loyalty_no", 
			"BIGTABLE_EMPLOYEE") 

	print "Creating small tables done."
