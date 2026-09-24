# ManyToOneExampleHibernate

A small Hibernate 6 example that demonstrates a **many-to-one** relationship: many `Car` rows point to a single `Brand` row.

## Tech stack

- Java (Maven project)
- Hibernate ORM `6.3.1.Final` (Jakarta Persistence annotations)
- MySQL with Connector/J `8.0.33`

## Project structure

```
src/main
├── java/com/rajesh
│   ├── entity
│   │   ├── Brand.java          # brand_table entity (the "one" side)
│   │   └── Car.java            # car_table entity (the "many" side)
│   ├── dao
│   │   ├── CarDao.java         # insert(car, brand) and getCarById(id)
│   │   └── StreamExample.java  # unrelated Java streams demo
│   ├── main
│   │   └── Main.java           # runnable demo: saves a Car with its Brand
│   └── utility
│       └── FactoryProvider.java  # builds the Hibernate SessionFactory
└── resources
    └── hibernate.cfg.xml       # DB connection + entity mappings
```

## The mapping

`Car` holds a foreign key to `Brand`:

```java
@ManyToOne
@JoinColumn(name = "brand_id")
private Brand brand;
```

| Table         | Column         | Notes                          |
|---------------|----------------|--------------------------------|
| `brand_table` | `brand_id`     | Primary key, auto-increment    |
|               | `brand_name`   |                                |
| `car_table`   | `car_id`       | Primary key, auto-increment    |
|               | `car_name`     |                                |
|               | `car_color`    |                                |
|               | `car_price`    |                                |
|               | `car_fuleType` |                                |
|               | `brand_id`     | Foreign key → `brand_table`    |

Tables are created and updated automatically (`hbm2ddl.auto = update`).

## Getting started

### 1. Create the database

```sql
CREATE DATABASE ManyToOneMapping01;
```

### 2. Configure the connection

Edit `src/main/resources/hibernate.cfg.xml` and set the URL, username and password to match your MySQL setup:

```xml
<property name="connection.url">jdbc:mysql://localhost:3306/ManyToOneMapping01</property>
<property name="connection.username">...</property>
<property name="connection.password">...</property>
```

The user needs permission to create and alter tables in that database.

### 3. Build

```bash
mvn clean compile
```

### 4. Run

Run `com.rajesh.main.Main` from your IDE (for example Eclipse: *Run As → Java Application*), or with Maven:

```bash
mvn exec:java -Dexec.mainClass=com.rajesh.main.Main
```

`Main` creates a `Brand` ("BMW") and a `Car` linked to it, then saves both through `CarDao.insert`. Hibernate prints the generated SQL to the console (`show_sql = true`).

To read a car back, uncomment the `CarDao.getCarById(1)` lines in `Main.java`.

## License

This project is for learning purposes.
