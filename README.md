# Main Application Requirements
* Java 21

# How to make the main Application up running 
* Run in your favourite IDE
* Make sure the gradlew has a write permission using command:  `bash chmod +x gradlew` 
* Run with Gradle using: `./gradlew bootRun`
* Run jar directly using: `java -jar jar/qa-assignment-0.0.1-SNAPSHOT.jar`

# Swagger
* Swagger UI can be accessed via [http://localhost:8080/webjars/swagger-ui/index.html](http://localhost:8080/webjars/swagger-ui/index.html)

# QA Assignment

This repository contains the deliverables for the QA assignment, including the test strategy, test cases, bug reports, and automated test scripts.

## Automated Test Scripts

### Tech Stack
The automated test scripts are written using:
- **Java 21**: The programming language used to write the test scripts.
- **JUnit 5**: A popular testing framework for unit testing in Java.
- **RestAssured**: A Java-based library used for testing and validating REST APIs.
- **Gradle**: A build automation tool that compiles and runs the tests.

### How to Run the Automated Tests
1. **Ensure the application is running**:
    - Start the application using Gradle:
      ```bash
      ./gradlew bootRun
      ```
    - Alternatively, you can run the JAR file directly:
      ```bash
      java -jar jar/qa-assignment-0.0.1-SNAPSHOT.jar
      ```

2. **Run the Tests**:
    - Open a new terminal window.
    - Execute the tests using Gradle:
      ```bash
      ./gradlew test
      ```

### How to Check the Test Results
- **View Test Results in the Terminal**: The test results, including any `System.out.println` outputs and exceptions, will be displayed in the terminal where the tests are executed.
- **View Detailed Test Reports**:
    - After running the tests, a detailed HTML report is generated at:
      ```
      build/reports/tests/test/index.html
      ```
    - Open this file in a web browser to see a detailed report of all test cases, including pass/fail status, error messages, and stack traces.

## Contents
- [Test Strategy](qa-docs/Test_Strategy.md): Comprehensive test strategy covering unit, integration, system, and user acceptance testing.
- [Test Cases](qa-docs/Test_Cases.md): Detailed test cases for each endpoint, covering positive and negative scenarios.
- [Bug Reports](qa-docs/Bug_Reports.md): Documented bugs identified during testing, including steps to reproduce and suggested fixes.
- [Automated Test Scripts](src/test/java): Automated test scripts for the API endpoints using RestAssured.

## How to Use the Documents
- **Test Strategy**: Provides an overview of the testing approach and methodology.
- **Test Cases**: Offers a detailed set of scenarios to validate each API endpoint.
- **Bug Reports**: Lists the identified bugs and suggests potential fixes.
- **Automated Test Scripts**: Contains scripts to automate the testing of the API endpoints.

## Future Considerations
- Explore performance testing for endpoints using tools like JMeter or Gatling.
- Extend test coverage to include additional components like databases and microservices.
- Integrate tests into a CI/CD pipeline for continuous feedback.

## Collaboration
- Collaborator: [gbucko@sohosquared.com](mailto:gbucko@sohosquared.com)

## License
This repository is private and intended for use by authorized personnel only.
