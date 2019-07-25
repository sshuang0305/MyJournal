package nl.sogyo.myjournal.persistance;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import nl.sogyo.myjournal.domain.Scrumboard;
import nl.sogyo.myjournal.domain.UserAndBoardLinker;

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
	
	public static void update(String[] backlog, String[] todo, String[] inprogress, String[] done, int scrumboardID) {
		
	    Configuration config = new Configuration().configure().addAnnotatedClass(Scrumboard.class);
	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    SessionFactory sf = config.buildSessionFactory(reg);
	    Session session = sf.openSession();

	    Transaction tx = session.beginTransaction();
	    Criteria cr = session.createCriteria(Scrumboard.class);
	    cr.add(Restrictions.eq("scrumboardID", scrumboardID));
	    Scrumboard scrumboard = (Scrumboard) cr.uniqueResult();
	    
		scrumboard.setBacklog(backlog);
		scrumboard.setTodo(todo);
		scrumboard.setInProgress(inprogress);
		scrumboard.setDone(done);

	    session.update(scrumboard);
	    tx.commit();
	}
}

