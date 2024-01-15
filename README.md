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

## Scalability and Performance Optimization
- Microservices Architecture, deployed as independent units to allow for horizontal scaling in response to varying demand. Promotes resilience and agile deployment with a focus on discrete business functionalities.
- Rate Limiting, ensures resource availability and consistent service quality by limiting the number of API requests
- The use of a NoSQL database like MongoDB supports horizontal scaling and can handle large volumes of unstructured data.
- Implementing caching to minimize calls to external APIs, mitigating latency and load, with independent scalability of the caching layer.
- Integrating an API Gateway to efficiently handle traffic surges, providing load balancing and routing.

  
### Microservices
 
- **FinSightAI** : service encompasses user authentication and financial profile management. It is designed to secure user sessions and personalize financial services based on user preferences.
    + Manages user authentication data: Implements secure sign-in and log-in functionalities, including token-based authentication, ensuring secure and stateless authentication across HTTP requests. 
    + Financial Profile Management: Allows users to create and edit their financial profiles, encompassing preferences like riskTolerance, investmentHorizon, and others.

- **FinSight-LLMService** : This service interfaces with the GPT-3.5 Turbo API to generate customized investment insights based on user financial profiles. Validates JWT token to maintain user session integrity.
    + Insight Generation: microservice receives user requests for investment insights and constructs prompts using the financial profile data and sends HTTP requests to the GPT-3.5 Turbo API, utilizing its advanced NLP capabilities to analyze financial data and generate insights.
    +  Rate Limiting: mircoservice enforces a rate limit of 5 requests per second to the API, considering the LLM response times. Ensures optimal utilization of the LLM API and maintains a high quality of service by preventing overuse and ensuring equitable access.

**FinSight-ClientLibrary** - Serves as a shared library containing common models and utilities used across FinSightAI microservices. Compiled into a JAR file, the library is included as a dependency in both microservices, promoting code reuse and consistency. 
 + Contains, UserFinancialProfile model, a shared class that defines the structure of user financial data, used for generating investment insights and managing user preferences. 
 + JWT Token Utility, with functionality to generate and validate JWTs for secure authentication and authorization processes.


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
   - In `FinSightAI-LLMService` update the mongodb settings with your connection URI, username, password, and database name.  
    Replace openai token with your token value, and replace api.secret.key as required
   ```sh
   spring.data.mongodb.uri=mongodb+srv://<username>:<password>@cyborgclstr.0uye6xv.mongodb.net/<your-database-name>?retryWrites=true&w=majority
   spring.data.mongodb.username=<username>
   spring.data.mongodb.password=<password>

   server.port=8083
   api.openai.url=https://api.openai.com/v1/chat/completions
   api.gpt.3.5.token=<openai-api-token>
   api.llm.model=<openai-model>

   api.secret.key=FinSightAI-Cybernetech
   ```
4. Build the project using Maven:
* Navigate to the `FinSightAI-ClientLibrary` directory
* Run the following Maven command to build the client lib and make `finsight-client-libs.jar` which is a dependency for other services:
  ```sh
   mvn clean install
   ```
5. Navigate to `FinSightAI` directory.
* Run the following Maven command to build the project and download all the necessary dependencies:
  ```sh
   mvn clean install
   ```
6. After the build is successful, start the Spring Boot application with the following command:
   ```sh
   mvn spring-boot:run
   ```
7. Now repeat steps `5` and `6` for `FinSightAI-LLMService` as well; Microservices should be up at following urls
   ```
   http://localhost:8080/
   http://localhost:8083/
   ```

### Testing Endpoints
1. Using the `FinSightAI-Frontend` application: [Goto](https://github.com/CyberneTech/FinSightAI-frontend.git) and replicate steps

2. With Postman
 A Postman collection is available to test the API endpoints: [go to file](./FinSightAI.postman_collection.json)
 <br/>To test the API endpoints:
*Import the Postman collection and make sure the application is running at `http://localhost:8080.`*


## Future Enhancements
- [ ]  Implementing caching to minimize calls to external APIs
- [ ]  Integrating an API Gateway to efficiently handle traffic surges, providing load balancing and routing.
- [ ]  Polling/ Crawling data related to current capital market trends form external API and finetuning LLM to provide more accurate results 
