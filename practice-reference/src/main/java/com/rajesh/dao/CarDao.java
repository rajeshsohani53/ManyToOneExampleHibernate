package com.rajesh.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rajesh.entity.Brand;
import com.rajesh.entity.Car;
import com.rajesh.utility.FactoryProvider;

public class CarDao {

	// Saves a new brand together with its car (used by Main)
	public static void insert(Car car,Brand brand)
	{
		try (Session s=FactoryProvider.getFactory().openSession()) {
			Transaction tx=s.beginTransaction();
			s.persist(brand);
			s.persist(car);
			tx.commit();
		}
	}

	// Saves a car and links it to an existing brand
	public static Car save(Car car, int brandId)
	{
		try (Session s=FactoryProvider.getFactory().openSession()) {
			Transaction tx=s.beginTransaction();
			Brand brand=s.get(Brand.class, brandId);
			if (brand==null) {
				tx.rollback();
				return null;
			}
			car.setBrand(brand);
			s.persist(car);
			tx.commit();
			return car;
		}
	}

	public static Car getCarById(int id)
	{
		try (Session s=FactoryProvider.getFactory().openSession()) {
			return s.get(Car.class, id);
		}
	}

	public static List<Car> getAll()
	{
		try (Session s=FactoryProvider.getFactory().openSession()) {
			// join fetch loads each car's brand in the same query
			return s.createQuery("select c from Car c left join fetch c.brand order by c.id", Car.class).list();
		}
	}

	public static boolean delete(int id)
	{
		try (Session s=FactoryProvider.getFactory().openSession()) {
			Transaction tx=s.beginTransaction();
			Car car=s.get(Car.class, id);
			if (car==null) {
				tx.rollback();
				return false;
			}
			s.remove(car);
			tx.commit();
			return true;
		}
	}


}
