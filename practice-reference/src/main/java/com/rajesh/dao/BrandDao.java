package com.rajesh.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rajesh.entity.Brand;
import com.rajesh.utility.FactoryProvider;

public class BrandDao {

	public static Brand save(Brand brand)
	{
		try (Session s=FactoryProvider.getFactory().openSession()) {
			Transaction tx=s.beginTransaction();
			s.persist(brand);
			tx.commit();
			return brand;
		}
	}

	public static List<Brand> getAll()
	{
		try (Session s=FactoryProvider.getFactory().openSession()) {
			return s.createQuery("from Brand order by name", Brand.class).list();
		}
	}
}
