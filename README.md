# Introduction: 
This project consists of RESTful services that implement a checkout counter for an online retail store that scans products and generates itemized bill.

It provides services for managing products and orders.  Products can be confifured with rate and category (A,B or C). Sales tax is applied based on the category of the product:
* Category A - 10%
* Category B - 20%
* Category C- 0%

Bill details the products, quantity, total cost,sales tax and the total value of the bill.Data for 10 Products and 1 bill are added during startup to browse.

# REST endpoints
Client can add/update/modify products and orders using the REST endpoints.Below is overview of REST end points:

## Products
*  GET /products - fetches list of all product data
*  GET /products/{id} - fetch a specific product
*  POST /products - Creates a new product based on request JSON
*  PUT /products/{id} - Updates product data based on request JSON
*  DELETE /products/{id} - Delete an existing product


## Bills
*  GET /bills - fetches all bill data
*  GET /bills/{id} - fetches bill of a particular id
*  POST /bills - creates a bill Id. Client has to use this bill Id while adding and removing products
*  PUT /bills/{id} - Updates bill data. Client can add or remove products to bill sending a JSON request.
*  DELETE /bills/{id} - Delete bill from the system.

 These REST end points are secured using basic authentication mechanism. Code uses in-memory repository with 'bob' as single user.

# How to run the application locally?

Pre-requisites to run application are Java, Maven and Git. Application can be run locally in following ways:
1) Download the jar from Git and run with command:java -jar RetaileStoreApplication-0.0.1-SNAPSHOT.jar

2) Build and run locally:
* Create a new directory called "retailstoreapp" 
* Clone repository using following command:git clone https://github.com/satishpeyyety/retailstore.git .
* Run the application through maven: mvn spring-boot:run
* Access and invoke APIs using url : http://localhost:8080/swagger-ui.html
