# test description 

Develop a web application for water delivery. The application should allow the user to:

Choose bottle sizes (1L, 5L, 18L)

Specify delivery time and address

Receive real-time notifications about order status changes

An admin panel should be implemented for managing orders.

# requirements

В ходе выполнения данного задания должен быть реализованно 
приложение для доставки воды. Приложение должно состоять из:
1. Java web приложение с REST API (back end)
2. Хранилище данных (rdbms database)
3. Пользовательский интерфэйс в виде вэб странички (front end)

## back end

### абстракции предметной области

1. User(id, mail, password)  
пользователь приложения. Должен иметь роли(role_user, role_admin)
2. Product(id, name)  
заказываемыей предмет. должен имень различный обьем(1,5,18 л)
3. Basket(id, user_id, item_list)
4. Order(id, user_id, item_list, create_datetime, delivery_datetime, delivery_address, delivery_status)
должен иметь статус(create, confirmed, in_delivery, delivered)

### use cases

#### user 

1. user login in system 

#### product

1. list products

#### basket

1. user create new basket
2. user add product to basket 
3. user delete product from busket
4. user update product quantity in basket
5. user delete basket
6. user create with existing basket

#### order 

1. admin change status of order 

## database  

```plantuml
entity app_user{
UUID id
text email
text password

constraint pk (id)
}

entity user_role{
UUID user_id 
text role

constraint fk (user_id) app_user(id)
constraint unique (user_id, role)
}

entity product{
UUID id
text name

constraint pk (id)
}

entity basket{
UUID id
UUID user_id

constraint pk (id)
constraint fk user_id  app_user(id)
}

entity basket_and_product{
UUID basket_id
UUID product_id

constraint fk (basket_id)  basket(id)
constraint fk (product_id)  product(id)
}

entity order{
UUID id
UUID user_id
timestamp create_datetime
timestamp delivery_datetime
text delivery_address
text delivery_status

constraint pk (id)
constraint fk (user_id) app_user(id)
}

entity order_and_product{
UUID order_id fk order(id)
UUID product_id fk product(id)

constraint fk (order_id)  order(id)
constraint fk (product_id)  product(id)
}

'app_user ||--|{ user_role
'app_user ||--o| basket
'app_user ||--o{ order
'basket ||--o{ product
'order }--|{ product

```