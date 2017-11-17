#Web notes

##API
>post :: /api/v1/users/signin

* Request
```json
{
	"email":"user@example.com",
	"password":"password"
}
```
* Response
```json 
ok:200
{
    "token": "TOKEN"
    "msg": "Successfully SignIn",
    "error": "0"
}

unauthrized:401
{
    "msg": "Invalid Auth",
    "error": "1"
}
```
>post :: /api/v1/users/signup
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
ok:200

{
"msg":"",
"error":0 
}
ERROR
{
"msg":"",
"error":1 
}

```
>post :: /api/v1/users/credits
* Request
```json
{
	"hash":"CARD-HASH"
}
```
* Response 
```json
ok:200

{
"msg":"",
"credits":"CURRENT BALANCE",
"error":0 
}
ERROR
{
"msg":"",
"error":1 
}

```
>get :: /api/v1/users/profile?token=TOKEN
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
>post :: /api/v1/users/update?token=TOKEN

//TODO:: 
