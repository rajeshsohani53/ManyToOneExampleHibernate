package com.rajesh.main;

import com.rajesh.dao.CarDao;
import com.rajesh.entity.Brand;
import com.rajesh.entity.Car;

public class Main {
    public static void main(String[] args) {
    	Car car = new Car();
        Brand brand = new Brand();

        brand.setName("BMW");        // set brand fields first (just for readability)

        car.setName("my bmw");
        car.setColor("Black");
        car.setFuleType("Petrol");
        car.setPrice(55d);
        car.setBrand(brand);         // 👈 THIS is the missing wire

        CarDao.insert(car, brand);	
//    	Car c=CarDao.getCarById(1);
//    	System.out.println(c.getName());
//    	System.out.println(c.getColor());
//    	System.out.println(c.getPrice());
	}
}
