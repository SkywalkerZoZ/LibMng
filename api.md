

| code | description                                    |
| ---- | ---------------------------------------------- |
| 1**  | 信息，服务器收到请求，需要请求者继续执行操作   |
| 2**  | 成功，操作被成功接收并处理                     |
| 3**  | 重定向，需要进一步的操作以完成请求             |
| 4**  | 客户端错误，请求包含语法错误或无法完成请求     |
| 5**  | 服务器错误，服务器在处理请求的过程中发生了错误 |



```json
{
    "code": 200,
    "message": "Success",
    "data": {
    }
}
```



```json
{
    "code": 404,
    "message": "Failed: not found",
    "data": null
}
```



```json
{
    "code": 400,
    "message": "Failed: bad request",
    "data":null
}
```



# 登录

**接口地址**



| 方法 | 地址   |
| ---- | ------ |
| POST | /login |



**请求参数**

| 参数     | 类型   | 是否必需 | 描述                                                         |
| -------- | ------ | -------- | ------------------------------------------------------------ |
| username | string | 否       | 用户名                                                       |
| password | string | 否       | 密码                                                         |
| userRole | string | 是       | 用户角色，{"user", "admin", "guest"}，<br />无username和password则用"guest" |



```json
{
	"username":"test1",
	"password":"1234",
	”userRole":"user"
}
```



```json
{
	"username": null,
	"password": null,
	”userRole":"guest"
}
```



**响应参数**

| 参数       | 类型   | 描述                                                   |
| ---------- | ------ | ------------------------------------------------------ |
| code       | int    | 状态码                                                 |
| message    | string | 提示信息                                               |
| data       | array  | 数据                                                   |
| data.token | string | 验证的token，前端应将其保存，后面每次发起请求携带token |



```json
{
	"code": 200,
	"message": "Success: login",
	"data": {
		"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
	}
}

```





```json
{
	"code":401,
	"message":"Failed: unauthorized, invalid username or password",
	"data":null
}
```



# 注册

**接口地址**




| 方法 | 地址    |
| ---- | ------- |
| POST | /regist |



**请求参数**

| 参数     | 类型   | 是否必需 | 描述                        |
| -------- | ------ | -------- | --------------------------- |
| username | string | 是       | 用户名                      |
| password | string | 是       | 密码                        |
| email    | string | 是       | 邮箱                        |
| userRole | string | 是       | 用户角色，{"user", "admin"} |



```json
{
    "username":"tester",
	"password":"123456",
    "email":"tester@qq.com",
    "userRole":"user"
}
```





**响应参数**

| 参数    | 类型   | 描述     |
| ------- | ------ | -------- |
| code    | int    | 状态码   |
| message | string | 提示信息 |
| data    | array  | 数据     |



```json
{
	"code": 200,
	"message" : "Success: regist",
	"data": null
}
```



```json
{
	"code": 400,
	"message": "Failed: duplicate username",
	"data": null
}
```



# 管理员



## 个人中心信息显示

**接口地址**



| 方法 | 地址          |
| ---- | ------------- |
| GET  | /user/profile |



**请求参数**



无



**响应参数**



| 参数     | 类型   | 描述                     |
| -------- | ------ | ------------------------ |
| userId   | int    | 用户ID                   |
| username | string | 用户名                   |
| email    | string | 邮箱地址                 |
| avatar   | blob   | 头像图片数据，base64编码 |
| userRole | string | 用户角色                 |



```json
{
    "code": 200,
    "message": "Success: user profile",
    "data": {
    	"userId": 123,
    	"username": "test1",
    	"email": "test1@example.com",
    	"avatar": "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/...",
    	"userRole": "admin"
	}
}
```





## 修改个人信息

**接口地址**



| 方法 | 地址          |
| ---- | ------------- |
| PUT  | /user/profile |



**请求参数**



| 参数     | 类型   | 是否必需 | 描述                         |
| -------- | ------ | -------- | ---------------------------- |
| email    | string | 否       | 新的邮箱地址                 |
| password | string | 否       | 新的密码                     |
| avatar   | blob   | 否       | 新的头像图片数据，base64编码 |



```json
{
    "email": "newemail@example.com",
    "password": "newpassword123",
    "avatar": "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/..."
}
```



**响应参数**



| 参数    | 类型   | 描述     |
| ------- | ------ | -------- |
| code    | int    | 状态码   |
| message | string | 提示信息 |



```json
{
    "code": 200,
    "message": "Success: profile",
    "data":null
}
```



