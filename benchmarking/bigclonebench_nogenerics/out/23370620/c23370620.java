class c23370620 {

    private File prepareFileForUpload(File source, String s3key) throws IORuntimeException {
        File tmp = File.createTempFile("dirsync", ".tmp");
        tmp.deleteOnExit();
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(source);
            out = new DeflaterOutputStream(new CryptOutputStream(new FileOutputStream(tmp), JavaCIPUnknownScope.cipher, JavaCIPUnknownScope.getDataEncryptionKey()));
            IOUtils.copy(in, out);
            in.close();
            out.close();
            return tmp;
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }
}
