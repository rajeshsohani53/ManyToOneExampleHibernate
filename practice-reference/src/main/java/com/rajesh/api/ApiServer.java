package com.rajesh.api;

import java.util.Map;

import com.rajesh.dao.BrandDao;
import com.rajesh.dao.CarDao;
import com.rajesh.entity.Brand;
import com.rajesh.entity.Car;
import com.rajesh.utility.FactoryProvider;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * REST API that the React frontend talks to.
 * Start it, then open http://localhost:8080/api/cars in a browser to test.
 */
public class ApiServer {

	// JSON body the frontend sends when creating a car
	public record CarRequest(String name, String color, Double price, String fuleType, Integer brandId) {}

	public static void main(String[] args) {
		FactoryProvider.getFactory(); // connect to MySQL at startup so errors show immediately

		// No CORS setup needed: the React dev server proxies /api/* to this port (see frontend/vite.config.js)
		Javalin app = Javalin.create();

		// ---- Brands ----
		app.get("/api/brands", ctx -> ctx.json(BrandDao.getAll()));

		app.post("/api/brands", ctx -> {
			Brand brand = ctx.bodyAsClass(Brand.class);
			if (isBlank(brand.getName())) {
				badRequest(ctx, "Brand name is required");
				return;
			}
			ctx.status(201).json(BrandDao.save(brand));
		});

		// ---- Cars ----
		app.get("/api/cars", ctx -> ctx.json(CarDao.getAll()));

		app.get("/api/cars/{id}", ctx -> {
			Car car = CarDao.getCarById(ctx.pathParamAsClass("id", Integer.class).get());
			if (car == null) {
				ctx.status(404).json(Map.of("error", "Car not found"));
				return;
			}
			ctx.json(car);
		});

		app.post("/api/cars", ctx -> {
			CarRequest req = ctx.bodyAsClass(CarRequest.class);
			if (isBlank(req.name()) || req.brandId() == null) {
				badRequest(ctx, "Car name and brandId are required");
				return;
			}
			Car car = new Car(req.name(), req.color(), req.price(), req.fuleType(), null);
			Car saved = CarDao.save(car, req.brandId());
			if (saved == null) {
				badRequest(ctx, "Brand " + req.brandId() + " does not exist");
				return;
			}
			ctx.status(201).json(saved);
		});

		app.delete("/api/cars/{id}", ctx -> {
			boolean deleted = CarDao.delete(ctx.pathParamAsClass("id", Integer.class).get());
			ctx.status(deleted ? 204 : 404);
		});

		app.start(8084);
	}

	private static boolean isBlank(String s) {
		return s == null || s.isBlank();
	}

	private static void badRequest(Context ctx, String message) {
		ctx.status(400).json(Map.of("error", message));
	}
}
