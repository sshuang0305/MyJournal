package nl.sogyo.myjournal.persistance;

import org.hibernate.*;

import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import nl.sogyo.myjournal.domain.Day;
import nl.sogyo.myjournal.domain.User;


public class UserConnector {
	
    Configuration config = new Configuration().configure().addAnnotatedClass(User.class);
    ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
    SessionFactory sf = config.buildSessionFactory(reg);
    Session session = sf.openSession();
    Transaction tx = session.beginTransaction();
    Criteria criteria = session.createCriteria(User.class);

	public User register(String username, String password) {
		User existingUser = (User) criteria.add(Restrictions.eq("username", username)).uniqueResult();
		if (existingUser == null) {
	    	User newUser= new User(username, password);
	    	Day newDay = new Day("dd/mm/yyyy", newUser);
	    	newUser.addDay(newDay);
		    session.save(newUser);
		    tx.commit();
		    return newUser;
		}
		return null;
	}
	
	public User login(String username, String password) {
	    User loginUser = (User) criteria.add(Restrictions.eq("username", username)).uniqueResult();
	    tx.commit();
	    if (User.validateUser(loginUser, password)) {
	    	return loginUser;
	    }
	    return null;
	}
	
	public User findUser(String username) {
	    User user = (User) criteria.add(Restrictions.eq("username", username)).uniqueResult();
	    tx.commit();
	    return user;
	}

}