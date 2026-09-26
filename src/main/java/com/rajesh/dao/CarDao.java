package com.rajesh.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.rajesh.entity.Brand;
import com.rajesh.entity.Car;
import com.rajesh.utility.FactoryProvider;

public class CarDao {
  
	public static void insert(Car car,Brand brand)
	{
		
		try(Session s =FactoryProvider.getFactory().openSession())
		{
			Transaction tx=s.beginTransaction();
			s.persist(brand);
			s.persist(car);
			
			tx.commit();
			s.close();
		}
		
		
		
	}
	
	public static Car save(Car car,Integer brandId)
	{
		try(Session s =FactoryProvider.getFactory().openSession())
		{
			Transaction tx=s.beginTransaction();
			// to save car by the brand that are alredy exist so i need to do 
			// first fetch the brand and get the object or brand 
			//and save that brand in car okay 
			Brand brand=s.get(Brand.class, brandId);
			if(brand!=null)
			{
				car.setBrand(brand);
				s.persist(car);
				tx.commit();
				s.close();
				return car;
			}else
			{
				return null;
			}
			
			
		}
		// here we finished the method that save the car with existing brand okay 
	}
	
	public static Car getCarById(int id)
	{
		Session s=FactoryProvider.getFactory().openSession();
		return s.get(Car.class, id);
	}
	
	
}

/*
 ✅ **Step 1 works.** The factory is now saved and reused, and you made it `private` too. Nice.

The `return factory;` on line 17 is extra, because line 19 already does the same thing. It's harmless, so you can remove it or keep it.

---

## Step 2: Close sessions in `CarDao`

Open `src/main/java/com/rajesh/dao/CarDao.java`.

**The problem:** Both methods call `openSession()`, but nothing ever closes the session. Each open session holds a database connection. In a web server that handles many requests, you'd eventually run out of connections and it would stop working.

**Your task:** Make every session close automatically, in both `insert` and `getCarById`.

**Hint:** Use **try-with-resources**. You open the session inside the brackets of `try ( ... )`, and Java closes it for you when the `{ }` block ends, even if an error happens:
```java
try (Session s = FactoryProvider.getFactory().openSession()) {
    // use s here
}
```
In `getCarById`, the `return` goes inside the `try` block.

**Test:** Run `Main.java`. It should still insert a car.

Tell me when you're done.
 * */
