# Hotel Booking System

A Spring Boot web application for hotel booking management, featuring user registration, hotel browsing, room booking, and an admin dashboard for hotel and user management.

## Features

- **User Registration & Login**
- **Browse Hotels**
- **Book Rooms**
- **View My Bookings**
- **Admin Dashboard**
  - Add, edit, and delete hotels
  - Edit admin profile
  - View all hotels
- **Input Validation**
- **Role-based Access (Admin/User)**
- **Session Management**

## Technologies Used

- Java 17+
- Spring Boot
- Spring Data JPA
- Thymeleaf
- Oracle
- Maven

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven

### Setup

1. **Clone the repository**
    ```bash
    git clone https://github.com/yourusername/hotel-booking-system.git
    cd hotel-booking-system
    ```

2. **Build the project**
    ```bash
    mvn clean install
    ```

3. **Run the application**
    ```bash
    mvn spring-boot:run
    ```

4. **Access the app**
    - Open [http://localhost:8081](http://localhost:8081) in your browser.



### Admin Access

- Register a user and set their role to `ADMIN` in the database to access `/admin`.
- Admin dashboard: `/admin`

## Project Structure

```
src/
  main/
    java/
      com/example/hotelbooking/
        model/         # Entities (User, Hotel, Booking)
        repository/    # Spring Data JPA Repositories
        service/       # Business Logic
        controller/    # Web Controllers
    resources/
      templates/       # Thymeleaf HTML templates
      static/css/      # CSS files
      application.properties
```


Pull requests are welcome! For major changes, please open an issue first to discuss what you would like to change.

## License

[MIT](LICENSE)
