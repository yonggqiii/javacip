// added by JavaCIP
public interface PdfWriter {

    public static PdfWriter getInstance(UNKNOWN_146 arg0, FileOutputStream arg1) {
        return null;
    }

    public abstract PdfImportedPage getImportedPage(PdfReader arg0, int arg1);

    public abstract PdfContentByte getDirectContent();
}
