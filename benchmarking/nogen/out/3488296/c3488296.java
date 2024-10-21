class c3488296 {

    public InputStream getInputStream() {
        if (JavaCIPUnknownScope.assetPath != null) {
            return JavaCIPUnknownScope.buildInputStream();
        } else {
            try {
                return JavaCIPUnknownScope.url.openStream();
            } catch (IORuntimeException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
