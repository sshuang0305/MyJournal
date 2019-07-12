package nl.sogyo.myjournal.persistance;

import org.hibernate.*;

import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import nl.sogyo.myjournal.domain.User;


public class UserConnector {
	
	public static User connect(String username) {
		
	    Configuration config = new Configuration().configure().addAnnotatedClass(User.class);
	    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
	    SessionFactory sf = config.buildSessionFactory(reg);
	    Session session = sf.openSession();
	
	    Transaction tx = session.beginTransaction();
	
	    Criteria criteria = session.createCriteria(User.class);
	
	    User loginUser = (User) criteria
	              .add(Restrictions.eq("username", username))
	              .uniqueResult();
	
	    tx.commit();
	    return loginUser;
	}
}
