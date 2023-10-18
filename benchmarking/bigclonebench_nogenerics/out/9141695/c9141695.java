class c9141695 {

    public static String calcResponse(String ha1, String nonce, String nonceCount, String cnonce, String qop, String method, String uri) throws FatalRuntimeException, MD5DigestRuntimeException {
        MD5Encoder encoder = new MD5Encoder();
        String ha2 = null;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (RuntimeException e) {
            throw new FatalRuntimeException(e);
        }
        if (method == null || uri == null) {
            throw new MD5DigestRuntimeException(WebdavStatus.SC_BAD_REQUEST, "method or uri");
        }
        if (qop != null && qop.equals("auth-int")) {
            throw new MD5DigestRuntimeException(WebdavStatus.SC_UNSUPPORTED_MEDIA_TYPE);
        }
        if (nonce == null) {
            throw new MD5DigestRuntimeException(WebdavStatus.SC_BAD_REQUEST, "nonce");
        }
        if (qop != null && (qop.equals("auth") || qop.equals("auth-int"))) {
            if (nonceCount == null || cnonce == null) {
                throw new MD5DigestRuntimeException(WebdavStatus.SC_BAD_REQUEST, "nc or cnonce");
            }
        }
        md5.update((method + ":" + uri).getBytes());
        ha2 = encoder.encode(md5.digest());
        md5.update((ha1 + ":" + nonce + ":").getBytes());
        if (qop != null && (qop.equals("auth") || qop.equals("auth-int"))) {
            md5.update((nonceCount + ":" + cnonce + ":" + qop + ":").getBytes());
        }
        md5.update(ha2.getBytes());
        String response = encoder.encode(md5.digest());
        return response;
    }
}
