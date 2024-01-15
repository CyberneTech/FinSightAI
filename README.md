# FinSightAI
## About 
This is a Spring Boot-based microservice application designed and developed to generate investment insights for user based on their financial preferences. It offers a comprehensive system for managing user financial profile and generate insights based on their existing profile. The application serves as a robust platform for users to enhance their investment portfolio and acheive their financial goals better.

## Backend System Architecture 
![Backend System Architecture](assets/Backend%20Architecture.png)
**Architecture Overview**
- Users interact with a highly responsive and dynamic [User Interface](https://github.com/CyberneTech/FinSightAI-frontend.git), which serves as the client-side application. This UI constructs and sends HTTP requests to the backend system. 
- Springboot REST API controllers expose various HTTP endpoints, Client accesses  backend services by sending API request to the specific microservice designed to handle that request . 
- A rate limiter is integrated into controller, which throttles the number of incoming requests to prevent service overuse. This ensures fair usage of resources and maintains the quality of service across the platform, protecting the microservices from being overwhelmed by an excessive number of calls.
-  Controllers call the microservices to perform the user authentication, preference management and investment insight generation, the user auth data and preference data is fetched from the FinSightAi Mongodb database. 
- FinSightAI-LLMService requests integrated LLM API (gpt 3.5-turbo), providing investment advice, according to user preference and current market trends.

### Microservices
 
- **FinSightAI** : The user authentication service validates user credentials and manages session tokens, ensuring secure access to the system.

- **FinSight-LLMService** : For generating tailored investment insights, the services interact with the FinSightAI-LLMService, which in turn utilizes the GPT-3.5 Turbo via an integrated LLM API, leveraging its advanced natural language processing capabilities.

FinSight-ClientLibrary - Common model and util classes used by both microservices  

### FinSightAI database
 The user authentication and preference data are persistently stored and managed in the FinSightAI MongoDB database, which offers high performance and flexibility to handle the schema-less data models typically required by dynamic user  
 data and preferences.
 - `users` Collection: This collection is dedicated to managing user authentication credentials. It stores essential authentication details such as usernames, email and password hashes.
 - `userfinancialprofile` Collection: structured to store detailed profiles of users, containing their financial preferences. This data underpins the platform’s ability to provide personalized investment insights.
> **Fields:**
>   + riskTolerance: Captures the user's appetite for risk, influencing the type of investments recommended (e.g., stocks, bonds).
>   + investmentHorizon: Determines the time frame the user is looking at for their investments, which can range from short-term to long-term.
>   + investmentManagementPreference: Indicates whether the user prefers active or passive management of their investments.
>   + sourcesOfIncome: An array listing the various income streams of the user, which helps in assessing their financial stability and capacity for investments.
>   + financialGoals: Describes the user’s financial aspirations, like starting a business, which would influence the aggressiveness or conservatism of the investment strategy.
>   + investmentCategoryPreference: Specifies the user's preference for certain investment categories, such as national benefit schemes, which could offer tax advantages or align with the user's values.

## Scalability


## Steps for Installation

1. Clone the repo
   ```sh
    git clone https://github.com/CyberneTech/FinSightAI.git
   ```
2. Create a Mongodb database and note the connection URI, username and password, and databse name
3. Update the settings in `application.properties` file:
   - In `FinSightAi` update the mongodb settings with your connection URI, username, password, and database name; 
     Also replace api.secret.key to the key of your choice
   ```sh
   spring.data.mongodb.uri=mongodb+srv://<username>:<password>@cyborgclstr.0uye6xv.mongodb.net/<your-database-name>?retryWrites=true&w=majority
   spring.data.mongodb.username=<username>
   spring.data.mongodb.password=<password>

   api.secret.key=FinSightAI-Cybernetech
   ```
4. Build the project using Maven:
* Navigate to the project directory in the terminal.
* Run the following Maven command to build the project and download all the necessary dependencies:
  ```sh
   mvn clean install
   ```
5. After the build is successful, start the Spring Boot application with the following command:
   ```sh
   mvn spring-boot:run
   ```

### Testing Endpoints with Postman
 A Postman collection is available to test the API endpoints: [go to file](postmanCollection/PostmanCollection-TravelPackageManagement.postman_collection.json)
 <br/>To test the API endpoints:
*Import the Postman collection and make sure the application is running at `http://localhost:8080.`*


## Future Enhancements
- [ ] implementing DTO's for better response payloads for APIs and enhanced security
- [ ] integrating user authentication
- [ ] add a queueing service (eg:Kafka)
- [ ] integrating with client side application
- [ ] indexing database/ hot partitioning
