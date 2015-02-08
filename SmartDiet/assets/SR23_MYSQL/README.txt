written 11/24/10
This is an SQL dump of the USDA nutrient database, based on files available here:
http://www.ars.usda.gov/Services/docs.htm?docid=8964
It has been tested with MySQL server 5.1.41-3ubuntu12.6 (Ubuntu). This is a first release. (I can't call it alpha 'cause its not software, but you get the idea) 
The original data is in the tables for the most part, but there are NO INDEXES, KEYS OR CONSTRAINTS placed on the tables yet, so you you will need to do that yourself if you intend to use this in a real world app. All the information about these is in the sr23_doc.pdf file included, and also available from the link above...Or you can wait till my next release in the next few weeks.

*** INSTALLATION ***
extract the files, then type:
mysql -u root -p YourDatabaseName < sr23_dump_1.sql
from the directory sr23_dump_1.sql is in then enter your root password for mysql, wait a while as its quite large.

***WHASSUP***
I intend to expand this over the next few weeks, finally releasing it with its proper indexes keys and contraints, and a selection of useful queries to be used in people's nurition applications, as well as the scripts I used to produce the dump, for modifications you might need to make, and for quick adaptation to future USDA releases. IF you have problems, my best advice is to wait till I am done, but if you really feel you need to reach me, I can be reached at gmail, and my username there is Luke.W.Bradley 

Happy Hacking!