```json
{
    "code": 400,
    "message": "Failed: bad request",
    "data":null
}
```



## 图书目录

**接口地址**



| 方法 | 地址                |
| ---- | ------------------- |
| GET  | /user/books/catalog |



**请求参数**

无



**响应参数**

| 参数                | 类型   | 描述         |
| ------------------- | ------ | ------------ |
| code                | int    | 状态码       |
| message             | string | 提示信息     |
| data                | array  | 图书目录数组 |
| data[i].isbn        | string | ISBN号       |
| data[i].title       | string | 标题         |
| data[i].author      | string | 作者         |
| data[i].cover       | blob   | 封面图片数据 |
| data[i].description | string | 描述         |
| data[i].available   | int    | 现存数量     |
| data[i].borrowed    | int    | 被借阅数量   |



```json
{
    "code": 200,
    "message": "Success: books catalog",
    "data": [
        {
            "isbn": "978-3-16-148410-0",
            "title": "Book Title 1",
            "author": "Author 1",
            "cover": "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/...",
            "description": "Description of Book 1",
            "available": 5,
            "borrowed": 10
        },
        {
            "isbn": "978-3-16-148410-1",
            "title": "Book Title 2",
            "author": "Author 2",
            "cover": "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/...",
            "description": "Description of Book 2",
            "available": 3,
            "borrowed": 8
        }
    ]
}

```



## 图书检索



**接口地址**



| 方法 | 地址               |
| ---- | ------------------ |
| GET  | /user/books/search |



**请求参数**

| 参数    | 类型   | 是否必需 | 描述       |
| ------- | ------ | -------- | ---------- |
| keyword | string | 是       | 检索关键词 |



```json
{
    "keyword": "SPM"
}
```



**响应参数**



| 参数                | 类型   | 描述         |
| ------------------- | ------ | ------------ |
| code                | int    | 状态码       |
| message             | string | 提示信息     |
| data                | array  | 检索结果数组 |
| data[i].isbn        | string | ISBN号       |
| data[i].title       | string | 标题         |
| data[i].author      | string | 作者         |
| data[i].cover       | blob   | 封面图片数据 |
| data[i].description | string | 描述         |
| data[i].available   | int    | 现存数量     |
| data[i].borrowed    | int    | 被借阅数量   |



```json
{
    "code": 200,
    "message": "Success: books search",
    "data": [
        {
            "isbn": "978-3-16-148410-0",
            "title": "Book Title 1",
            "author": "Author 1",
            "cover": "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/...",
            "description": "Description of Book 1",
            "available": 5,
            "borrowed": 10
        },
        {
            "isbn": "978-3-16-148410-1",
            "title": "Book Title 2",
            "author": "Author 2",
            "cover": "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/...",
            "description": "Description of Book 2",
            "available": 3,
            "borrowed": 8
        }
    ]
}

```



## 图书借阅信息目录

**接口地址**



| 方法 | 地址                        |
| ---- | --------------------------- |
| GET  | /admin/books/borrowing-info |



**请求参数**

| 参数 | 类型   | 是否必需 | 描述     |
| ---- | ------ | -------- | -------- |
| isbn | string | 是       | 图书ISBN |



```json
{
	"isbn":"978-3-16-148410-0"
}
```



**响应参数**

| 参数               | 类型   | 描述                 |
| ------------------ | ------ | -------------------- |
| code               | int    | 状态码               |
| message            | string | 提示信息             |
| data               | array  | 图书借阅信息目录数组 |
| data[i].username   | string | 读者用户名           |
| data[i].borrowDate | string | 借阅日期             |
| data[i].dueDate    | string | 到期日期             |
| data[i].bookId     | int    | 借阅书籍ID           |



```json
{
    "code": 200,
    "message": "Success: books borrowing-info",
    "data": [
        {
            "username": "user1",
            "borrowDate": "2024-03-20",
            "dueDate": "2024-04-20",
            "bookId": 1
        },
        {
            "username": "user2",
            "borrowDate": "2024-03-18",
            "dueDate": "2024-04-18",
            "bookId": 2
        }
    ]
}

```



## 增加新图书信息

**接口地址**



| 方法 | 地址             |
| ---- | ---------------- |
| POST | /admin/books/add |



**请求参数**

| 参数        | 类型   | 是否必需 | 描述             |
| ----------- | ------ | -------- | ---------------- |
| isbn        | string | 是       | 新图书的ISBN号   |
| title       | string | 是       | 新图书的标题     |
| author      | string | 是       | 新图书的作者     |
| cover       | blob   | 是       | 新图书的封面图片 |
| description | string | 是       | 新图书的描述     |



