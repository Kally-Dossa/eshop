# E-shop (Spring Boot Application)

A robust e-commerce system built with Spring Boot, designed to facilitate transactions between stores and customers. This project implements a marketplace where stores manage inventory and citizens perform shopping activities.

## Overview
This project implements a web-based marketplace based on the following requirements:
* **Stores:** Can list and manage products for sale.
* **Citizens:** Can search for products, manage their shopping cart, and complete purchases.

## User Roles
* **Store (Μαγαζί):** Business entity with a product catalog.
* **Citizen (Πολίτης):** Individual user/customer.

## System Entities & Attributes
The system is designed around the following core entities:
* **Citizen (Πολίτης):** Tax ID (ΑΦΜ), First Name, Last Name, Email, Password, Shopping Cart.
* **Product (Προϊόν):** Type, Brand, Description, Price, Stock Quantity, Associated Store.
* **Store (Μαγαζί):** Tax ID (ΑΦΜ), Brand Name, Owner Name, Password, Product List.
* **Cart (Καλάθι):** Citizen reference, List of Products, Total Price.

## Key Functionalities
* **Authentication:** Secure Login and Registration for both Citizens and Stores.
* **Inventory Management:** 
    * Add new products (for Stores).
    * Update stock levels (quantity) for existing products.
* **Shopping Experience:**
    * Multi-criteria product search.
    * Add products to the cart (with availability/stock validation).
* **Checkout:** Complete purchase of items currently in the shopping cart.

## Tech Stack
* **Java:** 17
* **Framework:** Spring Boot 4.0.1
* **Build Tool:** Maven
* **Version Control:** Git & GitHub

## Getting Started

### Prerequisites
* JDK 17
* Maven (optional, project includes Maven Wrapper)

### Installation & Execution
1. **Clone the repository:**
   ```bash
   git clone [https://github.com/Kally-Dossa/eshop.git](https://github.com/Kally-Dossa/eshop.git

2. **Navigate to the project directory:**
     ```bash
     cd eshop/eshop
     
3. **Build and Run the application:**
    ```bash
    ./mvnw spring-boot:run

The application will be accessible at http://localhost:8080

## Project Structure
* `src/main/java/gr/university/eshop`: Core logic and application entry point.

* `src/main/resources`: Configuration files and application properties.

* `pom.xml`: Project dependencies and Maven configuration.

     


   
