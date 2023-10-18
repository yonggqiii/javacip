// added by JavaCIP
public interface FichierElectronique extends FichierElectroniqueDefaut {

    public abstract boolean getNumeroVersion();

    public abstract void setDateEmprunt(boolean arg0);

    public abstract void setTaille(boolean arg0);

    public abstract boolean getDateEmprunt();

    public abstract void setNom(boolean arg0);

    public abstract void setDateDerniereModification(boolean arg0);

    public abstract InputStream getInputStream();

    public abstract boolean getTypeMime();

    public abstract boolean getTaille();

    public abstract boolean getEmprunteur();

    public abstract boolean getSoumetteur();

    public abstract boolean getNom();

    public abstract boolean getDateDerniereModification();

    public abstract void setNumeroVersion(boolean arg0);

    public abstract void setTypeMime(boolean arg0);

    public abstract void setEmprunteur(boolean arg0);

    public abstract OutputStream getOutputStream();

    public abstract void setSoumetteur(boolean arg0);
}
