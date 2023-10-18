class c12908232 {

    private void readFromObjectInput(String filename) {
        try {
            URL url = new URL(JavaCIPUnknownScope.getCodeBase(), filename);
            InputStream stream = url.openStream();
            ObjectInput input = new ObjectInputStream(stream);
            JavaCIPUnknownScope.fDrawing.release();
            JavaCIPUnknownScope.fDrawing = (Drawing) input.readObject();
            JavaCIPUnknownScope.view().setDrawing(JavaCIPUnknownScope.fDrawing);
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.initDrawing();
            JavaCIPUnknownScope.showStatus("Error: " + e);
        } catch (ClassNotFoundRuntimeException e) {
            JavaCIPUnknownScope.initDrawing();
            JavaCIPUnknownScope.showStatus("Class not found: " + e);
        }
    }
}
