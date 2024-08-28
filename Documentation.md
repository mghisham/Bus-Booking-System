# Bus Booking System - Documentation with Screenshots

## Overview
This document provides a visual guide to the Bus Booking System, including screenshots of Postman requests and the database tables.

## Postman Requests

### 1. **Authenticate**
- **Endpoint**: `POST /booking-system/authenticate`
- **Description**: This request allows a user to authenticate by providing a phone number and password.
- **Request Body**:
    ```json
    {
      "phone": "1234567890",
      "password": "password"
    }
    ```
- **Screenshot**:

  ![Authenticate Request](images/post_api_authenticate_success.png)
  ![Authenticate Request](images/jwt_parsed.png)
  ![Authenticate Request](images/post_api_authenticate_error_password.png)
  ![Authenticate Request](images/post_api_authenticate_error_user.png)

### 2. **Get All Buses**
- **Endpoint**: `GET /booking-system/bus`
- **Description**: Retrieves a list of all available buses.
- **Screenshot**:

  ![Get All Buses](images/get_api_bus_success.png)

### 3. **Search Buses**
- **Endpoint**: `GET /booking-system/search`
- **Description**: Retrieves a list of all available buses between a source and destination.
- **Screenshot**:

  ![Search Buses](images/get_api_search_success.png)
  ![Search Buses](images/get_api_search_error_token.png)

### 4. **Add a New Bus**
- **Endpoint**: `POST /booking-system/bus`
- **Description**: Adds a new bus to the system.
- **Request Body**:
    ```json
  {
      "busNumber" : "86",
      "busName" : "Romford Station",
      "travelCompany" : "TFL",
      "capacity" : 35,
      "availableSeats" : 20,
      "source" : "Stratford",
      "destination" : "Romford"
  }
    ```
- **Screenshot**:

  ![Add New Bus](images/post_api_bus_success.png)
  ![Update Bus](images/put_api_bus_success.png)
  ![Update Bus](images/put_api_bus_error.png)

### 4. **Book a Seat**
- **Endpoint**: `POST /booking-system/book`
- **Description**: Books a seat on a selected bus.
- **Request Body**:
    ```json
  {
      "bookingNumber": 2,
      "busNumber": "N86",
      "bookingDate": "2024-08-28",
      "source": "Romford",
      "destination": "Stratford",
      "totalSeats": 20,
      "bookingStatus": "CONFIRMED"
  }
    ```
- **Screenshot**:

  ![Book a Seat](images/post_api_book_success.png)
  ![Book a Seat](images/post_api_book_error_no_seats.png)
  ![Book a Seat](images/post_api_book_error_not_found.png)
  ![Book a Seat](images/post_api_book_error_no_token.png)

## Database Table Screenshots

### 1. **Customers Table**
- **Description**: This table stores all user details including phone number, name, and password.
- **Screenshot**:

  ![Customers Table](images/db_table_customer.png)

### 2. **Buses Table**
- **Description**: This table contains all bus information, such as bus number, name, travel company, capacity, and route details.
- **Screenshot**:

  ![Buses Table](images/db_table_bus.png)

### 3. **Bookings Table**
- **Description**: This table records all the bookings made by users, including bus number, booking date, source, destination, and seat details.
- **Screenshot**:

  ![Bookings Table](images/db_table_booking.png)
