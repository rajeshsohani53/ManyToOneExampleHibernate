package com.rajesh.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rajesh.entity.Brand;
import com.rajesh.entity.Car;
import com.rajesh.utility.FactoryProvider;

public class CarDao {
  
	public static void insert(Car car,Brand brand)
	{
		Session s=FactoryProvider.getFactory().openSession();
		Transaction tx=s.beginTransaction();
		s.persist(brand);
		s.persist(car);
		
		tx.commit();
	}
	
	public static Car getCarById(int id)
	{
		Session s=FactoryProvider.getFactory().openSession();
		return s.get(Car.class, id);
	}
	
	
}
