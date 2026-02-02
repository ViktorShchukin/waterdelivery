# waterdelivery

---

This project demonstrates a Spring Boot backend with
JWT-based authentication, role-based authorization,
and real-time order status notifications using Server-Sent
Events (SSE).

The project is deployed and publicly accessible at: https://ppudgy.ru:8080/water/ 

## Build and run

After app start automatically create user with login
"admin" and password "admin".

### docker compose

You can launch the app just with one command:

```bash
docker-compose up
```

This command will create and statrs containers with
postgresql database and app. Postgres will be available on
jdbc:postgresql://localhost:5432/postgres.
App will be available on http://localhost:8080

### Manual

I assume that you use linux and have
installed java and maven.

Set up and prepare Postgresql database
Then set up all required environment variables like so:

```bash
export WD_DB_PASSWORD=postgres
```

List of variables:

| variable       | default value | example value                             |
|----------------|---------------|-------------------------------------------|
| WD_DB_PASSWORD | -             | postgres                                  |
| WD_DB_USERNAME | -             | postgres                                  |
| WD_DB_URL      | -             | jdbc:postgresql://localhost:5432/postgres |

then you can build and run the app

```bash
mvn package
java -jar ./target/waterdelivery-0.0.1-SNAPSHOT.jar
```

After run app will be available on http://localhost:8080

## API

| Method | Endpoint                                           | Auth        | Description                             |
|--------|----------------------------------------------------|-------------|-----------------------------------------|
| POST   | /auth/login                                        |             | Authenticate user and return JWT        |
| POST   | /auth/signin                                       |             | Register new user                       |
| GET    | /user/me                                           | JWT         | Get current authenticated user          |
| GET    | /user                                              | JWT (ADMIN) | Get all users                           |
| GET    | /product                                           | JWT         | Get all products                        |
| GET    | /product/{id}                                      | JWT         | Get product by id                       |
| POST   | /product                                           | JWT (ADMIN) | Create new product                      |
| DELETE | /product/{id}                                      | JWT (ADMIN) | Delete product                          |
| GET    | /user/basket                                       | JWT         | Get current user's basket               |
| POST   | /user/basket                                       | JWT         | Create basket                           |
| POST   | /user/basket/add-product/{productId}               | JWT         | Add product to basket                   |
| PUT    | /user/basket/update-product/{productId}?quantity=n | JWT         | Update product quantity in basket       |
| DELETE | /user/basket                                       | JWT         | Delete basket                           |
| POST   | /user/order                                        | JWT         | Create order from basket                |
| GET    | /user/order                                        | JWT         | Get current user's orders               |
| GET    | /user/{userId}/order                               | JWT (ADMIN) | Get all orders by user id               |               
| GET    | /user/order/{id}                                   | JWT         | Get order by id                         |
| PUT    | /user/order/{id}                                   | JWT (ADMIN) | Update order status                     |
| GET    | /user/order/sse?token={jwt-token}                  | JWT         | Subscribe to order status updates (SSE) |

### Authentication & Authorization

**JWT**

Clients authenticate using a JWT Bearer token

Roles

- ROLE_USER
- ROLE_ADMIN

Header:

- Authorization: Bearer {jwt-token}

Query parameter:

- token={jwt-token}

### Authentication

### POST /auth/signin

Register new user.

**Authentication**
None

**Request body**

```json
{
  "login": "user",
  "password": "mypassword"
}
```

**Response body**

```json
{
  "token": "<jwt-token>",
  "user": {
    "id": "uuid",
    "login": "user",
    "roles": [
      "ROLE_USER"
    ]
  }
}
```

---

### POST /auth/login

Authenticate existing user and return JWT token.

**Authentication**
None

**Request body**

```json
{
  "login": "admin",
  "password": "admin"
}
```

**Response body**

```json
{
  "token": "<jwt-token>",
  "user": {
    "id": "uuid",
    "login": "admin",
    "roles": [
      "ROLE_USER",
      "ROLE_ADMIN"
    ]
  }
}
```

---

### User

### GET /user/me

Return current authenticated user.

**Authentication**
Bearer JWT

**Response body**

```json
{
  "id": "uuid",
  "login": "admin",
  "roles": [
    "ROLE_USER",
    "ROLE_ADMIN"
  ]
}
```

---

### GET /user

Return all users (admin only).

**Authentication**
Bearer JWT (ROLE_ADMIN)

**Response body**

