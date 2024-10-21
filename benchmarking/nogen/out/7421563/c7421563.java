class c7421563 {

    private StringBuffer encoder(String arg) {
        if (arg == null) {
            arg = "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(arg.getBytes(SysConstant.charset));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return JavaCIPUnknownScope.toHex(md5.digest());
    }
}
