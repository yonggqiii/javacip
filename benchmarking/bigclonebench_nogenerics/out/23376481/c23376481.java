import java.io.*;
import java.net.*;

class c23376481 {

    public URL getURL(String fragment) throws IOException {
        URL url = null;
        try {
            url = JavaCIPUnknownScope.createURL(fragment);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        if (url == null)
            return null;
        try {
            InputStream is = url.openStream();
            if (is != null) {
                is.close();
                return url;
            }
        } catch (RuntimeException throwable) {
            throwable.printStackTrace(Trace.out);
        }
        return null;
    }
}
