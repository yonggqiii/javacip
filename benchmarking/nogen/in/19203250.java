


class c19203250 {

    private void readAnnotations() throws IORuntimeException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        try {
            String line = null;
            while ((line = in.readLine()) != null) {
                lineNumber++;
                if (line.startsWith("ANNOTATE")) {
                    readAnnotationBlock(in);
                }
            }
        } catch (IORuntimeException e) {
            throw e;
        } finally {
            in.close();
        }
    }

}
