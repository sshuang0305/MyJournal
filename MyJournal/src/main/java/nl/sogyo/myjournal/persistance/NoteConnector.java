/**
 * NoteConnector.java
 *
 * @author Shan Shan Huang
 * @since 08-07-19
 */

package nl.sogyo.myjournal.persistance;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import nl.sogyo.myjournal.domain.Day;
import nl.sogyo.myjournal.domain.Note;


public class NoteConnector {

	public static void addNote(int dayID, String newNote) {
		
	    Configuration config = new Configuration().configure().addAnnotatedClass(Day.class);
	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    SessionFactory sf = config.buildSessionFactory(reg);
	    Session session = sf.openSession();
	    Transaction tx = session.beginTransaction();
	    Criteria criteria = session.createCriteria(Day.class);
	    Day day = (Day) criteria.add(Restrictions.eq("dayID", dayID)).uniqueResult();
		tx.commit();
		
		config = new Configuration().configure().addAnnotatedClass(Note.class);
	    reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    sf = config.buildSessionFactory(reg);
	    session = sf.openSession();
	    tx = session.beginTransaction();
    	session.save(new Note(newNote, day));
	    tx.commit();
	}
	
	public static void deleteNote(int noteID) {
		Configuration config = new Configuration().configure().addAnnotatedClass(Note.class);
	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    SessionFactory sf = config.buildSessionFactory(reg);
	    Session session = sf.openSession();
	    Transaction tx = session.beginTransaction();
	    Criteria criteria = session.createCriteria(Note.class);
	    Note note = (Note) criteria.add(Restrictions.eq("noteID", noteID)).uniqueResult();
	    session.delete(note);
		tx.commit();
	}
	
}
