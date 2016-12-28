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
1. Get code from
```
git clone https://github.com/SKGitHub2017/hometest
```
2. Build
```
mvn clean install
```
3. Run
```java
Run with default config server.port=8888 and download.file.location=C:\\download

java -jar download-1.0.0.jar

curl -X POST -H "Content-Type: application/json" -d '{"urls" : "http://weknowyourdreams.com/images/sea/sea-01.jpg, http://weknowyourdreams.com/images/sea/sea-02.jpg"}' "http://localhost:8888/download"
```
```
Run with override server.port=8080 and download.file.location=C:\\download.1

java -jar download-1.0.0.jar --server.port=8080 --download.file.location=C:\\download.1

curl -X POST -H "Content-Type: application/json" -d '{"urls" : "http://weknowyourdreams.com/images/sea/sea-01.jpg, http://weknowyourdreams.com/images/sea/sea-02.jpg"}' "http://localhost:8080/download"
```