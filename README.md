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

- **percent-of-discrepancies**: uses when you run two instances of this service. The idea of this http service is to provide a data set of required size.
  But if you run two instances of the same size they will contain the same data. So data validation tool won't find any
  discrepancies between them. In order to generate some discrepancies we added a 'percent-of-discrepancies' parameter which
  define how many discrepancies.


### Star application using docker image

To be done.

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

After start application will be available on `hostname:8080`.
Swagger UI is available on `hostname:8080/swagger-ui.html`.