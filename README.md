## Plain REST API CRUD with Spring Boot and PostgreSQL.


[![Build Status]

## Installation:
Postgresql DB:
DB Schema name: book_db
DB Port: 5433
username: postgres
password: vc123456

#####To run this application use:

```bash
mvn spring-boot:run
```

### The view in the Postman:


User Name : user
Password : user

# GET : http://localhost:8080/api/customers/display :a service that displays a list of service counters and token numbers which are not yet processed
                                                        or in queue for multi counter processing, in order of serving priority based on token priority.


# GET : http://localhost:8080/api/tokens/ getting list of all tokens


# POST : http://localhost:8080/api/tokens/ Creating new Tokens

                1) BODY for new customer : {
                   	"customer":"{             'name': 'Virendra Chouhan',        'phone': 1234567890,        'address': 'Jubilee Hills',        'typeOfService': 'R'    }" ,
                   	"remarks" : "HIGHVALUE"
                   }
                2) BODY for Existing customer : {
                                                	"token":"{'customerId': 20}" ,
                                                	"remarks" : "HIGHVALUE"

                                                }

# PUT : http://localhost:8080/api/tokens/tokenProcess/{tokenId} : for serving the customer token
                1).Body for single counter service of the token
                   {
                        "token":"{ 'message':'Loan process initiated'}"
                   }
                 2).Body for Multi counter service of the token
                    {
                        "token":"{ 'message':'Loan process initiated'}" ,
                        "nextServiceCounter" :2
                     }



# http://localhost:8080/api/tokens/12 getting list of token with id = 12
# http://localhost:8080/api/tokens/updateStatus/1/CREATED

# http://localhost:8080/api/tokens/ POST
 {
        "customerId": 20,
        "typeOfService": "P"
 }


http://localhost:8080/api/tokens/updateMessage/{tokenId} : PUT:
{
    "message" : "Message to be appended on the token"
}
