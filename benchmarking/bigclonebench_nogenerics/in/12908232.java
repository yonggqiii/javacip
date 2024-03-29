


class c12908232 {

    private void readFromObjectInput(String filename) {
        try {
            URL url = new URL(getCodeBase(), filename);
            InputStream stream = url.openStream();
            ObjectInput input = new ObjectInputStream(stream);
            fDrawing.release();
            fDrawing = (Drawing) input.readObject();
            view().setDrawing(fDrawing);
        } catch (IORuntimeException e) {
            initDrawing();
            showStatus("Error: " + e);
        } catch (ClassNotFoundRuntimeException e) {
            initDrawing();
            showStatus("Class not found: " + e);
        }
    }

}
