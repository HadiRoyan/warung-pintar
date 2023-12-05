# API Documentation (Warung Pintar)

# API Authentication 

## Register (Create Account) 

Request:
- Method: POST
- Endpoint: ```api/auth/register```
- Body: 
```json
{
    "email": "string",
    "password": "string",
    "store_name": "string",
    "phone_number": "string"
}
```

Response:
```json
{
    "status": "string",
    "data": "string"
}
```

## Login
Request:
- Method: POST
- Endpoint: ```api/auth/login```
- Body: 
```json
{
    "email": "string",
    "password": "string"
}
```

Response: 
```json
{
    "status": "string",
    "data": {
        "access_token": "string",
        "type": "string"
    }
}
```

## Reset Password
Request:
- Method: POST
- Endpoint: ```api/auth/reset-password```
- Body: 
```json
{
    "email": "string",
    "new_password": "string"
}
```

Response:
```json
{
    "status": "string",
    "data": "string"
}
```

# API User
## Read User
Request:
- Method: GET
- Endpoint: ```api/users/{email}```

Response:
```json
{
    "status": "string",
    "data": {
        "userId": "Int",
        "email": "string",
        "store_name": "string",
        "phone_number": "string"
    }
}
```

# API Dashboard
Request:
- Method: GET
- Endpoint: ```api/dashboard/{email}```

Response:
```json
{
    "status": "string",
    "data": {
        "store_data": {
            "email": "string",
            "store_name": "string"
        },
        "stock_data": {
            "entry_product": "Int",
            "exit_product": "Int",
            "product": "Int",
            "low_stock": "Int"
        }
    }
}
```

# API Product

## Add Product
Request:
- Method: POST
- Endpoint: ```api/products```
- Header: 
    - Accept: multipart/form-data
- Body: 
    - part: image
    - part: image_description:
        ```json
        {
            "product_name": "string",
            "entry_date": "string",
            "product_category": "string",
            "product_quantity": "Int",
            "low_stock": "Int",
            "code_stock": "string",
            "purchase_price": "Int",
            "selling_price": "Int",
            "expired_date" : "string",
        }
        ```

Response: 
```json
{
    "status": "string",
    "data": "string"
}
```

## Read Product (detail product)

Request:
- Method: GET
- Endpoint: ```api/products/{productId}```

Response: 
```json
    "status": "string",
    "data": {
        "product_name": "string",
        "entry_date": "string",
        "product_category": "string",
        "product_quantity": "Int",
        "low_stock": "Int",
        "code_stock": "string",
        "purchase_price": "Int",
        "selling_price": "Int",
        "expired_date" : "string",
    }
```

## Read All Product (list product)

Request:
- Method: GET
- Endpoint: ```api/products```

Response: 
```json
    "status": "string",
    "data": [
        {
            "product_name": "string",
            "entry_date": "string",
            "product_category": "string",
            "product_quantity": "Int",
            "low_stock": "Int",
            "code_stock": "string",
            "purchase_price": "Int",
            "selling_price": "Int",
            "expired_date" : "string",
        },
    ]
```

## Read All Category Product (list category product)

Request:
- Method: GET
- Endpoint: ```api/products/categories```

Response: 
```json
    "status": "string",
    "data": [
        "product_category",
    ]
```

## Read ALL History Product

Request :
- Method : GET
- Endpoint : ```api/product/histories/{email}```

Response: 
```json
    "status": "string",
    "data": {
        "history_id" : "string",
        "type" : "string",
        "product_name" : "string",
        "amount" : "int",
        "date" : "string",
        "price" : "string",
    }  
```

## Read ALL Notification

Request :
- Method : GET
- Endpoint : ```api/notification/{email}```

Response: 
```json
    "status": "string",
    "data": {
        "notification_id" : "string",
        "type" : "string",
        "store_name" : "string",
        "product_name" : "string",
    }  
```

## Read ALL Product Out

Request :
- Method : GET
- Endpoint : ```api/products/out/{email}```

Response: 
```json
    "status": "string",
    "data": [
        "product_name"
    ] 
```

## Delete Product

Request :
- Method : DELETE
- Endpoint : ```api/products/out/{email}/{product_name}```
- Body: 
```json
        {
            "product_name": "string",
            "exit_date": "string",
            "product_quantity": "Int",
            "selling_price": "Int",
        }
```

Response: 
```json
    "status": "string",
    "data": 
        "string" 
```
 