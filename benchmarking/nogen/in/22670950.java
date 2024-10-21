


class c22670950 {

    private void writeInputStreamToFile(InputStream stream, File file) {
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            IOUtils.copy(stream, fOut);
            fOut.close();
        } catch (FileNotFoundRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
    }

}
