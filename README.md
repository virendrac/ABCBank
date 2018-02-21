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



# POST : http://localhost:8080/api/tokens/ Creating new Tokens

                1)for single service :
                {
                  	"customer": {
                  		"customerId":"160"
                  		
                  	},
                  	"service":"LOAN ",
                  	"message":"LOAN APPLICATION"
                  }
                2) for multi service : 
                {
                    "customer": {
                        "customerId":"160"
                        
                    },
                    "service":"LOAN , DEPOSIT ",
                    "message":"message here"
                 }

#http://localhost:8080/api/operators/service/ POST : for serving the token
                {
                	"tokenId":23
                
                }


# http://localhost:8080/api/tokens/display GET : for displaying the list of token to be served with corresponding counter number in order of service.


#http://localhost:8080/api/customers POST : for registering a new customer
                {
                    "name":"John Doe",
                    "phone":"7096858765",
                    "address":"221 B bakers street",
                    "typeOfCustomer":"1" //1 for premium and 2 for regular customer
                }