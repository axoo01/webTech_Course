# E-Commerce CRUD API Assignment

## Endpoints
- GET /api/products/getAllProducts → all products
- GET /api/products/getProduct/{id} → single product
- POST /api/products/addProduct → create new
- PATCH /api/products/updateProduct/{id} → update product
- DELETE /api/products/{id} → delete

### Database
Connected to PostgreSQL (JPA + Hibernate)

### Screenshots (Postman, pg Admin tests)

**GET /api/products - Fetching all products from DB**
![GET all products](Screenshots/get-all-products.png)

**Creating a new product (201 Created)**
![POST new product](Screenshots/create-product.png)

**PostgreSQL Table after POST - New product saved**
![POST new product](Screenshots/pg-admin.png)

**Updating product details (200 OK)**
![PUT product update](Screenshots/updating-product.png)

 **Deleting a product (204 No Content)**
![DELETE product delete](Screenshots/deleting-product.png)
