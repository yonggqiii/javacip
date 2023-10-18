// added by JavaCIP
public class ZipInputStream extends FileOutputStream {

    public ZipEntry getNextEntry() {
        return null;
    }

    public ZipInputStream(FileInputStream arg0) {
        super((File) null);
    }

    ZipInputStream() {
        super((File) null);
    }
}
