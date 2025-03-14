
## 🚗 DreamCar

---

Web car search engine that allows users searching and filtering car offers. It also includes user registration functionality. Logged-in users can save offers to their favourite list, create, edit or delete own car offers.<br>
This application automatically initialize the database with data when the application starts. This ensures that the service is ready to use from the moment it is deployed.

---

### 👨‍💻 Set up application

To run this application you need to have installed Docker.

1. Clone this repository
```
git clone https://github.com/BeeBercik/DreamCar.git
```

2. Navigate to the project directory
```
cd DreamCar
```

3. Build and run application using Docker Compose
```
docker-compose up -d --build
```

Visit http://localhost:8080 in your browser to access the app.

💡 The application is configured to initialize database with sample data so the service is ready to use as soon as it starts. 

---

### 🚀 Application Features
1. **User registration & login**
   - Create an account to manage personal car offers.
2. **Add and edit offers**
   - Create your own car offers, including details such as brand, model, price, etc.
3. **Favorites list**
   - Save interesting offers to your favourite list.
4. **Search & filter**
   - Filter offers by brand, price range, year, or other criteria.
5. **Sorting**
   - Sort results based on mileage, price, or other attributes.
6. **SPA architecture**
   - Smooth navigation without full page reloads (thanks to JavaScript + REST).

---

### 🔥 Technologies used in the project
1. **Backend**
   - Java
   - Spring
   - JPA (Hibernate)
2. **Frontend**
   - SPA (Javascript + REST API)
   - HTML
   - CSS
3. **Database**
   - PostgreSQL
4. **Build & run**
   - Maven
   - Docker
5. **Documentation**
   - Javadoc



