# Utility International Backend System

## Project Overview

Utility International is a Spring Boot backend system designed to provide customers with a self-service utility management platform.

The system enables customers to:
- View current and historical bills
- Pay utility bills
- Dispute incorrect bills
- Change utility tariff plans
- Receive future notifications and reminders

The backend system also supports:
- Role-based authentication and authorization
- Call center dispute management
- Internal payment processing
- Simulated notification handling

---

# Technology Stack

## Backend Framework
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security

## Database
- MySQL

## Documentation
- Swagger / OpenAPI

## Build Tool
- Maven

## Java Version
- Java 25

---

# Project Architecture

The application follows a layered Spring Boot architecture.

## Layers

### 1. Controller Layer
Handles incoming HTTP requests and exposes REST APIs.

### 2. Service Layer
Contains business logic and application workflows.

### 3. Repository Layer
Handles database access using Spring Data JPA repositories.

### 4. Entity Layer
Represents database models and relationships.

### 5. Utility Processing Layer
Handles:
- Internal bill calculations
- Payment processing workflows
- Simulated notification operations

---

# Core Modules

## Authentication Module
Responsible for:
- Login
- User authentication
- Role-based access control

## Customer Module
Responsible for:
- Customer operations
- Customer profile management
- Customer account handling

## Billing Module
Responsible for:
- Bill retrieval
- Bill management
- Bill calculations
- Bill status management

## Payment Module
Responsible for:
- Payment processing
- Payment validation
- Payment status updates

## Dispute Module
Responsible for:
- Bill dispute submission
- Dispute tracking
- Dispute resolution workflows

## Tariff Module
Responsible for:
- Utility plan management
- Tariff upgrades/downgrades

## Notification Module
Responsible for:
- Simulated SMS notifications
- Simulated email notifications
- Payment reminders
- Future notification expansion

---

# Package Structure

```text
com.utilityinternational.utility_backend

├── config
├── controller
├── service
│   └── impl
├── repository
├── entity
├── enums
├── dto
├── security
├── exception
├── util
└── notification