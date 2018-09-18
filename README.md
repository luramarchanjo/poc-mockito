# Overview

This is a simple poc using [vertx-web] to create java web applications.

## Test

1. Start your application.

2. Execute the commands to test your application

**POST**

`curl -d '{"name" : "test1"}' -H "Content-Type: application/json" -X POST http://localhost:8080/api/v1/products`


**GET**

`curl -X GET http://localhost:8080/api/v1/products`

**PUT**

`curl -d '{"name" : "test-put"}' -H "Content-Type: application/json" -X PUT http://localhost:8080/api/v1/products/3f1423b7-c8e2-4ed1-987a-19079af794e4`

**DELETE**

`curl -X DELETE http://localhost:8080/api/v1/products/3f1423b7-c8e2-4ed1-987a-19079af794e4`


[vertx-web]: https://vertx.io/docs/vertx-web/java/
