package download.data.utility;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GetDiskSpace {

    public static void main(String[] args) {
        Path path = Paths.get("C:\\download");
        File f = new File(String.valueOf(path.getRoot()));
        System.out.println("Printing the total space");
        System.out.println(f.getTotalSpace() +" bytes");
        System.out.println(f.getTotalSpace()/1000.00 +" Kilobytes");
        System.out.println(f.getTotalSpace()/1000000.00 +" Megabytes");
        System.out.println(f.getTotalSpace()/1000000000.00 +" Gigabytes");
        System.out.println("----------------------------");
        System.out.println("Printing the free space");
        System.out.println(f.getFreeSpace() +" bytes");
        System.out.println(f.getFreeSpace()/1000.00 +" Kilobytes");
        System.out.println(f.getFreeSpace()/1000000.00 +" Megabytes");
        System.out.println(f.getFreeSpace()/1000000000.00 +" Gigabytes");
    }
}
