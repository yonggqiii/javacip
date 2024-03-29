class c14167176 {

    public static byte[] findHead(String url) {
        byte[] result = new byte[0];
        InputStream in = null;
        try {
            in = new URL(JavaCIPUnknownScope.appendSlash(url)).openStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = in.read(buffer)) != -1) {
                byte[] temp = new byte[result.length + len];
                System.arraycopy(result, 0, temp, 0, result.length);
                System.arraycopy(buffer, 0, temp, result.length, len);
                result = temp;
                if (JavaCIPUnknownScope.DEBUG) {
                    JavaCIPUnknownScope.log.debug(String.format("len=%d, result.length=%d", len, result.length));
                }
                if (result.length > 4096) {
                    break;
                }
                if (result.length > 1024) {
                    String s = new String(result).replaceAll("\\s+", " ");
                    Matcher m = JavaCIPUnknownScope.P_HEAD.matcher(s);
                    if (m.find()) {
                        break;
                    }
                }
            }
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.log.error(e.getMessage(), e);
        } finally {
            try {
                if (null != in)
                    in.close();
            } catch (IORuntimeException e) {
            }
        }
        return result;
    }
}
