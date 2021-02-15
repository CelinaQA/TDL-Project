## Coverage: 84.9%  
![Coverage screenshot](https://github.com/CelinaQA/TDL-Project/blob/featureWebsite/Documentation/Coverage.png)

# TDL-Project
This To-do List (TDL) Web Application was designed with full CRUD capabilities so that a user can create a to-do list with multiple tasks assigned to it.  They can keep track with their progress by updating the tasks to mark them as completed, as well as being able to view and delete tasks in a list.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites
You will need to have Java and Git Bash installed on your computer to run the TDL.  
  
For Java: https://www.oracle.com/java/technologies/javase/jdk14-archive-downloads.html  
For Git Bash: https://git-scm.com/download/win 

To run the tests, you will need to have Spring Tool Suite installed.  

For Spring Tools: https://spring.io/tools#suite-three

### Installing

## Running the tests

To run the tests, open the project on Spring Tool Suite.  Right click on the project and select Coverage As -> JUnit Test.

### Unit Tests 
Unit tests are run to ensure that each 'unit' of an application is functioning as they are intended to.  These can be made for individual classes and a test should be made for all the methods and attributes that make up each class.  

### Integration Tests 
Integration tests are run to check that the communication between different objects is happening correctly.  For this TDL web application, a controller class is used to call the relevant service class.  Mock requests are made to the controller class to test the communication between controller and service.

### User Acceptance Criteria Tests
Selenium was used to run automated tests for the following user acceptance criteria: 
Given that I can access my TDL Web Apllication, 
* when I navigate to the create tab and I create a list, I should be able to see that my list was created successfully 
* when I navigate to the create tab and I add a task, I should be able to see that my task was added successfully 
* when I navigate to the read list tab and I select a list to read, I should be able to see the tasks in my list 
* when I navigate to the update list tab and I update a task in my list, I should see the task was updated successfully and see my updated task description 
* when I navigate to the delete tab and I delete a task in my list, I should be able to see the task was deleted successfully 
* when I navigate to the delete tab and I delete a list, I should see the list was deleted successfully 

### Static Analysis
SonarQube was used for static analysis to review the overall quality of the code:
![Coverage screenshot](https://github.com/CelinaQA/TDL-Project/blob/featureWebsite/Documentation/SonarQube-screenshot.png)

## Deployment
This TDL Web Application runs on http://127.0.0.1:8080/index.html.  The .war file must first be executed in the command-line before it can be accessed using the following command: 
```
$ java -jar 
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## Authors
* Celina Basa - [GitHub Link](https://github.com/CelinaQA)

## License

This project is licensed under the MIT license - see the [LICENSE.md](LICENSE.md) file for details 

*For help in [Choosing a license](https://choosealicense.com/)*

## Acknowledgments
Bootstrap: https://getbootstrap.com/

