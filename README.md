# 🏨 Hotel Reservation System (Java + JDBC + MySQL)

A simple **console-based backend project** for managing hotel reservations using **Java**, **JDBC**, and **MySQL**. It allows hotel staff to perform operations such as adding, updating, deleting, and retrieving reservation details.

---

## 🚀 Features

- ➕ Add new guest reservations
- 📝 Update guest or room details
- ❌ Delete reservation (used as checkout)
- 🔍 Search reservation by ID
- 📋 View all reservations
- 🔄 No fixed room limit (dynamically handled)

---

## ⚙️ Technologies Used

- **Java** (Core Java, OOP)
- **JDBC** (Java Database Connectivity)
- **MySQL** (RDBMS)

---

## 📚 What I Learned

- Connecting Java with MySQL using JDBC
- Writing and executing **SQL queries** inside Java
- Implementing **parameterized queries** using `PreparedStatement`
- Performing **CRUD operations**
- Backend structuring and exception handling

---

## 🔧 How to Run the Project

1. **Set up the MySQL Database:**
```sql
CREATE DATABASE HOTEL;

USE HOTEL;

CREATE TABLE RESERVATION (
  RESERVATION_ID INT AUTO_INCREMENT,
  GUEST_NAME VARCHAR(100) NOT NULL,
  ROOM_NUMBER INT NOT NULL,
  CONTACT_NUMBER VARCHAR(20),
  CHECKIN_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (RESERVATION_ID)
);

