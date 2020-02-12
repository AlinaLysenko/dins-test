# Тестовое задание в Dino Systems

## Task
1) Let imagine that we have system that holds and manages contact directory of many companies. 
There is one of HTTP endpoints that responsible for retrieving user data by name: 
GET /company/%companyId%/users?name=%someName% 
Please write test scenarios for this endpoint, as many as you can. 
Example: Request some_domain.com/company/777/users?name=Izergil where 777 is ID of company that does not exist, check that error is returned and HTTP status code is 404 
2) Implement following scenario for this endpoint using java: Verify that user can search only member of his own company.

## Solution
Пункт 2 задания был выполнен на языке Java с использованием фреймворков 
* Spring MVC
* Spring Test
* Junit
* Mockito