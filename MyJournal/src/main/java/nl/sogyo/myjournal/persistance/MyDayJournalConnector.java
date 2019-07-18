package nl.sogyo.myjournal.persistance;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import nl.sogyo.myjournal.domain.MyJournalDay;
import nl.sogyo.myjournal.domain.User;

public class MyDayJournalConnector {

	public static MyJournalDay connect(String date, User user) {
		
	    Configuration config = new Configuration().configure().addAnnotatedClass(MyJournalDay.class);
	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    SessionFactory sf = config.buildSessionFactory(reg);
	    Session session = sf.openSession();
	
	    Transaction tx = session.beginTransaction();
	
	    Criteria criteria = session.createCriteria(MyJournalDay.class);

	    MyJournalDay journal = (MyJournalDay) criteria
	    		.add(Restrictions.eq("date", date))
	    		.add(Restrictions.eq("user_id", user))
	    		.uniqueResult();

	    if (journal == null) {
	    	System.out.println("add entry");
	    }

	    tx.commit();
		return journal;
	}
}
