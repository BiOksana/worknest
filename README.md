# WorkNest Application
### This is a web-based application for managing projects and vacancies.
It is developed in Java using the Spring Boot framework and follows a layered, modular architecture with RESTful APIs and database integration.
Aspect-Oriented Programming (AOP) is used for logging.

Deployed Application
The deployed version of the application is available at:
🔗 https://worknest-production-ffb3.up.railway.app/swagger-ui/index.html

### Technologies used:
- Java 17+
- Spring Boot
- Spring Data JPA (Hibernate)
- Aspect-Oriented Programming (AOP)
- Tomcat
- Log4j2
- MySQL, Liquibase
- Maven
- Lombok
- MapStruct
- JUnit 5, Mockito
- Swagger
- Docker

### REST API Endpoints
- Project Controller — REST API for managing projects and vacancies in the app
GET	    /projects	                Returns all app projects
POST	/projects	                Creates a new project
PUT	    /projects/{id}	            Updates the project by the provided project ID
DELETE	/projects/{id}	            Deletes the project by the provided project ID
GET	    /projects/{id}/vacancies	Returns all vacancies for the given project ID
POST	/projects/{id}/vacancies	Creates a new vacancy for the given project ID

- Vacancy Controller — REST API for managing (updating and deleting) vacancies
PUT	    /vacancies/{id}	            Updates the vacancy by the provided vacancy ID
DELETE	/vacancies/{id}	            Deletes the vacancy by the provided vacancy ID

### Environment Variables
Please ensure the following environment variables are set when running the application locally or with Docker:

DB_URL=
DB_USERNAME=
DB_PASSWORD=

### Spring Profiles
This application uses Spring profiles to separate configuration for different environments:

Profile
- dev:	Used for local development and Docker builds. Uses MySQL DB.	-Dspring.profiles.active=dev
- prod	Used for production deployment.                             	-Dspring.profiles.active=prod

### How to run the app:
Running with Docker Compose
Make sure you have Docker installed.
- Build and run the containers:        docker-compose up --build
- Or (if already built):               docker-compose up
Access the application: http://localhost:8080/swagger-ui/index.html

The dev profile will be used automatically via SPRING_PROFILES_ACTIVE=dev set in docker-compose.yml.

### Mock Data for Testing
Mock data is automatically loaded from the file:  https://github.com/BiOksana/worknest/blob/master/src/main/resources/db/migrations/002_data.sql
This data is inserted into the database on application startup.

### Example Requests:
- Create a Project
POST /projects
{
"name": "Developing onboarding materials for new team members",
"field": "HR",
"experience": "MORE_TWO_YEARS",
"deadline": "2026-11-22",
"description": "We are looking for an HR specialist to create engaging and clear onboarding materials to help new team members integrate quickly into the company culture and processes."
}
- Add Vacancy to Project
POST /projects/1/vacancies
{
"name": "Front-End Developer",
"field": "Engineering",
"experience": "MORE_ONE_YEAR",
"country": "Germany",
"description": "We are hiring a junior frontend developer with basic React skills."
}
