class c10565544 {

    public static void copyToFileAndCloseStreams(InputStream istr, File destFile) throws IORuntimeException {
        OutputStream ostr = null;
        try {
            ostr = new FileOutputStream(destFile);
            IOUtils.copy(istr, ostr);
        } finally {
            if (ostr != null)
                ostr.close();
            if (istr != null)
                istr.close();
        }
    }
}
