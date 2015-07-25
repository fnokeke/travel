# travel
Travel project using DB2, Python, Java, RESTful API (JSON)

# Overview
 - client/ios talks to websphere and websphere connects to DB2
 - websphere connects to DB2 using Java 

# Tools
 - websphere liberty
 - eclipse luna
 - Data Studio Client
 - Xcode beta

# Usage
 - Load eclipse project and import PhoneToWeb if not in project view
 - If you don't already have a "dynamic web project", create one
 - Make sure you have a websphere liberty profile running
 - Right click on project, select `Run As` and click Server and your project
 deploys on websphere liberty profile you made.
 
 
# Database
 - Profile and Advanced Profile Scripts used to dump data into DB2 database
 - server side data dump using Python scripts

# web server
enable http, https, port 80, port 443

`sudo firewall-cmd --permanent --add-service=http`

`sudo firewall-cmd --permanent --add-service=https`

`sudo firewall-cmd --permanent --add-port=80/tcp`

`sudo firewall-cmd --permanent --add-port=443/tcp`

`sudo firewall-cmd --permanent --list-all #see enabled ports`

NB: On virtualbox, network setting should be "host-only" and also "NAT" (two
		adapters). If you plan to make the webserver accessible to multiple clients
-- for instance, mobile phones -- then you should use "bridged" and "NAT". 
Not using NAT means no external network on the guest and not using "host-only"
or "bridged" means no communication between host and guest.

# iOS
Client app running on iOS emulator and making RESTful calls.

# Errors
- In DB2, a null value is not the same as zero or all blanks. 

# Travel API
### Token
- You need to get an Access Token

### URI Resources
Every URI is preceded by v1/
```
profiles/
profiles/profileId/{profileId}

profiles/profileId/{profileId}/names   					
profiles/profileId/{profileId}/names/firstname  
profiles/profileId/{profileId}/names/middlename 
profiles/profileId/{profileId}/names/lastname   

profiles/profileId/{profileId}/dateofbirth

profiles/profileId/{profileId}/gender

profiles/profileId/{profileId}/emails/primary 	
profiles/profileId/{profileId}/emails/other 		

profiles/profileId/{profileId}/addresses/work 	
profiles/profileId/{profileId}/addresses/home 	

profiles/profileId/{profileId}/phones/work 			
profiles/profileId/{profileId}/phones/home 			

profiles/profileId/{profileId}/loyalties
profiles/profileId/{profileId}/loyalties/loyaltyType/{loyaltyType} 

loyalties/loyaltyId/{loyaltyId}/details (get details of a traveler based on their travelerId
```
