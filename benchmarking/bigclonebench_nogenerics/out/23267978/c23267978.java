class c23267978 {

    public static boolean copy(InputStream is, File file) {
        try {
            IOUtils.copy(is, new FileOutputStream(file));
            return true;
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.logger.severe(e.getMessage());
            return false;
        }
    }
}
