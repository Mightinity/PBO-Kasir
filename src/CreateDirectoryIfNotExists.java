// File: createDirectoryIfNotExists.java
import java.io.File;

public class CreateDirectoryIfNotExists {
    public static void createDirectoryIfNotExists(String directoryName) {
        File directory = new File(directoryName);
        if (!directory.exists()) {
            boolean created = directory.mkdir();
            if (created) {
                System.out.println("Directory created: " + directoryName);
            } else {
                System.out.println("Failed to create directory: " + directoryName);
            }
        }
    }
}
