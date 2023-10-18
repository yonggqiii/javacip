// added by JavaCIP
public interface ContenuFichierElectronique {

    public abstract String getNomFichier();

    public abstract InputStream getInputStream();

    public abstract String getContentType();

    public abstract long getTailleFichier();
}
