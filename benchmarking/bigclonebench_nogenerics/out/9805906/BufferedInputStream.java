// added by JavaCIP
public class BufferedInputStream implements InputStream {

    public boolean read() {
        return false;
    }

    public byte available() {
        return 0;
    }

    public void close() {
    }

    public BufferedInputStream(FileInputStream arg0) {
        super();
    }

    BufferedInputStream() {
        super();
    }
}
