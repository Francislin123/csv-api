# CSV API

- This application is responsible for reading a CSV file and saving the information in the H2 database

# Technologies

- Java 17
- H2
- Lombok
- Apache Poi 5.2.3
- Springframework Boot 3.2.2

### To run the application just follow the steps below.

- 1 - Enter the folder where the project jar is
- 2 - Execute the following command by cmd

- java -jar write-csv-0.0.1-SNAPSHOT.jar --spring.config.location = file: C: /Config/application.yml
- OBS: This start command in the application, it finds the configurations of the configuration file of the database
- among other configurations (application.yml)
- -Dspring.profiles.active=local

### Documentation for testing api
- H2 Database URL(http://localhost:8080/api/v1/h2/login.jsp?jsessionid=a1198c3d53a23d032976c4314f888286) pass:sa
- To test the listing/statistics
- The xlsx/csv file is in the root of the project (moviestes.xlsx)
- Api URI(http://localhost:8080/api/v1/assets/csv/statics) 
- curl --location 'http://localhost:8080/api/v1/csv/statics' \--header 'Content-Type: application/json'

### Cloud Foundry Solution Architecture
![Captura de Tela 2019-05-12 às 15 18 49](https://res.cloudinary.com/duep7y7ve/image/upload/v1724192539/Captura_de_Tela_2024-08-20_a%CC%80s_18.39.27_phexlo.png)

- Cloud Foundry is an open-source Platform-as-a-Service (PaaS) that allows developers to quickly build, test, deploy, and scale applications.

- It abstracts the complexity of the underlying infrastructure, allowing developers to focus on code rather than managing servers or virtual machines.

- Some important features of Cloud Foundry include:

- 1 = Multilingual: Supports multiple programming languages such as Java, Node.js, Python, Ruby, Go, etc.

- 2 = Autoscaling: Automatically adjusts resources based on application load.

- 3 = Continuous development: Facilitates the development lifecycle, enabling fast and secure application updates.

- 4 = Portability: Can be run in different cloud environments, such as AWS, Azure, Google Cloud or in private data centers.

- It is widely used for enterprise applications in production environments, offering an efficient way to manage and scale microservices.
