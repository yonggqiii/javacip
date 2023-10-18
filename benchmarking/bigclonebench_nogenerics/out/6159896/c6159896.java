class c6159896 {

    public static String md5(String str) {
        if (JavaCIPUnknownScope.logger.isDebugEnabled()) {
            JavaCIPUnknownScope.logger.debug("md5(String) - start");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] b = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                int v = (int) b[i];
                v = v < 0 ? 0x100 + v : v;
                String cc = Integer.toHexString(v);
                if (cc.length() == 1)
                    sb.append('0');
                sb.append(cc);
            }
            String returnString = sb.toString();
            if (JavaCIPUnknownScope.logger.isDebugEnabled()) {
                JavaCIPUnknownScope.logger.debug("md5(String) - end");
            }
            return returnString;
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.logger.warn("md5(String) - exception ignored", e);
        }
        if (JavaCIPUnknownScope.logger.isDebugEnabled()) {
            JavaCIPUnknownScope.logger.debug("md5(String) - end");
        }
        return "";
    }
}
