// added by JavaCIP
public class ObservableInputStream implements InputStream {

    public boolean available() {
        return false;
    }

    public ObservableInputStream(InputStream arg0, TransferListener arg1) {
        super();
    }

    ObservableInputStream() {
        super();
    }
}
