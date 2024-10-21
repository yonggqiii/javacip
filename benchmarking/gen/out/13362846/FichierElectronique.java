// added by JavaCIP
public interface FichierElectronique {

    public abstract boolean getNom();

    public abstract void setTaille(long arg0);

    public abstract void setNom(String arg0);

    public abstract void setDateDerniereModification(Date arg0);

    public abstract SupportDocument getSupport();

    public abstract void setTypeMime(String arg0);

    public abstract Object getId();

    public abstract OutputStream getOutputStream();

    public abstract void setSoumetteur(UtilisateurIFGD arg0);
}
