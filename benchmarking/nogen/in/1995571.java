


class c1995571 {

    protected Source resolveRepositoryURI(String path) throws TransformerRuntimeException {
        Source resolvedSource = null;
        try {
            if (path != null) {
                URL url = new URL(path);
                InputStream in = url.openStream();
                if (in != null) {
                    resolvedSource = new StreamSource(in);
                }
            } else {
                throw new TransformerRuntimeException("Resource does not exist. \"" + path + "\" is not accessible.");
            }
        } catch (MalformedURLRuntimeException mfue) {
            throw new TransformerRuntimeException("Error accessing resource using servlet context: " + path, mfue);
        } catch (IORuntimeException ioe) {
            throw new TransformerRuntimeException("Unable to access resource at: " + path, ioe);
        }
        return resolvedSource;
    }

}
