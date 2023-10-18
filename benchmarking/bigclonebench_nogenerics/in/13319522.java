


class c13319522 {

    protected void loadXslFromUri(URI uri) {
        URLConnection urlc;
        try {
            urlc = uri.toURL().openConnection();
            InputStream is = urlc.getInputStream();
            Reader rd = new InputStreamReader(is);
            Source xslSource = new StreamSource(rd);
            try {
                xslTemplate = factory.newTemplates(xslSource);
            } catch (TransformerConfigurationRuntimeException tce) {
                tce.printStackTrace();
            }
        } catch (IORuntimeException ioe) {
            ioe.printStackTrace();
        }
    }

}
