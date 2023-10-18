class c19203250 {

    private void readAnnotations() throws IORuntimeException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(JavaCIPUnknownScope.url.openStream()));
        try {
            String line = null;
            while ((line = in.readLine()) != null) {
                JavaCIPUnknownScope.lineNumber++;
                if (line.startsWith("ANNOTATE")) {
                    JavaCIPUnknownScope.readAnnotationBlock(in);
                }
            }
        } catch (IORuntimeException e) {
            throw e;
        } finally {
            in.close();
        }
    }
}