```json
{
  "content": [
    {
      "id": "e17c54c3-e4b9-4f02-91da-51c5cbe6cdc9",
      "login": "admin",
      "roles": [
        "ROLE_USER",
        "ROLE_ADMIN"
      ]
    },
    {
      "id": "03b0de81-41e2-4de3-9e18-4f3b6efd2004",
      "login": "user",
      "roles": [
        "ROLE_USER"
      ]
    }
  ],
  "empty": false,
  "first": true,
  "last": true,
  "number": 0,
  "numberOfElements": 2,
  "pageable": {
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 10,
    "paged": true,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "unpaged": false
  },
  "size": 10,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "totalElements": 2,
  "totalPages": 1
}
```

---

### Products

### GET /product

Get all products.

**Authentication**
Bearer JWT

**Response body**

```json
{
  "content": [
    {
      "id": "30b65df6-0376-485c-8bbe-5867ccf6f488",
      "name": "1L"
    },
    {
      "id": "db728b73-3b06-4734-aa4f-d3341a84cabb",
      "name": "5L"
    }
  ],
  "empty": false,
  "first": true,
  "last": true,
  "number": 0,
  "numberOfElements": 2,
  "pageable": {
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 10,
    "paged": true,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "unpaged": false
  },
  "size": 10,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "totalElements": 2,
  "totalPages": 1
}
```

---

### GET /product/{id}

Get product by id.

**Authentication**
Bearer JWT

**Response body**

```json
{
  "id": "30b65df6-0376-485c-8bbe-5867ccf6f488",
  "name": "Water"
}
```

---

### POST /product

Create new product.

**Authentication**
Bearer JWT (ROLE_ADMIN)

**Request body**

```json
{
  "name": "1L"
}
```

**Response body**

```json
{
  "id": "30b65df6-0376-485c-8bbe-5867ccf6f488",
  "name": "Water"
}
```

---

### DELETE /product/{id}

Delete product.

**Authentication**
Bearer JWT (ROLE_ADMIN)

---

### Basket

### GET /user/basket

Get current user's basket.

**Authentication**
Bearer JWT

**Response body**

```json
{
  "id": "ed38955d-0852-427c-8a50-00dbff780f5c",
  "items": [],
  "userId": "e17c54c3-e4b9-4f02-91da-51c5cbe6cdc9"
}
```

---

### POST /user/basket

Create basket for user.

**Authentication**
Bearer JWT

**Response body**

```json
{
  "id": "ed38955d-0852-427c-8a50-00dbff780f5c",
  "items": [],
  "userId": "e17c54c3-e4b9-4f02-91da-51c5cbe6cdc9"
}
```

---

### POST /user/basket/add-product/{productId}

Add product to basket.

**Authentication**
Bearer JWT

**Response body**

```json
{
  "id": "ed38955d-0852-427c-8a50-00dbff780f5c",
  "items": [
    {
      "id": "ced6a231-5d76-44ed-b2b7-88c68d7f92b3",
      "product": {
        "id": "30b65df6-0376-485c-8bbe-5867ccf6f488",
        "name": "1L"
      },
      "quantity": 1
    }
  ],
  "userId": "e17c54c3-e4b9-4f02-91da-51c5cbe6cdc9"
}
```

---

### PUT /user/basket/update-product/{productId}?quantity={n}

Update product quantity in basket.

**Authentication**
Bearer JWT

**Response body**

```json
{
  "id": "ed38955d-0852-427c-8a50-00dbff780f5c",
  "items": [
    {
      "id": "ced6a231-5d76-44ed-b2b7-88c68d7f92b3",
      "product": {
        "id": "30b65df6-0376-485c-8bbe-5867ccf6f488",
        "name": "1L"
      },
      "quantity": 5
    }
  ],
  "userId": "e17c54c3-e4b9-4f02-91da-51c5cbe6cdc9"
}
```

---

### DELETE /user/basket

Delete basket.

**Authentication**
Bearer JWT

---

### Orders

### POST /user/order

Create order from basket.

**Authentication**
Bearer JWT

**Request body**

```json
{
  "deliveryAddress": "my address",
  "deliveryDateTime": "2026-01-29T04:55:02.227Z"
}
```

**Response body**

```json
{
  "createdAt": "2026-02-02T01:29:59.090795169+03:00",
  "deliveryAddress": "my address",
  "deliveryDateTime": "2026-01-29T04:55:02.227Z",
  "id": "81f7434f-3612-45f8-be14-2bb41bf29211",
  "items": [
    {
      "id": "cdddf68a-1646-497c-bcd3-a66f0b11693a",
      "product": {
        "id": "30b65df6-0376-485c-8bbe-5867ccf6f488",
        "name": "1L"
      },
      "quantity": 1
    }
  ],
  "orderStatus": "CREATED",
  "user": {
    "id": "e17c54c3-e4b9-4f02-91da-51c5cbe6cdc9",
    "login": "admin"
  }
}
```