```json
{
    "isbn": "978-3-16-148410-2",
    "title": "New Book Title",
    "author": "New Author",
    "cover": "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/...",
    "description": "Description of New Book"
}
```



**响应参数**

| 参数    | 类型   | 描述           |
| ------- | ------ | -------------- |
| code    | int    | 状态码         |
| message | string | 提示信息       |
| data    | object | 新增的图书信息 |



```json
{
    "code": 200,
    "message": "Success: books add",
    "data": null
}
```



```json
{
    "code": 400,
    "message": "Failed: bad request",
    "data":null
}
```



## 删除图书信息

**接口地址**

| 方法   | 地址                |
| ------ | ------------------- |
| DELETE | /admin/books/{isbn} |



**请求参数**

无



**响应参数**

| 参数    | 类型   | 描述     |
| ------- | ------ | -------- |
| code    | int    | 状态码   |
| message | string | 提示信息 |
| data    | object | 删除结果 |



```json
{
    "code": 200,
    "message": "Success: books delete",
    "data": null
}
```



```json
{
    "code": 400,
    "message": "Failed: isbn not found",
    "data":null
}
```



## 修改图书信息

**接口地址**

| 方法 | 地址                |
| ---- | ------------------- |
| PUT  | /admin/books/{isbn} |



**请求参数**

| 参数        | 类型   | 是否必需 | 描述             |
| ----------- | ------ | -------- | ---------------- |
| title       | string | 否       | 新的标题         |
| author      | string | 否       | 新的作者         |
| cover       | blob   | 否       | 新的封面图片数据 |
| description | string | 否       | 新的书籍描述     |



```json
{
    "title": "New Title",
    "author": "New Author",
    "cover": "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/...",
    "description": "New Description"
}

```



**响应参数**

| 参数    | 类型   | 描述     |
| ------- | ------ | -------- |
| code    | int    | 状态码   |
| message | string | 提示信息 |
| data    | object | 修改结果 |



```json
{
    "code": 200,
    "message": "Success",
    "data": null
}
```



```json
{
    "code": 400,
    "message": "Failed: bad request",
    "data":null
}
```



## 增加图书实体

**接口地址**



| 方法 | 地址                   |
| ---- | ---------------------- |
| POST | /admin/books/instances |



**请求参数**

| 参数 | 类型   | 是否必需 | 描述         |
| ---- | ------ | -------- | ------------ |
| isbn | string | 是       | 图书的ISBN号 |



```json
{
    "isbn": "978-3-16-148410-0",
    "number": 5
}
```



**响应参数**

| 参数    | 类型   | 描述         |
| ------- | ------ | ------------ |
| code    | int    | 状态码       |
| message | string | 提示信息     |
| data    | object | 操作结果信息 |



```json
{
    "code": 200,
    "message": "Success: books instances",
    "data": null
}

```



```json
{
    "code": 400,
    "message": "Failed: bad request",
    "data":null
}
```



## 删除图书实体

**接口地址**



| 方法   | 地址                                |
| ------ | ----------------------------------- |
| DELETE | /admin/books/instances/{instanceId} |



**请求参数**

无



**响应参数**



| 参数    | 类型   | 描述         |
| ------- | ------ | ------------ |
| code    | int    | 状态码       |
| message | string | 提示信息     |
| data    | object | 操作结果信息 |



```json
{
    "code": 200,
    "message": "Success: books instances delete",
    "data": null
}
```



```json
{
    "code": 400,
    "message": "Failed: instanceId not found",
    "data":null
}
```



## 查看借阅申请

**接口地址**



| 方法 | 地址                          |
| ---- | ----------------------------- |
| GET  | /admin/borrowing/applications |



**请求参数**

无



**响应参数**

| 参数                     | 类型   | 描述                                     |
| ------------------------ | ------ | ---------------------------------------- |
| code                     | int    | 状态码                                   |
| message                  | string | 提示信息                                 |
| data                     | array  | 借阅申请列表                             |
| data[i].borrowingId      | int    | 借阅记录ID                               |
| data[i].userId           | int    | 用户ID                                   |
| data[i].username         | string | 用户名                                   |
| data[i].instanceId       | int    | 图书实体ID                               |
| data[i].isbn             | string | 图书ISBN号                               |
| data[i].borrowDate       | string | 借阅日期                                 |
| data[i].dueDate          | string | 应归还日期                               |
| data[i].borrowAprvStatus | int    | 借阅申请的审批状态，0为未审批，1为已审批 |



