# API memberservice

Uppgift under utbildningen Systemutveckling Java till kursen Backend. Uppgiften gick ut på att skapa ett api 
som kan hantera medlemmar. Olika användare har olika behöright med hur medlemmar kan hanteras.

Huvudsyftet med uppgiften är att träna på:
- **REST API** Arbeta och förstå hur man skapar ett REST API
- **Spring Boot** Använda Spring Boot för att bygga API
- **Spring Boot Security** Använda Spring Boot Security för säkerhet och hantera roller med olika behörigheter

## ✅ Funktionalitet

Admin kan utföra följande aktiviteter

- Lista medlemmar GET ”/admin/members” – All data på respektive medlem hämtas och visas
  
- Hämta enskild medlem GET ”/admin/members/{id}” – All data på vald medlem hämtas och visas
  
- Uppdatera uppgifter PUT ”/admin/members/{id}” – Samtlig data för vald medlem uppdateras
  
- Uppdatera uppgifter PATCH ”/admin/members/{id}” – Viss data för vald medlem uppdateras
  
- Lägga till medlem POST ”/admin/members” – Ny medlem ska läggas till i databasen
  
- Ta bort medlem DELETE ”/admin/members/{id}” – Angiven medlem ska raderas från databasen

Medlemmar kan utföra följande aktiviteter
    
- Lista medlemmar GET ”/mypages/members” – firstName, lastName, addressCreateDto, email och phone på
  samtliga medlemmar ska hämtas och visas

- Hämta enskild medlem GET "/mypages/members/{id} - Samtlig data för den inloggade datan ska hämtas och visas
  
- Uppdatera uppgifter PUT ”/mypages/members/{id}” – Data för den inloggade medlemmen ska uppdateras

## ⭐ Kom igång

Använder H2 lokalt mot http://localhost:8080. Det finns redan hårdkodade användare, deras uppgifter kan hittas i config\DataInitializer.
Du kan använda Postman för att skicka anrop mot API:et.

## ✍️ Författare

Uppgift skapad för utbildningssyfte av Sara Nilsson. 

LinkedIn: https://www.linkedin.com/in/sara-nilsson-774402220/

Github: https://github.com/Skrapp
