## 📌 API CRUD Operations

This project is a RESTful API built using Flask that performs CRUD (Create, Read, Update, Delete) operations on data.

It demonstrates:
- REST API design
- HTTP methods (GET, POST, PUT, DELETE)
- Response body validation or user data validation

## 🌐 API Endpoints

### 1. Create Data
POST /create

### 2. Get All Data
GET /read

### 3. Update Data
PUT /update/{id}

### 4. Delete Data
DELETE /delete/{id}

## 📥 Example Request

POST /create
{
  "name": "Mahadeva",
  "age": 25
}

## 📤 Response
{
  "message": "Data created successfully"
}

## 🛠 Tech Stack
- Java
- REST API
- Test NG
- DataProvidors
- Json-path
