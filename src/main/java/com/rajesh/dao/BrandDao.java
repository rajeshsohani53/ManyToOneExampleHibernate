package com.rajesh.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.rajesh.entity.Brand;
import com.rajesh.utility.FactoryProvider;

public class BrandDao {
  public static Brand save(Brand brand )
  {
	  try(Session s=FactoryProvider.getFactory().openSession())
	  {
		  Transaction tx=s.beginTransaction();
		  s.persist(brand);
		  tx.commit();
		  return  brand;
	  }
	  //return null;
	  
	  //here i build the save brand method
  }
  public static List<Brand> getAllBrands()
  {
	  try(Session s=FactoryProvider.getFactory().openSession())
	  {
		 Query<Brand> query=s.createQuery("from Brand order by name",Brand.class);
		 List<Brand> brand_list=query.list();
		 return brand_list;
	  }
	//return null;  
	  //here i build and complete the getList of All brand method okay 
  }
}
