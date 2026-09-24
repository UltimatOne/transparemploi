Backend — TransparEmploi  
Java 21 • Spring Boot 4.1.0 • PostgreSQL • JWT + Refresh Token

Présentation

Backend de l’application **TransparEmploi**, développé en **Java 21** avec **Spring Boot 4.1.0**.  
Il fournit une API REST moderne, sécurisée, avec :

- Authentification JWT  
- Refresh token persistant  
- Gestion des rôles (USER / ADMIN)  
- Endpoints protégés  
- CRUD utilisateurs  
- CRUD offres d’emploi  
- Architecture propre et modulaire  

Ce backend constitue le **MVP complet** de l’application.

---

Technologies

- **Java 21 (LTS)**
- **Spring Boot 4.1.0**
- **Spring Security 6**
- **JWT (access token)**
- **Refresh Token (persisté en base)**
- **Hibernate / JPA**
- **PostgreSQL 15+**
- **Maven 3.11+**

---

Installation & Lancement

1. Cloner le projet

bash
git clone https://github.com/UltimatOne/transparemploi.git
cd transparemploi/backend

2. Configurer PostgreSQL
Voir le fichier :
src/main/resources/application-local.properties.example

3. Lancer le backend
./mvnw spring-boot:run

Backend disponible sur :
http://localhost:8080


Authentification & Sécurité
JWT Access Token
Durée courte (ex : 15 minutes).
Contient :

email

rôle (USER / ADMIN)

Refresh Token
Durée longue (ex : 7 jours).
Stocké en base PostgreSQL.
Permet de régénérer un JWT sans se reconnecter.

Logout
Supprime le refresh token → l’utilisateur doit se reconnecter.

/me
Renvoie l’utilisateur connecté à partir du JWT.

Rôles & Permissions
Spring Security protège automatiquement :

/api/users/**        → USER ou ADMIN selon les endpoints
/api/users/{id}/role → ADMIN uniquement

Et via annotations :
@PreAuthorize("hasRole('ADMIN')")
@PreAuthorize("hasRole('USER')")


Endpoints
AUTH — /api/auth

Méthode	     Endpoint	                Description
POST	/api/auth/register	  Inscription
POST	/api/auth/login	      Connexion (JWT + refresh token)
POST	/api/auth/refresh	  Renouvelle le JWT
POST	/api/auth/logout	  Supprime le refresh token
GET	/api/auth/me	          Renvoie l’utilisateur connecté


JOB OFFERS — /api/offers

Méthode	  Endpoint	                Description
GET	/api/offers	              Liste toutes les offres
POST	/api/offers	          Crée une nouvelle offre
GET	/api/offers/transparent	  Liste les offres transparentes


USERS — /api/users


Méthode	   Endpoint	                Description
GET	/api/users	              Liste tous les utilisateurs
GET	/api/users/{id}	          Récupère un utilisateur
PUT	/api/users/{id}	          Met à jour un utilisateur
PUT	/api/users/{id}/role	  ADMIN : met à jour le rôle
DELETE	/api/users/{id}	      Supprime un utilisateur


Structure du projet

backend/
 ├── HELP.md
 ├── mvnw
 ├── mvnw.cmd
 ├── pom.xml
 ├── postman
 │   └── TransparEmploi.postman_collection.json
 └── src
     ├── main
     │   ├── java
     │   │   └── com/transparemploi/backend
     │   │       ├── BackendApplication.java
     │   │       ├── controller
     │   │       │   ├── AuthController.java
     │   │       │   ├── JobOfferController.java
     │   │       │   ├── LogoutController.java
     │   │       │   ├── MeController.java
     │   │       │   ├── RefreshTokenController.java
     │   │       │   └── UserController.java
     │   │       ├── dto
     │   │       │   ├── AuthResponse.java
     │   │       │   ├── AuthResponseDTO.java
     │   │       │   ├── ErrorResponse.java
     │   │       │   ├── JobOfferRequest.java
     │   │       │   ├── JobOfferResponse.java
     │   │       │   ├── LoginRequest.java
     │   │       │   ├── RefreshTokenRequestDTO.java
     │   │       │   ├── RegisterRequest.java
     │   │       │   ├── RoleUpdateRequest.java
     │   │       │   ├── UserResponse.java
     │   │       │   └── UserUpdateRequest.java
     │   │       ├── exception
     │   │       │   └── GlobalExceptionHandler.java
     │   │       ├── mapper
     │   │       │   ├── JobOfferMapper.java
     │   │       │   └── UserMapper.java
     │   │       ├── model
     │   │       │   ├── JobOffer.java
     │   │       │   ├── RefreshToken.java
     │   │       │   └── User.java
     │   │       ├── repository
     │   │       │   ├── JobOfferRepository.java
     │   │       │   ├── RefreshTokenRepository.java
     │   │       │   └── UserRepository.java
     │   │       ├── security
     │   │       │   ├── JwtAuthenticationFilter.java
     │   │       │   ├── JwtService.java
     │   │       │   └── SecurityConfig.java
     │   │       └── service
     │   │           ├── AuthService.java
     │   │           ├── JobOfferService.java
     │   │           ├── RefreshTokenService.java
     │   │           └── UserService.java
     │   └── resources
     │       ├── application.properties
     │       ├── application-local.properties
     │       ├── application-prod.properties
     │       ├── static/
     │       └── templates/
     └── test
         └── java/com/transparemploi/backend
             └── BackendApplicationTests.java

Tests avec Postman

Une collection Postman est fournie dans :
/backend/postman/TransparEmploi.postman_collection.json

Elle contient :

Auth
Register

Login

Refresh

Logout

Me

Offers
Get all

Create

Get transparent

Users
Get all

Get by ID

Update

Update role (ADMIN)

Delete

Variables Postman
{{baseUrl}} = http://localhost:8080
{{token}}
{{refreshToken}}

Exemple de réponse : Login
{
  "token": "jwt...",
  "refreshToken": "uuid...",
  "refreshTokenExpiry": 1723456789000,
  "role": "USER"
}

Exemple de réponse : /me
{
  "id": 12,
  "email": "jj@test.com",
  "role": "USER"
}


Statut du backend

Fonctionnalité	          Statut
Authentification	        ✔
JWT	                        ✔
Refresh Token	            ✔
Logout	                    ✔
/me	                        ✔
CRUD Users	                ✔
CRUD Offers	                ✔
Rôles & Permissions	        ✔
Documentation	            ✔
Collection Postman	        ✔

Prochaines étapes

- Module Jobs avancé (**analyse, filtres, pagination, score de transparence**)  
- Module Companies (**profil entreprise + historique des annonces**)  
- Dashboard Admin (**suivi des scores, statistiques globales, gestion utilisateurs**)  
- Tests unitaires (JUnit)  
- Déploiement (Railway / Render / VPS)
