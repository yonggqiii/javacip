class c11075914 {

    public InputStream open(String filename) throws IORuntimeException {
        URL url = TemplateLoader.resolveURL("cms/" + filename);
        if (url != null)
            return url.openStream();
        url = TemplateLoader.resolveURL(filename);
        if (url != null)
            return url.openStream();
        return null;
    }
}