```json
{
    "code": 200,
    "message": "Success: borrowing applications",
    "data": [
        {
            "borrowingId": 1,
            "userId": 456,
            "username": "test_user",
            "instanceId": 123,
            "isbn": "1234567890123",
            "borrowDate": "2024-03-21",
            "dueDate": "2024-04-21",
            "borrowAprvStatus":0
        },
        {
            "borrowingId": 2,
            "userId": 789,
            "username": "another_user",
            "instanceId": 456,
            "isbn": "4567890123456",
            "borrowDate": "2024-03-22",
            "dueDate": "2024-04-22",
            "borrowAprvStatus":1
        }
    ]
}

```



## 查看迟还申请

**接口地址**

| 方法 | 地址                          |
| ---- | ----------------------------- |
| GET  | /admin/borrowing/late-returns |



**请求参数**

无



**响应参数**

| 参数                      | 类型   | 描述                                     |
| ------------------------- | ------ | ---------------------------------------- |
| code                      | int    | 状态码                                   |
| message                   | string | 提示信息                                 |
| data                      | array  | 迟还申请列表                             |
| data[i].borrowingId       | int    | 借阅记录ID                               |
| data[i].userId            | int    | 用户ID                                   |
| data[i].username          | string | 用户名                                   |
| data[i].instanceId        | int    | 图书实体ID                               |
| data[i].isbn              | string | 图书ISBN号                               |
| data[i].borrowDate        | string | 借阅日期                                 |
| data[i].dueDate           | string | 应归还日期                               |
| data[i].lateRetDate       | string | 迟还日期                                 |
| data[i].lateRetAprvStatus | int    | 迟还申请的审批状态，0为未审批，1为已审批 |



```json
{
    "code": 200,
    "message": "Success: borrowing late-returns",
    "data": [
        {
            "borrowingId": 1,
            "userId": 456,
            "username": "test_user",
            "instanceId": 123,
            "isbn": "1234567890123",
            "borrowDate": "2024-03-21",
            "dueDate": "2024-04-21",
            "lateRetDate":"2024-05-21",
            "lateRetAprvStatus":0
        },
        {
            "borrowingId": 2,
            "userId": 789,
            "username": "another_user",
            "instanceId": 456,
            "isbn": "4567890123456",
            "borrowDate": "2024-03-22",
            "dueDate": "2024-04-22",
            "lateRetDate":"2024-05-22",
			"lateRetAprvStatus":1
        }
    ]
}

```







## 处理借阅申请

**接口地址**

| 方法 | 地址                                        |
| ---- | ------------------------------------------- |
| PUT  | /admin/borrowing/applications/{borrowingId} |



**请求参数**

| 参数  | 类型 | 是否必需 | 描述               |
| ----- | ---- | -------- | ------------------ |
| agree | int  | 是       | 1为同意，0为不同意 |



```json
{
    "agree": 1
}
```



**响应参数**



| 参数    | 类型   | 描述                 |
| ------- | ------ | -------------------- |
| code    | int    | 状态码               |
| message | string | 提示信息             |
| data    | object | 处理后的借阅申请信息 |



```json
{
    "code": 200,
    "message": "Success: borrowing applications process",
    "data": null
}
```



```json
{
    "code": 400,
    "message": "Failed: bad request",
    "data":null
}
```



## 处理迟还申请

**接口地址**



| 方法 | 地址                                        |
| ---- | ------------------------------------------- |
| PUT  | /admin/borrowing/late-returns/{borrowingId} |



**请求参数**

| 参数  | 类型 | 是否必需 | 描述               |
| ----- | ---- | -------- | ------------------ |
| agree | int  | 是       | 1为同意，0为不同意 |



```json
{
    "agree": 1
}
```



**响应参数**

```json
{
    "code": 200,
    "message": "Success: borrowing late-returns process",
    "data": null
}
```



```json
{
    "code": 400,
    "message": "Failed: bad request",
    "data":null
}
```



## 查看未归还的读者列表

**接口地址**

| 方法 | 地址                             |
| ---- | -------------------------------- |
| GET  | /admin/borrowing/overdue-readers |



**请求参数**

无



**响应参数**

| 参数               | 类型   | 描述           |
| ------------------ | ------ | -------------- |
| code               | int    | 状态码         |
| message            | string | 提示信息       |
| data               | array  | 未归还读者列表 |
| data[i].userId     | int    | 用户ID         |
| data[i].username   | string | 用户名         |
| data[i].email      | string | 邮箱地址       |
| data[i].borrowDate | string | 借阅日期       |
| data[i].dueDate    | string | 应归还日期     |



