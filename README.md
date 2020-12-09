# HTTP datasource example for data validation tool

## Datasource description

This datasource is an example for testing data validation tool. The datasource has a pretty silly logic:
- it generates test data while starting;
- it stores generated data in memory;
- it uses stable data generator. It means, the data generator will generate exactly the same data set every time you start/restart your service 
  (of course if you define the same number of generated entities: see details in configuration description section);
- it has 3 endpoints:
    - GET /users (with request params: 'page' and 'pageSize')
    - GET /departments (with request params: 'page' and 'pageSize')
    - GET /companies (with request params: 'page' and 'pageSize')
    which return test data;
- it also has 3 endpoints which return the total size of elements:
    - GET /users/size
    - GET /departments/size
    - GET /companies/size

## How to run

### Configuration

Application can be configured by customizing the next properties:
- **number-of-users**, **number-of-departments**, **number-of-companies**: define how many
  user/department/company elements has to be generated;

- **size-of-nested-lists**: department contains field "employees". It represents a list of "employee" entities.
  This param defines how long this list has to be;

- **percent-of-discrepancies**: uses when you run two instances of this service. The idea of this http service is to provide a data set of required size, but 
  if you run two instances of the same size they will contain the same data. So data validation tool won't find any
  discrepancies between them. In order to generate some discrepancies we added a 'percent-of-discrepancies' parameter which
  define how many discrepancies has to be generated. So when you start the first instance of datasource you can set this value to 0, and for the second
  instance of the application you can set, for example, 10% of discrepancies.
  
#### Default configuration:
- number-of-users: 1000
- number-of-departments: 1000
- number-of-companies: 1000
- size-of-nested-lists: 100
- percent-of-discrepancies: 0

### Star application using docker image

In order to start the application using docker you have to build a docker image with the next command (in project directory):

`docker build . -t dvt-http-datasource`

To run the application use:

`docker run --name <name> -p <your port>:8080 -d dvt-http-datasource`

You can customize the application properties by adding the next environment variables:

- "APPLICATION_NUMBER_OF_USERS=number-you-need"
- "APPLICATION_NUMBER_OF_DEPARTMENTS=number-you-need"
- "APPLICATION_NUMBER_OF_COMPANIES=number-you-need"
- "APPLICATION_SIZE_OF_NESTED_LISTS=number-you-need"
- "APPLICATION_PERCENT_OF_DISCREPANCIES=number-you-need"

These properties override default values. So you have to override only the properties you have to change, otherwise default properties will be used. 

For example, you want to start two instances of http datasource. You want to have 5000 entities in each table for both datasources. You also want
to have 20% of discrepancies between them. In order to do this you have to run two docker containers:

First container:
```
docker run
 --name datasource-1
 -e "APPLICATION_NUMBER_OF_USERS=5000"
 -e "APPLICATION_NUMBER_OF_DEPARTMENTS=5000"
 -e "APPLICATION_NUMBER_OF_COMPANIES=5000"
 -e "APPLICATION_PERCENT_OF_DISCREPANCIES=0"
 -p 8091:8080
 -d
 dvt-http-datasource
```

Second container:
```
docker run
 --name datasource-2
 -e "APPLICATION_NUMBER_OF_USERS=5000"
 -e "APPLICATION_NUMBER_OF_DEPARTMENTS=5000"
 -e "APPLICATION_NUMBER_OF_COMPANIES=5000"
 -e "APPLICATION_PERCENT_OF_DISCREPANCIES=20"
 -p 8092:8080
 -d
 dvt-http-datasource
```

After that two application will be available at: `hostname:8091` (first) and `hostname:8091` (second).

### Start application locally

You can start the application locally using: `./gradlew bootRun`.

In order to customize the generated data set you can use the next application properties:

```
application:
    number-of-users: 1000
    number-of-departments: 1000
    number-of-companies: 1000
    size-of-nested-lists: 100
    percent-of-discrepancies: 10
```

## How to access

After start application will be available on `hostname:port`.
Swagger UI is available on `hostname:port/swagger-ui.html`.