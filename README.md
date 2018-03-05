    1. REPOSITORY

        - https://github.com/marcusviniciusfs/decoraws;

    2. BUILD

        - Execute mvn install to build it;
        - The distribution war file will be provided in <PROJECT>/target/decoraws-<VERSION>.war directory;

    3. INSTALLATION REQUIREMENTS

        - Maven
        - Linux or Windows;
        - Java 1.8 or superior (Oracle ou OpenJDK);
        - MongoDB
        - Wildfly 10 Application Server
        - Http Client(i.e: Postman - https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop)

    4. INSTALLATION

        - Configure the mongoDB connection to Wildfly application server (http://mongodb.github.io/mongo-java-driver/3.3/driver/reference/connecting/jndi/);
            a. Driver version mongo-java-driver-3.4.0.jar
            b. By default the application will bind at localhost:27017;
        - Deploy the decoraws-<VERSION>.war file

    5. INITIALIZATION

        - For Basic Authentication a root user has been provided:
            a. username: root@decora.com
            b. password: password
            c. Or Set up the <Authorization> KEY at http header client with VALUE: <Basic cm9vdEBkZWNvcmEuY29tOnBhc3N3b3Jk>;

        - Propritary KEY <X-Roles> provided for user roles validates with acceptable VALUES <administrador> and <user>

        - By default the services will be available at:

          * HTTP CRUD Services (POST, GET (Retrieve All)):  127.0.0.1:8080/decoraws-1.0-SNAPSHOT/service/user/
          * HTTP CRUD Services (GET, DELETE ):              127.0.0.1:8080/decoraws-1.0-SNAPSHOT/service/user/{$EMAIL}
          * HTTP CRUD Services (PUT ):                      127.0.0.1:8080/decoraws-1.0-SNAPSHOT/service/user/ - (Passing a SystemUser entity at body request)

          * Search                                          127.0.0.1:8080/decoraws-1.0-SNAPSHOT/service/user/findby?$PARAM=$VALUE
            - QueryParams: name, email, phone, state, town, orderBy, skip, limit;

          * Login (Http header: Basic Authorization):       127.0.0.1:8080/decoraws-1.0-SNAPSHOT/login/