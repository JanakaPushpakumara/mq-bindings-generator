# MQ JNDI Bindings Generator

## Description
This Maven project generates a `.bindings` file containing IBM MQ queue and topic connection factories and destinations. 
It is particularly useful for macOS users, as the IBM MQ Explorer is not supported, 
and it cannot run natively on macOS. By providing the appropriate connection factory and metadata, 
this tool simplifies the creation of `.bindings` files. 

## Prerequisites
- **Java Development Kit (JDK 11 or higher)** installed.
- **Apache Maven** installed.

## Setup Instructions

### 1. Clone the Repository
Clone the repository or download the project files to your local machine.

### 2. Install Required JAR Files
Install the following JAR files into your local Maven repository:

#### Command to Install `fscontext.jar`:
```bash
mvn install:install-file \
    -Dfile=/path/to/fscontext.jar \
    -DgroupId=com.sun.jndi \
    -DartifactId=fscontext \
    -Dversion=1.2.1 \
    -Dpackaging=jar \
    -DgeneratePom=true 
```
#### Command to Install `providerutil.jar`:
```bash
mvn install:install-file \
    -Dfile=/path/to/providerutil.jar \
    -DgroupId=com.sun.jndi \
    -DartifactId=providerutil \
    -Dversion=1.2.1 \
    -Dpackaging=jar \
    -DgeneratePom=true
```
#### Note
These JAR files can be found in the <IBM_MQ_HOME>/java/lib/ directory.
If you are using an IBM MQ Docker container, you can locate these libraries inside the /opt/mqm/java/lib/ directory. 
Use the docker cp command to copy the JAR files from the container to your local machine.

### 3. Build and Run the Project
   1. Navigate to the project directory in your terminal.
   2. Run the following Maven command to clean, compile, and execute the project:
   3. ```bash 
      mvn clean compile exec:java \
      -Dexec.mainClass="com.example.mq.MQBindingsGenerator" \
      -Djndi.provider.url=file:/your/custom/path
      ```

### 4. Output
The `.bindings` file will be created in the directory specified by the `Context.PROVIDER_URL` property, 
which you set using the `-Djndi.provider.url` option in the Maven build command.

