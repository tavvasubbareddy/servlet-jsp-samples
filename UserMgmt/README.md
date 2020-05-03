# servlet-jsp-samples
We have developed below simple basic features in our User Management web application.
  - Create a User
  - Update a User
  - Delete a User
  - Retrieve a User
  - List of all Users
	
Tools and technologies used
  - JSP2.2 +
  - IDE - Eclipse
  - JDK - 1.8 or later
  - Apache Tomcat - 8.5
  - JSTL - 1.2.1
  - Servlet API - 2.5
  - MySQL - mysql-connector-java-8.0.13.jar
	
# Pre requisites 
1. Set up db by executing UserMgmt\sql\create-user-table.sql file
2. Open web.xml, provide dbUser, dbPassword and dbURL according to your db configuration
3. Build application and deploy it to your tomcat server. URL: http://localhost/UserMgmt/