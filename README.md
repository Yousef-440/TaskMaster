# Secure Task Management API

This is a secure and RESTful Task Management API built using **Spring Boot**, **Spring Security**, and **JWT**.  
It allows users to register, log in, manage their tasks, and perform full CRUD operations — all protected by role-based access and ownership verification.

## Authentication & Authorization

- User registration and login.
- Password update requires confirmation of the current password.
- All sensitive routes are protected using **JWT-based authentication**.

## Features

- User Registration
- Login with JWT
- Update password (with old password verification)
- Delete user (only if you are the owner)
- Create, Read, Update, Delete Tasks
- Each user has their own tasks (One-to-Many relationship)
- Security checks before modifying or deleting any task

## Tech Stack

- Java
- Spring Boot
- Spring Security
- JWT (JSON Web Tokens)
- JPA / Hibernate
- Lombok
- PostgreSQL
- RESTful API


## Security Highlights

- Uses JWT for stateless authentication
- Spring Security configuration ensures only the **task owner** can:
- Add, update, or delete a task
- Delete their account
- Password change logic enforces entering the **old password first**
