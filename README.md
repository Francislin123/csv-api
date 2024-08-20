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

- Cloud Foundry é uma plataforma como serviço (PaaS) de código aberto que permite aos desenvolvedores construir, testar, implantar e escalar aplicativos rapidamente.

- Ela abstrai a complexidade da infraestrutura subjacente, permitindo que os desenvolvedores se concentrem no código em vez de gerenciar servidores ou máquinas virtuais.

- Algumas características importantes do Cloud Foundry incluem:

- 1 = Multilinguagem: Suporta múltiplas linguagens de programação como Java, Node.js, Python, Ruby, Go, etc.

- 2 = Escalabilidade automática: Ajusta automaticamente os recursos com base na carga da aplicação.

- 3 = Desenvolvimento contínuo: Facilita o ciclo de vida de desenvolvimento, permitindo atualizações rápidas e seguras dos aplicativos.

- 4 = Portabilidade: Pode ser executado em diversos ambientes de nuvem, como AWS, Azure, Google Cloud ou em data centers privados.

- É amplamente utilizado para aplicativos empresariais em ambientes de produção, oferecendo uma maneira eficiente de gerenciar e escalar microsserviços.
