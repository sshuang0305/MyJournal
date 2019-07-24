package nl.sogyo.myjournal.persistance;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import nl.sogyo.myjournal.domain.Scrumboard;

public class ScrumboardConnector {

	public static Scrumboard save(String projectName, int userID, String[] userStories) {
		
	    Configuration config = new Configuration().configure().addAnnotatedClass(Scrumboard.class);
	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    SessionFactory sf = config.buildSessionFactory(reg);
	    Session session = sf.openSession();

	    Transaction tx = session.beginTransaction();
	    
		Scrumboard newScrumboard= new Scrumboard(projectName, userID, userStories);
	    session.save(newScrumboard);
	    tx.commit();
	    return newScrumboard;
	}
}