```json
{
    "code": 200,
    "message": "Success: borrowing overdue-readers",
    "data": [
        {
            "userId": 123,
            "username": "user1",
            "email": "user1@example.com",
            "borrowDate": "2024-03-21",
            "dueDate": "2024-04-21"
        },
        {
            "userId": 456,
            "username": "user2",
            "email": "user2@example.com",
            "borrowDate": "2024-03-22",
            "dueDate": "2024-04-22"
        }
    ]
}
```



## 检索读者

**接口地址**

| 方法 | 地址                  |
| ---- | --------------------- |
| GET  | /admin/readers/search |



**请求参数**

| 参数     | 类型 | 是否必需 | 描述                         |
| -------- | ---- | -------- | ---------------------------- |
| username | int  | 否       | 用户名                       |
| userId   | int  | 否       | 用户ID                       |
| overdue  | int  | 否       | 是否过期未还，1为是，0为不是 |



```json
{
    "username": "test_user",
    "userId": 123,
    "overdue": 1
}
```



**响应参数**

| 参数                | 类型   | 描述                                       |
| ------------------- | ------ | ------------------------------------------ |
| code                | int    | 状态码                                     |
| message             | string | 提示信息                                   |
| data                | array  | 匹配的读者列表                             |
| data[i].userId      | int    | 用户ID                                     |
| data[i].username    | string | 用户名                                     |
| data[i].email       | string | 用户邮箱                                   |
| data[i].avatar      | blob   | 用户头像图片数据                           |
| data[i].borrowPerms | int    | 借阅权限，1表示正常，0表示不能借阅所有书籍 |



```json
{
    "code": 200,
    "message": "Success: readers search",
    "data": [
        {
            "userId": 123,
            "username": "test1",
            "email": "test1@example.com",
            "avatar": "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/...",
            "borrowPerms": 1
        },
        {
            "userId": 456,
            "username": "test2",
            "email": "test2@example.com",
            "avatar": "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/...",
            "borrowPerms": 0
        }
    ]
}
```



```json
{
    "code": 400,
    "message": "Failed: bad request",
    "data":null
}
```



## 处分读者

**接口地址**

| 方法 | 地址                    |
| ---- | ----------------------- |
| PUT  | /admin/penalty/{userId} |



**请求参数**

| 参数    | 类型   | 是否必需 | 描述         |
| ------- | ------ | -------- | ------------ |
| reason  | string | 是       | 处分理由     |
| endDate | string | 是       | 处分结束日期 |



```json
{
    "reason": "Late returns",
    "endDate": "2024-05-01"
}
```



**响应参数**

| 参数    | 类型   | 描述             |
| ------- | ------ | ---------------- |
| code    | int    | 状态码           |
| message | string | 提示信息         |
| data    | object | 处理后的读者信息 |



```json
{
    "code": 200,
    "message": "Success: penalty",
    "data": null
}
```



```json
{
    "code": 400,
    "message": "Failed: bad request",
    "data":null
}
```



# 读者



## 个人中心信息显示

## 修改个人信息

## 图书目录

## 图书检索

以上同管理员



## 借阅图书

**接口地址**

| 方法 | 地址            |
| ---- | --------------- |
| POST | /user/borrowing |



**请求参数**

| 参数       | 类型   | 是否必需 | 描述       |
| ---------- | ------ | -------- | ---------- |
| userId     | int    | 是       | 借阅用户ID |
| instanceId | int    | 是       | 图书实体ID |
| dueDate    | string | 是       | 应归还日期 |



```json
{
    "userId": 123,
    "instanceId": 456,
    "dueDate": "2024-04-20"
}
```



**响应参数**

| 参数            | 类型   | 描述                     |
| --------------- | ------ | ------------------------ |
| code            | int    | 状态码                   |
| message         | string | 提示信息                 |
| data            | object | 数据                     |
| data.instanceId | int    | 借阅的书实体ID（哪一本） |



```json
{
    "code": 200,
    "message": "Success: borrowing",
    "data": {
        "instanceId":6
    }
}
```



```json
{
    "code": 400,
    "message": "Failed: bad request",
    "data":null
}
```



tip: borrowDate为当前时间

## 预约图书

**接口地址**

| 方法 | 地址                       |
| ---- | -------------------------- |
| PUT  | /user/reservation/{userId} |



**请求参数**

