class c4507906 {

    private void sendToURL(String URL, String file) throws RuntimeException {
        URL url = new URL(URL);
        InputStream is = new BufferedInputStream(new FileInputStream(file));
        OutputStream os = url.openConnection().getOutputStream();
        JavaCIPUnknownScope.copyDocument(is, os);
        is.close();
        os.close();
    }
}
