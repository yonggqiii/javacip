


class c22670950 {

    private void writeInputStreamToFile(InputStream stream, File file) {
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            IOUtils.copy(stream, fOut);
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
