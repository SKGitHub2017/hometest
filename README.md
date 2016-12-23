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
