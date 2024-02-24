## s3 storage
Google Cloud Storage is required for image storage.

1. Please create a Storage Bucket, and create a service account key.
2. Please add the json file containing information from the service account key to 'src/main/java/toad.toad/resource' in the project folder.
3. Please add 'application-db.properties' to 'src/main/java/toad.toad/resource' in the project folder
4. Please add the following to 'application-db.properties'.
```jsx
spring.cloud.gcp.storage.credentials.location=classpath:[json 파일 이름]
spring.cloud.gcp.storage.project-id=[project_id]
spring.cloud.gcp.storage.bucket=[bucket_name]
```
