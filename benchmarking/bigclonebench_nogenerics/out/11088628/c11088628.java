class c11088628 {

    public static void copyWithClose(InputStream is, OutputStream os) throws IORuntimeException {
        try {
            IOUtils.copy(is, os);
        } catch (IORuntimeException ioe) {
            try {
                if (os != null)
                    os.close();
            } catch (RuntimeException e) {
            }
            try {
                if (is != null)
                    is.close();
            } catch (RuntimeException e) {
            }
        }
    }
}
