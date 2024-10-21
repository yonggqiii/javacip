class c12908231 {

    private void readFromStorableInput(String filename) {
        try {
            URL url = new URL(JavaCIPUnknownScope.getCodeBase(), filename);
            InputStream stream = url.openStream();
            StorableInput input = new StorableInput(stream);
            JavaCIPUnknownScope.fDrawing.release();
            JavaCIPUnknownScope.fDrawing = (Drawing) input.readStorable();
            JavaCIPUnknownScope.view().setDrawing(JavaCIPUnknownScope.fDrawing);
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.initDrawing();
            JavaCIPUnknownScope.showStatus("Error:" + e);
        }
    }
}
