


class c21618559 {

    protected String getGraphPath(String name) throws ServletRuntimeException {
        String hash;
        try {
            MessageDigest md = MessageDigest.getInstance(m_messagedigest_algorithm);
            md.update(name.getBytes());
            hash = bytesToHex(md.digest());
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new ServletRuntimeException("NoSuchAlgorithmRuntimeException while " + "attempting to hash file name: " + e);
        }
        File tempDir = (File) getServletContext().getAttribute("javax.servlet.context.tempdir");
        return tempDir.getAbsolutePath() + File.separatorChar + hash;
    }

}
