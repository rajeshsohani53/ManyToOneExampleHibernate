package com.rajesh.utility;



import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryProvider {
  public static SessionFactory factory;
  
  public static SessionFactory getFactory()
  {
	  if(factory==null)
	  {
		  Configuration c=new Configuration().configure();
		  return c.buildSessionFactory();
	  }
	  return factory;
  }
}
