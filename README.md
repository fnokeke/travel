# travel
Travel project using DB2 and Python

# DB2
Profile and Advanced Profile Scripts used to dump data into DB

# web server
enable http, https, port 80, port 443

`sudo firewall-cmd --permanent --add-service=http`
`sudo firewall-cmd --permanent --add-service=https`
`sudo firewall-cmd --permanent --add-port=80/tcp`
`sudo firewall-cmd --permanent --add-port=443/tcp`

`sudo firewall-cmd --permanent --list-all #see enabled ports`

# NULL VALUES
In DB2, a null value is not the same as zero or all blanks. 

