# Project documentation

## Pre-requisites: 
1) Netbeans IDE 
2) Glassfish 5.1.0

## Steps to launch backend 
1) open Netbeans IDE
2) go to Services tab
3) Right-click "MySQL Server at localhost:3306 [root]"
4) Select Create Database
5) Enter "is3106weddingplanner" in database name field
6) Check Grant Full Access To checkbox and select the "mysql.sys@localhost" option
7) Click File -> Open Project and select "IS3106WeddingPlanner" (Found in IS3106Group13WeddingPlanner/projectBackend folder)
8) If you encounter library resolution issues please refer to the next section (Library reference issue)
9) Right-click project and select "Clean and Build"
10) Right-click project and select "Deploy"

After completing these steps, the backend server will be running.

### Library reference issue (Missing JWT, JWTImpl, JWTJackson)
The additional packages (.jar files) required for this project can be found in the IS3106WeddingPlanner/src folder
1) Right-click project folder and click "Resolve reference"
2) Add new library named "JWT" -> Add reference to "jjwt-api-0.11.5.jar"
3) Add new library named "JWTImpl" -> Add reference to "jjwt-impl-0.11.5.jar"
4) Add new library name "JWTJackson" -> Add reference to "jjwt-jackson-0.11.5.jar"
5) We also use Java EE 8 API library, Persistence JPA 2.1, and JDK 1.8 (Default) 

## Database Info
Database name: `is3106weddingplanner`

JDBC Connection Pool name: `is3106WeddingPlannerJpa`

- Extract from existing connection parameter: `jdbc:mysql://localhost:3306/is3106weddingplanner?zeroDateTimeBehavior=CONVERT_TO_NULL`
- Datasource classname: `com.mysql.cj.jdbc.MysqlDataSource`
- username: `root` password: `password`
