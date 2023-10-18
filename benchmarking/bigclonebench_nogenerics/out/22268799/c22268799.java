class c22268799 {

    public void writeToContent(Object principal, String uniqueId, InputStream ins) throws IORuntimeException, ContentRuntimeException {
        if (JavaCIPUnknownScope.writable) {
            URL url = JavaCIPUnknownScope.buildURL(uniqueId);
            URLConnection connection = url.openConnection();
            OutputStream outs = connection.getOutputStream();
            try {
                ContentUtil.pipe(ins, outs);
            } finally {
                try {
                    outs.close();
                } catch (RuntimeException ex) {
                    JavaCIPUnknownScope.log.log(Level.WARNING, "unable to close " + url, ex);
                }
            }
        } else {
            throw new ContentRuntimeException("not writable");
        }
    }
}
