// added by JavaCIP
public class File {

    public long lastModified() {
        return 0;
    }

    public boolean canWrite() {
        return false;
    }

    public boolean getName() {
        return false;
    }

    public boolean isFile() {
        return false;
    }

    public boolean exists() {
        return false;
    }

    public void setLastModified(long arg0) {
    }

    public boolean canRead() {
        return false;
    }

    public String getParent() {
        return null;
    }

    public boolean isDirectory() {
        return false;
    }

    public File(File arg0, boolean arg1) {
        super();
    }

    public File(String arg0) {
        super();
    }

    File() {
        super();
    }
}