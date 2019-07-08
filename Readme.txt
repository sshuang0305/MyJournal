The MyJournalAPI had a dependency on the MyJournalDomain so make sure you do the following:


- Use Maven to install the MyJournalDomain artifact in the Maven repository:

		(from within the MyJournal domain project)
		==> mvn clean install

		if you make a change in the MyJournal project rerun this step to make sure the change ends up in the Maven repository

- To run the MyJournalAPI project (the HTTP port used is defined within the projects POM):

		(from within the MyJournalAPI project)
		==> mvn clean package
		==> mvn jetty:run

- You can use Postman to test the MyJournalAPI (independent of your Client WebApp), or open a browser and go to:

		==> http://localhost/myjournal
