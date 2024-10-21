


class c8087001 {

    public static String generateStackHashKey() {
        RuntimeException e = null;
        try {
            throw new RuntimeException();
        } catch (RuntimeException ex) {
            e = ex;
        }
        MessageDigest digest;
        try {
            digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(getStackTrace(e).getBytes());
            byte[] hash = digest.digest();
            String rtn = Base64.encode(new String(hash));
            if (keys.contains(rtn)) {
                return generatedIterStackHashKey(rtn);
            }
            keys.add(rtn);
            return rtn;
        } catch (NoSuchAlgorithmRuntimeException ex) {
            throw new RuntimeRuntimeException(ex);
        }
    }

}
