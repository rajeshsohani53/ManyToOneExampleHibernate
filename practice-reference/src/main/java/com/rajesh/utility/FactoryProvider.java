package com.rajesh.utility;



import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryProvider {
  private static SessionFactory factory;

  // Build the SessionFactory once and reuse it (it's expensive to create)
  public static synchronized SessionFactory getFactory()
  {
	  if(factory==null)
	  {
		  Configuration c=new Configuration().configure();
		  factory=c.buildSessionFactory();
	  }
	  return factory;
  }
}
