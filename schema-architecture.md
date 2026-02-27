# Section 1: Architecture summary

This Spring Boot application is built on a hybrid architecture that leverages both traditional 
MVC and modern RESTful design patterns to serve different client needs. 
For the front-end interfaces, Thymeleaf templates are utilized to render the interactive Admin and Doctor dashboards directly from the server. 
Meanwhile, the rest of the application's features are exposed as REST APIs, allowing for flexible integration with mobile apps or distinct front-end frameworks.

To optimize data storage, the application employs a polyglot persistence strategy using two distinct databases. 
Structured, relational data—such as patient records, doctor profiles, appointment schedules, 
and admin details—is managed in a MySQL database using Spring Data JPA entities. 
Conversely, unstructured or document-heavy data, specifically medical prescriptions, is stored in a MongoDB database using document models. 
To maintain a clean architecture and strict separation of concerns, all incoming requests—whether from the MVC controllers or REST endpoints—are routed through a centralized service layer. 
This service layer encapsulates the core business logic and delegates data access operations to the appropriate repositories.





Section 2: Numbered flow of data and control

> A user (Admin, Doctor, or Patient) interacts with the application, either by navigating to a Thymeleaf-rendered dashboard page or by an external client making an HTTP request to a REST API endpoint.

> The incoming request is intercepted by the application and routed to the appropriate controller (an MVC controller for returning web views, or a REST controller for returning raw data).

> The controller parses the incoming request parameters or payloads and delegates the operation to the centralized service layer.

> The service layer executes the required business logic and determines which type of data model is needed—JPA entities for relational data or Document models for prescriptions.

> The service layer calls the appropriate repository interface (Spring Data JPA Repository or MongoRepository) to perform the necessary CRUD (Create, Read, Update, Delete) operations.

> The repository executes the query against the correct database, fetching or persisting data in either MySQL (for users and appointments) or MongoDB (for prescriptions).

> The data flows back up through the service layer to the controller, which either binds the data to a Thymeleaf model to render the HTML view, or serializes the data into JSON format to return as an HTTP response to the API client.



