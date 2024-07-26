**API Test Cases**
This document provides an overview of the test cases for the Products, Users, and Carts APIs of the fakestore:https://fakestoreapi.com/docs.  It covers the various operations such as retrieving, adding, updating, and deleting resources, and is implemented using TestNG.

Prerequisites
Java 8 or higher
TestNG
RestClient
Appropriate dependencies for running and reporting tests

Test Cases:
**Products API**
**Positive Test Cases are covered in "ProductsPositiveScenariosTests" file:**
1. Get All Products
   Description: Verify that all products are retrieved successfully.
2. Get Product by ID
   Description: Verify that a product is retrieved successfully by its ID.
3. Limit Results
   Description: Verify that the products list is limited to a specified number of items.
4. Sort Results
   Description: Verify that the products are sorted in descending order.
5. Add new Product
   Description: Verify that a new product can be added successfully
6. Update Existing Product:
   Description: Verify that an existing product details can be updated successfully.
7. Delete Product
   Description: Verify that an existing product can be deleted successfully.
8. Get All product categories
   Description: Verify that all categories are retrieved successfully.
9. Filter Products by Category
   Description: Verify that Products under a certain category are retrieved successfully.

**Negative Test Cases are covered in ProductsNegativeScenariosTests:**
1. Get Product by Invalid ID
   Description: Verify that an invalid product ID returns an error(404).

**Users API:**
**Positive Test Cases are covered in "UsersPositiveScenarioTests" file:**
1. VerifyAllUsers
   Description: Verifies that the number of users returned by the getAllUsers API matches the expected number of users.
2. verifyGetUserById
   Description: Verifies that the details of a user retrieved by ID match the expected details
3. verifyAddNonExistingValidUser
   Description: Verifies that a new user can be added successfully and that the user details match the provided values.
4. verifyUpdateUser
   Description: Verifies that a user can be updated successfully and that the updated details match the expected values.
5. verifyDeleteUser
   Description: Verifies that a user can be deleted successfully.

**Carts API**
1. verifyGetAllCarts
   Description: Verifies that the number of carts returned by the getAllCarts API matches the expected number of carts.
2. verifyGetCartById
   Description: Verifies that the details of a cart retrieved by ID match the expected details.
3. verifyAddNonExistingCart
   Description: Verifies that a new cart can be added successfully and that the cart details match the provided values.
4. verifyUpdateCart
   Description: Verifies that a cart can be updated successfully and that the updated details match the expected values.
5. verifyDeleteCart
   Description: Verifies that a cart can be deleted by ID.
6. verifyGetUserCart
   Description: Verifies that the carts associated with a specific user can be retrieved.
7. verifyGetCartInDateRange
   Description: Verifies that carts created within a specific date range are retrieved successfully.
8. verifyProductLimits
   Description: Verifies that the correct number of carts is returned as specified by the limit.
9. verifyCartsAreSortedDesc
   Description: Verifies that carts are returned in descending order by ID.


**Negative Test Cases that are not covered**
The following test cases are not implemented as APIs always work properly (success 200) when executing them.
1. Limit Results - Negative Limit
2. Sort Results - Invalid Sort Parameter
3. Add New Product/User/Cart - Missing Fields
4. Update a Product/User/Cart - Invalid ID
5. Update a Product/User/Cart - Missing Fields/Empty body
6. Delete a Product/User/Cart - Invalid ID

**Running the Tests**
1. Setup: Ensure all dependencies are properly configured in your build tool (e.g., Maven or Gradle).
2. Execute Tests: Run the tests using TestNG : mvn test -DsuiteXmlFile=testng.xml
3. Reports: Test results and reports will be generated by TestNG and can be found in the root directory "results.html".