// added by JavaCIP
public interface PdfWriter {

    public static boolean PageLayoutTwoColumnRight = false;

    public abstract void setViewerPreferences(boolean arg0);

    public abstract void setCropBoxSize(boolean arg0);

    public abstract PdfContentByte getDirectContent();

    public abstract void setBoxSize(String arg0, boolean arg1);

    public abstract void setPageEvent(EventBackgroundAndPageNumbers arg0);

    public abstract boolean getImportedPage(PdfReader arg0, int arg1);

    public static PdfWriter getInstance(Document arg0, FileOutputStream arg1) {
        return null;
    }

    public abstract int getPageNumber();
}
