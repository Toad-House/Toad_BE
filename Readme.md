## MySQL Database Configuration
MySQL is required for database management.

🔥 Prerequisites: Make sure MySQL is installed.

1. Check that the following section in the `applications.properties` file matches the environment in which you're running the project. The database name is toadhouse, and MySQL username is set to root.

```jsx
spring.datasource.url=jdbc:mysql://localhost:3306/toadhouse?serverTimezone=UTC&characterEncoding=UTF-8
spring.datasource.username={your_username}
```

  ⚠️ If the database is not automatically created, connect to MySQL and execute `CREATE DATABASE toadhouse;` to create the database manually before running the application.

2. Add your MySQL password in `applications-db.properties` file, enter the MySQL password as follows.

```jsx
spring.datasource.password={your_password}
```


## s3 storage
Google Cloud Storage is required for image storage.

1. Please create a Storage Bucket, and create a service account key.
2. Please add the json file containing information from the service account key to 'src/main/java/toad.toad/resource' in the project folder.
3. Please add 'application-db.properties' to 'src/main/java/toad.toad/resource' in the project folder
4. Please add the following to 'application-db.properties'.

```jsx
spring.cloud.gcp.storage.credentials.location=classpath:{json 파일 이름]}
spring.cloud.gcp.storage.project-id={project_id}
spring.cloud.gcp.storage.bucket={bucket_name}
```
