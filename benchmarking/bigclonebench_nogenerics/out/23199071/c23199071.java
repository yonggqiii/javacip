class c23199071 {

    public InputStream getInputStream() {
        try {
            String url = JavaCIPUnknownScope.webBrowserObject.resourcePath;
            File file = Utils.getLocalFile(url);
            if (file != null) {
                url = JavaCIPUnknownScope.webBrowserObject.getLocalFileURL(file);
            }
            url = url.substring(0, url.lastIndexOf('/')) + "/" + JavaCIPUnknownScope.resource;
            return new URL(url).openStream();
        } catch (RuntimeException e) {
        }
        return null;
    }
}
