# chargeback-processor
This code consists in a chargeback processor using CSV files.
The Transaction_sample_data.csv needs to be included inside the dir=src/main/resources, and it will be read at start up 
moment, processed and insert in a H2 database Table.

You will find two Rest endpoints, one to retrieve all transactions inside the database and another to upload one CSV file.
More information about the endpoints were included on the Swagger interface: [http://localhost:8080/swagger-ui/index.html]
To perform locally the system, you need to follow the steps on the final of this document.

I created a simple DTO to Transactions, on its endpoint, just to don't bring the whole structure.

Notice that for the informed examples, I had the columns predefined, but for unknown documents, could be necessary adjust 
a few lines of the code, OR, you can insert values on your POST request, to define the CSV column for "Transaction_Date" 
and "Transaction_Amount", furthermore, you also need to specify the Date format. 

Below you will find the parameters names, and I also attached "postman.jpg" with a Postman example to make it easier.
This request, when ok, will return a structure with total lines in the csv file, and the matches found in the Transaction
table. It also will return the kind of unmatched. Again the structure could be find on the swagger docs.

1 - file: [Your CSV file here]

2 - dateRowNumber: [Date column int number]

3 - dateFormat: [Date format to parse]

4 - amountRowNumber: [Amount column int number]

### Software Engineer
- Deorgenes Xavier Junior

### Technology stack:
- Programming language: Java [17]
- Framework: Springboot (3.0.6)
- Webserver: Embedded TomCat
- JDK: jdk-17

### Perform Locally

1. Enter inside chargeback-processor package
2. Open a terminal (Git bash for example)
3. perform this command: mvn clean spring-boot:run OR
4. perform this command: java -jar target/processor-0.0.1-SNAPSHOT.jar
5. After running is possible to access the endpoints by [http://localhost:8080/swagger-ui/index.html]
6. To stop the system is just a simple ctrl^c