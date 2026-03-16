# 🌐 Lumine Translations

![Next.js](https://img.shields.io/badge/Frontend-Next.js-black?logo=next.js)
![React](https://img.shields.io/badge/UI-React-blue?logo=react)
![Spring Boot](https://img.shields.io/badge/Backend-Spring%20Boot-green?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-blue?logo=postgresql)
![AWS S3](https://img.shields.io/badge/Storage-AWS%20S3-orange?logo=amazonaws)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

A **modern web application** for freelance translators to **generate translation quotes** for English and French documents.

The platform allows translators to upload files, analyze translation requirements, and generate accurate quotes efficiently.

---

# ✨ Features

* 📄 Upload translation documents
* 💰 Automatic translation quote generation
* 🌍 English ↔ French translation support
* ☁️ Secure cloud file storage
* ⚡ Fast modern frontend
* 🔐 Secure backend API
* 🧠 Scalable architecture

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

```bash
cd front
npm install
npm run dev
```

Runs the frontend locally at:

```
http://localhost:3000
```

---

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
* 📊 Quote analytics dashboard
* 📑 Support for additional document formats
* 🌎 More language pairs
* 💳 Payment integration for clients


