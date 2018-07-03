API's
-
* Users
    > ##### Users Sign In  
    - [X] <a>post</a>  /api/v1/users/signin
        * Request
            ```json
            {
                "email":"user@example.com",
                "password":"password"
            }
            ```
        * Response 
            ```json
            {
                "token": "__TOKEN__ IF SUCCESS",
                "msg": "__MESSAGE_TEXT__",
                "error": "___ERROR_VALUE__"
            }
            ```
    > ##### Users Sign Out  
    - [X] <a>get</a>  /api/v1/users/logout?token=TOKEN
        * Request
            - no data required
        * response
            ```json
            {
                "msg": "Successfully Logout",
                "error": "0"
            }
            ```
            
    > ##### New user Sign Up
    - [x] <a>post </a> /api/v1/users/signup
        * Request
            ```json
            {
                "email":"user@example.com",
                "password":"password",
                "name":"name",
                "phone":"phone"
            }
            ```
        * Response
            ```json
            {
              "msg":"__MESSAGE__",
              "error":"__ERROR_VALUE__"
            }
            ```
    > ##### Add Credits to user
    - [x] <a>post </a> /api/v1/users/credits?token=TOKEN
        * Request
            ```json
            {
                "hash":"CARD-HASH"
            }
            ```
        * Response 
            ```json
            {
              "msg":"__MESSAGE_TEXT__",
              "credits":"__CURRENT_BALANCE_ IF OPERATION SUCCESSED",
              "error":0 
            }
            ```
    > ##### Get User Profile
    - [x] <a>get </a> /api/v1/users/profile?token=TOKEN
        * Request
            - Not data neaded.
        * Response
            ```json
            {
                "msg": "User Found",
                "data": {
                    "id": 54,
                    "name": "__NAME__",
                    "email": "__EMAIL",
                    "status": 0,
                    "group_id": 0,
                    "balance": "__USER__CREDITS__",
                    "pics_uploaded": 1,
                    "phone": "__PHONE__",
                    "img": "__LINK__",
                    "created_at": "__TIME_STAMP__",
                    "updated_at": "__TIME_STAMP__"
                },
                "error": "0"
            }
            ```
    > ##### Update User basic info.
    - [x] <a>post </a> /api/v1/users/update?token=TOKEN
        * Request
            ```json
                 {
                  "email":"user@example.com",
                  "name":"name",
                  "phone":"phone"
                }
            ```
        * Response 
            ```json
            {
                "msg": "User Found",
                "data": {
                    "id": 1,
                    "name": "NAME",
                    "email": "USER@EXAMPLE.COM",
                    "status": 1,
                    "group_id": 1,
                    "balance": "USER-CREDITS",
                    "pics_uploaded": 1,
                    "phone": "PHONE",
                    "img": "LINK",
                    "created_at": "2017-10-17 14:53:17",
                    "updated_at": "2017-10-17 14:53:17"
                },
                "error": "0"
            }
            ```
    > ##### Update User password.
    - [x] <a>post </a> /api/v1/users/password?token=TOKEN
        * Request
             ```json
            {
              "password": "__VALUE",
              "password_confirm": "__THE_SAME_PASSWORD__"
            }
            ```
        * Response
            ```json
            {
              "msg": "__MESSAGE__TEXT__",
              "error": "__ERROR_VALUE__"
             }
            ```
    > ##### Get User History of Orders.
    - [x] <a>get</a> /api/v1/users/history?token=TOKEN
        * Request
            ```json
            {
                "msg": "User Found",
                "history": [
                    {
                        "id": "1",
                        "date": "Nov 15, 2017 11:59:00 pm",
                        "items": [
                            {
                                "name": "test item",
                                "price": "15$",
                                "quantity": 5
                            },
                            {
                                "name": "test item",
                                "price": "15$",
                                "quantity": 5
                            },
                            {
                                "name": "test item",
                                "price": "15$",
                                "quantity": 5
                            }
                        ]
                    },
                    {
                        "id": "2",
                        "date": "Nov 15, 2017 11:59:59 pm",
                        "items": [
                            {
                                "name": "test item",
                                "price": "15$",
                                "quantity": 5
                            },
                            {
                                "name": "test item",
                                "price": "15$",
                                "quantity": 5
                            }
                        ]
                    }
                ],
                "error": "0"
            }
            ```
* Orders
    > ##### Generate QR
    - [X] <a>post</a>  /api/v1/orders/registration?token=TOKEN
        * Request
            ```json
            {
                "firebase_token":"_FIREBASE_TOKEN_"
            }
            ```
        * Response 
            -   no errors
            ```xml
          <?xml version="1.0" encoding="UTF-8"?>
             <svg>
             </svg>
            ```
            *  user not found or  bad token
            ```json
            {
              "msg":"",
              "error":"__ERROR_VALUE__"
            }
            ```

> ##### Get order info
    - [X] <a>get</a>  /api/v1/orders/__ORDER_ID__?token=TOKEN