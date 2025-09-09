# Quote API 

## 🎯 Objective
This project implements a RESTful API that returns a random inspirational quote.  
It includes IP-based rate limiting to ensure fair usage — each client IP can make only 5 requests per minute.  
After the limit is exceeded, the API responds with HTTP 429 {"error": "Rate limit exceeded. Try again in X seconds."}.

---

##⚙️ Tech Stack
- Programming Language: Java 11  
- Framework: Spring Boot  
- Architecture: MVC (Model-View-Controller)  
- Libraries/Tools:  
  - Bucket4j → Rate limiting  
  - Springdoc OpenAPI → Swagger UI documentation  
  - SLF4J/Logback → Logging  
  - JUnit → Unit testing  
- Build Tool: Maven (Wrapper included, no external installation needed)  
- Deployment: GitHub Pages 

---
## 🏗️ Project Structure
quote-api/
├── src/
│   ├── main/
│   │   ├── java/com/example/quoteapi/
│   │   │   ├── QuoteApiApplication.java
│   │   │   ├── config/
│   │   │   │   ├── SwaggerConfig.java
│   │   │   │   ├── RateLimitConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── QuoteController.java
│   │   │   ├── service/
│   │   │   │   ├── QuoteService.java
│   │   │   │   ├── RateLimitService.java
│   │   │   ├── model/
│   │   │   │   ├── Quote.java
│   │   │   │   ├── ErrorResponse.java
│   │   │   ├── interceptor/
│   │   │   │   ├── RateLimitInterceptor.java
│   │   ├── resources/
│   │       ├── application.properties
│   ├── test/
│   │   ├── java/com/example/quoteapi/
│   │       ├── QuoteControllerTest.java
│   │       ├── RateLimitServiceTest.java
├── pom.xml
├── README.md


---

 🧩 Design Patterns & Architecture
- MVC Pattern:  
  - Controller → Handles API requests (`QuoteController`).  
  - Service → Business logic (`QuoteService`, `RateLimitService`).  
  - Model → Quote (returned as JSON).  
- Interceptor Pattern: Applied via `RateLimitInterceptor` for cross-cutting concern (rate limiting).  
- Singleton Pattern: `Bucket` instances per IP stored in map → one per client.  

---

 📖 How It Works
1. Client calls `GET/api/quote`.  
2. Request passes through RateLimitInterceptor, which checks if IP has exceeded its quota.  
3. If allowed → `QuoteService` picks a random quote and returns it.  
   ✅ Successful response:
	{ "quote": "The only way to do great work is to love what you do. - Steve Jobs" }
4. If limit exceeded → returns `429 Too Many Requests` with JSON:  
   ```json
   { "error": "Rate limit exceeded. Try again in X seconds." }

---

🧪 Testing APIs
Using Postman
# Postman → new GET request.
# URL: http://localhost:8080/api/quote
# Send
# Sent multiple times (6th call within 1 min) → Observed HTTP 429 error.

---

Using Swagger UI

# Visit: http://localhost:8080/swagger-ui.html
# Expand GET /api/quote.
# Click Try it out → Execute.
# View response in the UI.
# Repeat >5 times to trigger rate limit.

---

📹 Demo Video

👉 Full executed video (terminal →Web port and Swagger UI → outputs → unit tests):
http//:googledrive.com

---

📚 External Sources / References

Spring Boot Documentation ( spring initializr ) : https://spring.io/projects/spring-boot
Bucket4j Rate Limiting Library : https://github.com/bucket4j/bucket4j
Springdoc OpenAPI : https://springdoc.org/
Swagger UI : https://swagger.io/tools/swagger-ui/

---

🙋 Notes

No database → In-memory only.
Thread-safe implementation.
Deployed backend on Railway and on GitHub Pages.
Used Java, J2EE, J2SE, GitHub, Spring Boot, HTML, CSS ,Referred to external docs/tutorials for Bucket4j, Swagger.
