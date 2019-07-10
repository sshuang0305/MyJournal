package nl.sogyo.myjournal.domain;


import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.*;


public class UserConnector {

	public static void main(String args[]) {
		
		User newUser = new User("miauw", "woef");
		
		Configuration config = new Configuration().configure().addAnnotatedClass(User.class);
		ServiceRegistry reg = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		SessionFactory sf = config.buildSessionFactory(reg);
		Session session = sf.openSession();
		
		Transaction tx = session.beginTransaction();
		
		session.save(newUser);
		tx.commit();
	}  
}
