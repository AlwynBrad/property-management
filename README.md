# Property Management System - Backend

Welcome to the backend of the Property Management System (PMS). This backend is developed using Java and Spring Boot framework. The system provides functionalities for managing user registration, login, and property-related operations.

## Table of Contents
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Contributing](#contributing)
- [License](#license)

## Technologies Used

- Java
- Spring Boot
- Spring Security
- JWT Authentication
- Microsoft SQL Server


### Getting Started

**To get started with the project, follow these steps:**

Clone the PMS backend repository to your local machine using the following command:

```
    git clone https://github.com/AlwynBrad/property-management.git
```
   
  
**Configure Database Settings:**

* Open the application.properties file in your project's resources folder.
* Configure the database settings such as URL, username, and password according to your Microsoft SQL Server setup or any oher database you like.
* Save the changes.



**Run the Application:**

+ Open your preferred Java IDE (Integrated Development Environment).
+ Build the project using Maven by running the following command in the terminal:
```
    mvn clean install
```
+ Or Reload the Maven project in your IDE to ensure dependencies are resolved.
+ Run the application by executing the main class, named PropertyManagementApplication.java, or use the IDE's run configuration.




**Access API Endpoints:**

+ Once the application is running, you can access the defined API endpoints to interact with the Property Management System.
+ Use a tool like Postman to conveniently test and interact with the API endpoints. Import the provided Postman collection file for easy setup.


**Explore API Documentation:**

+ Explore the API documentation using Swagger.
+ After the application is up and running, access the Swagger UI by navigating to http://localhost:8080/swagger-ui.html in your web browser.
+ The Swagger documentation provides detailed information about available endpoints and how to use them.




### Contributing
If you would like to contribute to this project, please feel free to do so.


### License
This project is licensed under the MIT License.

**MIT License:**

***Copyright (c) 2023 Alwyn Bradman A***

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
