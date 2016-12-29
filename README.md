# Coding problem
## Write a program that can be used to download data from multiple sources and protocols to local disk.
The list of sources will be given as input in the form of URLs
(e.g. http://my.file.com/file, ftp://other.file.com/other, sftp://and.also.this/ending etc.)
## The program should download all the sources to a configurable location (file name should be uniquely determined from the URL) and then exit.

### In your code, please consider:
1. The program should extensible to support different protocols
2. Some sources might be very big (more than memory)
3. Some sources might be very slow, while others might be fast
4. Some sources might fail in the middle of download
5. We don’t want to have partial data in the final location in any case
6. We want to see the progress while the file is downloading
7. It should be able to track how much disk space left (unused) and if the process can continue to download or not

## Build and Run
### Get code from
```
git clone https://github.com/SKGitHub2017/hometest
```
### Build
```
mvn clean install
```
### Run
```java
1.Run with default config server.port=8888 and download.file.location=C:\\download

java -jar .\target\download-1.0.0.jar

curl -X POST -H "Content-Type: application/json" -d "{\"urls\" : \"http://weknowyourdreams.com/images/sea/sea-01.jpg, http://weknowyourdreams.com/images/sea/sea-02.jpg\"}" "http://localhost:8888/download"

2.Run with override server.port=8080 and download.file.location=C:\\download.1

java -jar .\target\download-1.0.0.jar --server.port=8080 --download.file.location=C:\\download.1

curl -X POST -H "Content-Type: application/json" -d "{\"urls\" : \"http://weknowyourdreams.com/images/sea/sea-01.jpg, http://weknowyourdreams.com/images/sea/sea-02.jpg\"}" "http://localhost:8080/download"

3.Run with external YAML property configuration file (In this case we use YAML property file)
The relevant part is the application.yml file which goes to C:\\config\application.yml. The contents could look like this:

server:
     port: 8081

download :
    file :
      location : C:\\downloads

java -jar .\target\download-1.0.0.jar --spring.config.location=C:\\config\application.yml

curl -X POST -H "Content-Type: application/json" -d "{\"urls\" : \"http://weknowyourdreams.com/images/sea/sea-01.jpg, http://weknowyourdreams.com/images/sea/sea-02.jpg\"}" "http://localhost:8081/download"
```

### Sample success to downloads
```java
Request:
curl -X POST -H "Content-Type: application/json" -d "{\"urls\" : \"http://weknowyourdreams.com/images/sea/sea-01.jpg, http://weknowyourdreams.com/images/sea/sea-02.jpg\"}" "http://localhost:8888/download"

Response:
{"results":[{"source":"http://weknowyourdreams.com/images/sea/sea-01.jpg","destination":"C:\\downloads\\sea-01.jpg","isSuccess":true,"userMessage":"Download http://weknowyourdreams.com/images/sea/sea-01.jpg completed","developerMessage":"Download http://weknowyourdreams.com/images/sea/sea-01.jpg completed"},{"source":"http://weknowyourdreams.com/images/sea/sea-02.jpg","destination":"C:\\downloads\\sea-02.jpg","isSuccess":true,"userMessage":"Download http://weknowyourdreams.com/images/sea/sea-02.jpg completed","developerMessage":"Download http://weknowyourdreams.com/images/sea/sea-02.jpg completed"}]}
```

### Sample failed when files alread exists
```java
Request:
curl -X POST -H "Content-Type: application/json" -d "{\"urls\" : \"http://weknowyourdreams.com/images/sea/sea-01.jpg, http://weknowyourdreams.com/images/sea/sea-02.jpg\"}" "http://localhost:8888/download"

Response:
{"results":[{"source":"http://weknowyourdreams.com/images/sea/sea-01.jpg","destination":"C:\\downloads\\sea-01.jpg","isSuccess":false,"userMessage":"Download http://weknowyourdreams.com/images/sea/sea-01.jpg failed","developerMessage":"Download http://weknowyourdreams.com/images/sea/sea-01.jpg failed, File Name: sea-01.jpg already exists in target download location"},{"source":"http://weknowyourdreams.com/images/sea/sea-02.jpg","destination":"C:\\downloads\\sea-02.jpg","isSuccess":false,"userMessage":"Download http://weknowyourdreams.com/images/sea/sea-02.jpg failed","developerMessage":"Download http://weknowyourdreams.com/images/sea/sea-02.jpg failed, File Name: sea-02.jpg already exists in target download location"}]}
```