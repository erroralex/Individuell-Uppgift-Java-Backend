# Individuell Uppgift – Java Backend

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![H2 Database](https://img.shields.io/badge/Databas-H2-003B57?style=for-the-badge&logo=h2&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-Basic_Auth-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![Postman](https://img.shields.io/badge/Testad_med-Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)

Ett RESTful API byggt med Java 21 och Spring Boot 3.x som en del av en individuell inlämningsuppgift. API:et simulerar ett medlemssystem för **Wigellkoncernen**, med stöd för två användarroller – administratörer och medlemmar – samt grundläggande CRUD-operationer, relationshantering och säkerhet via Spring Security.

---

## 🧩 Funktioner

### Adminendpoints – `/admin/members`

| Metod    | Endpoint                  | Beskrivning                                  |
|----------|---------------------------|----------------------------------------------|
| `GET`    | `/admin/members`          | Hämtar samtliga medlemmar med all data       |
| `GET`    | `/admin/members/{id}`     | Hämtar en specifik medlem med all data       |
| `POST`   | `/admin/members`          | Lägger till en ny medlem i databasen         |
| `PUT`    | `/admin/members/{id}`     | Ersätter all data för en vald medlem         |
| `PATCH`  | `/admin/members/{id}`     | Uppdaterar viss data för en vald medlem      |
| `DELETE` | `/admin/members/{id}`     | Tar bort en angiven medlem ur databasen      |

### Medlemsendpoints – `/mypages/members`

| Metod  | Endpoint                    | Beskrivning                                                              |
|--------|-----------------------------|--------------------------------------------------------------------------|
| `GET`  | `/mypages/members`          | Visar `firstName`, `lastName`, `address`, `email` och `phone` för alla   |
| `PUT`  | `/mypages/members/{id}`     | Uppdaterar data för den inloggade medlemmen                              |

---

## 🗄️ Datamodell

### Member

| Fält          | Typ      | Begränsningar              |
|---------------|----------|----------------------------|
| `id`          | Long     | Primärnyckel, autogenererad |
| `firstName`   | String   | Max 50 tecken, ej null     |
| `lastName`    | String   | Max 50 tecken, ej null     |
| `email`       | String   | Max 100 tecken, ej null    |
| `phone`       | String   | Max 20 tecken, **nullable** |
| `dateOfBirth` | Date     | Ej null, **unikt**         |
| `address`     | Address  | Många-till-en-relation     |

### Address

| Fält         | Typ    | Begränsningar           |
|--------------|--------|-------------------------|
| `id`         | Long   | Primärnyckel, autogenererad |
| `street`     | String | Max 100 tecken, ej null |
| `postalCode` | String | Max 10 tecken, ej null  |
| `city`       | String | Max 50 tecken, ej null  |

> En medlem kan endast ha **en** adress, men en adress kan delas av **flera** medlemmar (`@ManyToOne`).

---

## 🔐 Säkerhet

API:et är skyddat med **HTTP Basic Authentication** via Spring Security.

| Roll     | Åtkomst                             |
|----------|-------------------------------------|
| `ADMIN`  | `/admin/**`                         |
| `MEMBER` | `/mypages/**`                       |

Autentiseringsuppgifter skickas med som `Authorization: Basic ...`-header i Postman.

---

## 🚀 Kom igång

### Krav

* Java 21
* Maven

### Starta applikationen

```bash
git clone https://github.com/erroralex/Individuell-Uppgift-Java-Backend.git
cd Individuell-Uppgift-Java-Backend
mvn spring-boot:run
```

Applikationen startar på `http://localhost:8080`.

### H2-konsolen

H2:s inbyggda webbkonsol nås via:

```
http://localhost:8080/h2-console
```

Anslut med JDBC-URL:en som är konfigurerad i `application.properties`.

### Förpopulerad data

Vid uppstart skapas **5 medlemmar** automatiskt i databasen via dataladdning i `data.sql` eller motsvarande konfiguration.

---

## 🧪 Testning med Postman

All kommunikation med API:et sker via **Postman**. Importera samlingen eller skapa requests manuellt mot de endpoints som listas ovan.

**Exempel – hämta alla medlemmar som admin:**

```
GET http://localhost:8080/admin/members
Authorization: Basic YWRtaW46YWRtaW4=
```

**Exempel – skapa ny medlem:**

```
POST http://localhost:8080/admin/members
Content-Type: application/json

{
  "firstName": "Anna",
  "lastName": "Svensson",
  "email": "anna@example.com",
  "phone": "070-123 45 67",
  "dateOfBirth": "1990-06-15",
  "address": {
    "street": "Storgatan 1",
    "postalCode": "12345",
    "city": "Stockholm"
  }
}
```

---

## ⚠️ Felhantering

Samtliga endpoints returnerar lämpliga HTTP-statuskoder och felmeddelanden:

| Statuskod | Betydelse                                         |
|-----------|---------------------------------------------------|
| `200 OK`  | Lyckad hämtning eller uppdatering                 |
| `201 Created` | Ny resurs skapad                              |
| `204 No Content` | Lyckad radering                           |
| `400 Bad Request` | Ogiltigt eller saknat indata              |
| `401 Unauthorized` | Saknad eller felaktig autentisering      |
| `403 Forbidden` | Otillräckliga rättigheter                  |
| `404 Not Found` | Angiven resurs finns inte                  |
| `409 Conflict` | Konflikt, t.ex. duplicerat `dateOfBirth`    |

---

## 🛠️ Teknisk stack

* **Java 21** – Virtual Threads & moderna språkfunktioner
* **Spring Boot 3.x** – Ramverk för webben och dependency injection
* **Spring Data JPA / Hibernate** – ORM och databasabstraktion
* **H2 Database** – Inbäddad in-memory-databas med konsol
* **Spring Security** – HTTP Basic Authentication och rollbaserad åtkomstkontroll
* **Maven** – Byggsystem och beroendehantering

---

## 📜 Licens

Distribueras under **MIT-licensen**. Fri för personligt och kommersiellt bruk.

---

<p align="center">
  <b>Utvecklad av</b><br>
  <img src="src/main/resources/alx_logo.png" width="120" alt="Alexander Nilsson Logo"><br>  
  Copyright © 2026 Alexander Nilsson
</p>
