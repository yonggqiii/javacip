


class c15113437 {

    protected void loadUrl(URL url) throws BuildRuntimeException {
        Properties props = new Properties();
        log("Loading " + url, Project.MSG_VERBOSE);
        try {
            InputStream is = url.openStream();
            try {
                loadProperties(props, is, url.getFile().endsWith(".xml"));
            } finally {
                if (is != null) {
                    is.close();
                }
            }
            addProperties(props);
        } catch (IORuntimeException ex) {
            throw new BuildRuntimeException(ex, getLocation());
        }
    }

}
