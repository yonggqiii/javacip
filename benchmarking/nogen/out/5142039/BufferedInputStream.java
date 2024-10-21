// added by JavaCIP
public class BufferedInputStream extends BufferedOutputStream {

    public int read() {
        return 0;
    }

    public BufferedInputStream(ZipArchiveInputStream arg0) {
        super(false);
    }

    BufferedInputStream() {
        super(false);
    }
}
