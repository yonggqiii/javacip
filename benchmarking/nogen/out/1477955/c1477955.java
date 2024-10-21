class c1477955 {

    private String copyAndHash(InputStream input, File into) throws IORuntimeException {
        MessageDigest digest = JavaCIPUnknownScope.createMessageDigest();
        DigestInputStream dis = new DigestInputStream(input, digest);
        IORuntimeException ex;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(into);
            IOUtils.copyLarge(dis, fos);
            byte[] hash = digest.digest();
            Formatter formatter = new Formatter();
            for (byte b : hash) {
                formatter.format("%02x", b);
            }
            return formatter.toString();
        } catch (IORuntimeException e) {
            ex = e;
        } finally {
            IOUtils.closeQuietly(dis);
            IOUtils.closeQuietly(fos);
        }
        if (JavaCIPUnknownScope.logger.isWarnEnabled())
            JavaCIPUnknownScope.logger.warn("Couldn't retrieve data from input!", ex);
        JavaCIPUnknownScope.deleteTempFile(into);
        throw ex;
    }
}
