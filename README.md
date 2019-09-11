# MyJournal
MyJournal is an application that functions as a journal for your project.
You can use it to log your progress and keep track of your project with a scrumboard.
![JournalDay](/MyJournalAPI/src/main/resources/JournalDay.png)
![Scrumboard](/MyJournalAPI/src/main/resources/Scrumboard.png)

## Getting Started
The MyJournalAPI has a dependency on the MyJournalDomain:

- Use Maven to install the MyJournalDomain artifact in the Maven repository:
		(from within the MyJournal domain project)
		==> mvn clean install

- To run the MyJournalAPI project (the HTTP port used is defined within the projects POM):
		(from within the MyJournalAPI project)
		==> mvn clean package
		==> mvn jetty:run

- You can use Postman to test the MyJournalAPI (independent of your Client WebApp), or open a browser and go to:
		==> http://localhost/myjournal

## author
Shan Shan Huang