| 参数   | 类型   | 是否必需 | 描述       |
| ------ | ------ | -------- | ---------- |
| userId | int    | 是       | 用户ID     |
| isbn   | string | 是       | 图书ISBN号 |



```json
{
    "userId": 123,
    "isbn": "1234567890123"
}
```



**响应参数**

| 参数    | 类型   | 描述         |
| ------- | ------ | ------------ |
| code    | int    | 状态码       |
| message | string | 提示信息     |
| data    | object | 预约记录信息 |



```json
{
    "code": 200,
    "message": "Success: reservation",
    "data": null
}
```



```json
{
    "code": 400,
    "message": "Failed: bad request",
    "data":null
}
```



## 查看借阅记录

**接口地址**

| 方法 | 地址                    |
| ---- | ----------------------- |
| GET  | /user/borrowing/records |



**请求参数**

无



**响应参数**

| 参数                     | 类型   | 描述                                     |
| ------------------------ | ------ | ---------------------------------------- |
| code                     | int    | 状态码                                   |
| message                  | string | 提示信息                                 |
| data                     | array  | 借阅记录列表                             |
| data[i].borrowingId      | int    | 借阅记录ID                               |
| data[i].userId           | int    | 用户ID                                   |
| data[i].username         | string | 用户名                                   |
| data[i].instanceId       | int    | 图书实体ID                               |
| data[i].isbn             | string | 图书ISBN号                               |
| data[i].borrowDate       | string | 借阅日期                                 |
| data[i].dueDate          | string | 应归还日期                               |
| data[i].borrowAprvStatus | int    | 借阅申请的审批状态，0为未审批，1为已审批 |



```json
{
    "code": 200,
    "message": "Success: borrowing records",
    "data": [
        {
            "borrowingId": 1,
            "instanceId": 123,
            "isbn": "1234567890123",
            "title":"ttt",
            "cover": "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/...",
            "borrowDate": "2024-03-21",
            "dueDate": "2024-04-21",
            "borrowAprvStatus": 1
        },
        {
            "borrowingId": 2,
            "instanceId": 456,
            "isbn": "4567890123456",
            "title":"ttt7",
            "cover": "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/...",
            "borrowDate": "2024-03-22",
            "dueDate": "2024-04-22",
            "borrowAprvStatus": 0
        }
    ]
}
```



## 查看处分记录

**接口地址**

| 方法 | 地址          |
| ---- | ------------- |
| GET  | /user/penalty |



**请求参数**

无



**响应参数**

| 参数                  | 类型   | 描述         |
| --------------------- | ------ | ------------ |
| code                  | int    | 状态码       |
| message               | string | 提示信息     |
| data                  | array  | 处分记录列表 |
| data[i].penaltyId     | int    | 处分记录ID   |
| data[i].adminId       | int    | 管理员ID     |
| data[i].adminUsername | string | 管理员用户名 |
| data[i].userId        | int    | 用户ID       |
| data[i].username      | string | 用户名       |
| data[i].reason        | string | 处分原因     |
| data[i].penaltyDate   | string | 处分日期     |
| data[i].endDate       | string | 处分结束日期 |



```json
{
    "code": 200,
    "message": "Success: penalty",
    "data": [
        {
            "penaltyId": 1,
            "adminUsername": "admin1",
            "reason": "Late return",
            "penaltyDate": "2024-03-21",
            "endDate": "2024-04-21"
        },
        {
            "penaltyId": 2,
            "adminUsername": "admin2",
            "reason": "Damaged book",
            "penaltyDate": "2024-03-22",
            "endDate": "2024-04-22"
        }
    ]
}

```



## 查看预约



**接口地址**

| 方法 | 地址              |
| ---- | ----------------- |
| GET  | /user/reservation |



**请求参数**

无



**响应参数**

| 参数           | 类型   | 描述       |
| -------------- | ------ | ---------- |
| code           | int    | 状态码     |
| message        | string | 提示信息   |
| data           | array  | 预约列表   |
| data[i].isbn   | string | 图书ISBN号 |
| data[i].title  | string | 图书标题   |
| data[i].author | string | 图书作者   |



```json
{
    "code": 200,
    "message": "Success: reservation",
    "data": [
        {
            "isbn": "1234567890123",
            "title": "Book Title 1",
            "cover": "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/...",
        },
        {
            "isbn": "4567890123456",
            "title": "Book Title 2",
            "cover": "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAYABgAAD/...",
        }
    ]
}

```



# 游客



## 图书目录

## 图书检索

以上同管理员
