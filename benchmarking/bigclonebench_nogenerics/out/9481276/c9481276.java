class c9481276 {

    public void save(InputStream is) throws IORuntimeException {
        File dest = Config.getDataFile(JavaCIPUnknownScope.getInternalDate(), JavaCIPUnknownScope.getPhysMessageID());
        OutputStream os = null;
        try {
            os = new FileOutputStream(dest);
            IOUtils.copyLarge(is, os);
        } finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(is);
        }
    }
}
