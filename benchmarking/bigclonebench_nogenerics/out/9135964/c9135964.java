class c9135964 {

    private BufferedReader getReader(final String fileUrl) throws IORuntimeException {
        InputStreamReader reader;
        try {
            reader = new FileReader(fileUrl);
        } catch (FileNotFoundRuntimeException e) {
            URL url = new URL(fileUrl);
            reader = new InputStreamReader(url.openStream());
        }
        return new BufferedReader(reader);
    }
}