---

### GET /user/order

Get current user's orders.

**Authentication**
Bearer JWT

**Response body**

```json
{
  "content": [
    {
      "createdAt": "2026-02-01T22:29:59.090795Z",
      "deliveryAddress": "my address",
      "deliveryDateTime": "2026-01-29T04:55:02.227Z",
      "id": "81f7434f-3612-45f8-be14-2bb41bf29211",
      "items": [
        {
          "id": "cdddf68a-1646-497c-bcd3-a66f0b11693a",
          "product": {
            "id": "30b65df6-0376-485c-8bbe-5867ccf6f488",
            "name": "1L"
          },
          "quantity": 1
        }
      ],
      "orderStatus": "CREATED",
      "user": {
        "id": "e17c54c3-e4b9-4f02-91da-51c5cbe6cdc9",
        "login": "admin"
      }
    }
  ],
  "empty": false,
  "first": true,
  "last": true,
  "number": 0,
  "numberOfElements": 1,
  "pageable": {
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 10,
    "paged": true,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "unpaged": false
  },
  "size": 10,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "totalElements": 1,
  "totalPages": 1
}
```

---

### GET /user/order/{id}

Get order by id.

**Authentication**
Bearer JWT

**Response body**

```json
{
  "createdAt": "2026-01-29T05:02:32.298639Z",
  "deliveryAddress": "my address",
  "deliveryDateTime": "2026-01-29T04:55:02.227Z",
  "id": "dd7e9775-1fb8-47a3-8746-505eb9d0f59c",
  "items": [],
  "orderStatus": "DELIVERED",
  "user": {
    "id": "daa8e56f-23fd-427b-af32-086c5922b336",
    "login": "admin"
  }
}
```

### GET /user/{userId}/order

Get orders by user id.

**Authentication**
Bearer JWT (ROLE_ADMIN)

**Response body**

```json
{
  "content": [
    {
      "createdAt": "2026-02-01T22:29:59.090795Z",
      "deliveryAddress": "my address",
      "deliveryDateTime": "2026-01-29T04:55:02.227Z",
      "id": "81f7434f-3612-45f8-be14-2bb41bf29211",
      "items": [
        {
          "id": "cdddf68a-1646-497c-bcd3-a66f0b11693a",
          "product": {
            "id": "30b65df6-0376-485c-8bbe-5867ccf6f488",
            "name": "1L"
          },
          "quantity": 1
        }
      ],
      "orderStatus": "CREATED",
      "user": {
        "id": "e17c54c3-e4b9-4f02-91da-51c5cbe6cdc9",
        "login": "admin"
      }
    }
  ],
  "empty": false,
  "first": true,
  "last": true,
  "number": 0,
  "numberOfElements": 1,
  "pageable": {
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 10,
    "paged": true,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "unpaged": false
  },
  "size": 10,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "totalElements": 1,
  "totalPages": 1
}
```

---

### PUT /user/order/{id}

Update order status (admin).

**Authentication**
Bearer JWT (ROLE_ADMIN)

**Request body**

```json
{
  "status": "IN_DELIVERY"
}
```

**Response body**

```json
{
  "createdAt": "2026-02-01T22:29:59.090795Z",
  "deliveryAddress": "my address",
  "deliveryDateTime": "2026-01-29T04:55:02.227Z",
  "id": "81f7434f-3612-45f8-be14-2bb41bf29211",
  "items": [
    {
      "id": "cdddf68a-1646-497c-bcd3-a66f0b11693a",
      "product": {
        "id": "30b65df6-0376-485c-8bbe-5867ccf6f488",
        "name": "1L"
      },
      "quantity": 1
    }
  ],
  "orderStatus": "IN_DELIVERY",
  "user": {
    "id": "e17c54c3-e4b9-4f02-91da-51c5cbe6cdc9",
    "login": "admin"
  }
}
```

---

### SSE

### GET /user/order/sse?token={jwt-token}

Subscribe to real-time order status updates using Server-Sent Events.

**Authentication**
JWT via query parameter

**Event example**

```text
event: change-order-status
data: {
  "orderId": "e17c54c3-e4b9-4f02-91da-51c5cbe6cdc9",
  "status": "DELIVERED",
  "changedAt": "2026-01-30T07:35:59+03:00"
}
```



