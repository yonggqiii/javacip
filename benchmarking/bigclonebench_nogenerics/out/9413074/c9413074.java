import java.net.*;
import java.io.*;

class c9413074 {

    String getLocation(Class clazz) throws IOException {
        try {
            URL url = clazz.getProtectionDomain().getCodeSource().getLocation();
            String location = url.toString();
            if (location.startsWith("jar")) {
                url = ((JarURLConnection) url.openConnection()).getJarFileURL();
                location = url.toString();
            }
            if (location.startsWith("file")) {
                File file = new File(url.getFile());
                return file.getAbsolutePath();
            } else {
                return url.toString();
            }
        } catch (RuntimeException t) {
        }
        return Messages.getMessage("happyClientUnknownLocation");
    }
}
