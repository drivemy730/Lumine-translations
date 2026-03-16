# 🌐 Lumine Translations

![HTML5](https://img.shields.io/badge/Frontend-HTML5-orange?logo=html5)
![CSS3](https://img.shields.io/badge/Styling-CSS3-blue?logo=css3)
![JavaScript](https://img.shields.io/badge/Scripting-JavaScript-yellow?logo=javascript)
![Spring Boot](https://img.shields.io/badge/Backend-Spring%20Boot-green?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-blue?logo=postgresql)
![AWS S3](https://img.shields.io/badge/Storage-AWS%20S3-orange?logo=amazonaws)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

**Lumine Translations** is a platform for freelance translators to **generate translation quotes** for English and French documents.  
The backend is fully functional (Spring Boot + PostgreSQL + AES + AWS S3), while the frontend is currently a **basic HTML/CSS/JS placeholder**.


---

# ✨ Features

- 📄 Upload and parse translation documents (backend ready)
- 📄 Return qoutes for customers (backend ready)
- 🌍 English ↔ French translation support
- ⚡ Backend fully functional
- 🖥️ Frontend currently basic HTML/CSS/JS (non-functional)


---

# 🏗️ Architecture

```text
Frontend (Next.js / React)
        │
        │ REST API
        ▼
Backend (Spring Boot)
        │
        │
        ├── PostgreSQL (data storage)
        └── AWS S3 (document storage)
```

---

# 🛠️ Tech Stack

## Frontend

* ⚛️ React
* ▲ Next.js
* 🎨 Modern UI components

## Backend

* ☕ Spring Boot
* 🔗 REST APIs

## Database

* 🐘 PostgreSQL

## Cloud

* ☁️ AWS S3

## Security

* 🔐 AES Encryption utilities

---

# 📂 Project Structure

```
Lumine-translations
│
├── front       # Next.js / React frontend
├── back        # Spring Boot backend
├── database    # PostgreSQL configuration
├── aes         # Encryption utilities
└── README.md
```

---

# 🚀 Getting Started

## 1️⃣ Clone the repository

```bash
git clone https://github.com/drivemy730/Lumine-translations.git
cd Lumine-translations
```

---

## 2️⃣ Frontend Setup

The frontend is currently a static placeholder

Open front/index.html in a browser to view the UI


## 3️⃣ Backend Setup

```bash
cd back
./mvnw spring-boot:run
```

Backend API will run at:

```
http://localhost:8080
```



# 🧭 Future Improvements

* 👤 Translator accounts
* ⚛️ Replace frontend with React / Next.js functional UI
* 📊 Quote analytics dashboard
* 📑 Support for additional document formats
* 🌎 More language pairs
* 💳 Payment integration for clients
* ☁️ Secure cloud file storage
* 🔐 Secure backend API
* 🧠 Scalable architecture



