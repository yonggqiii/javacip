class c14915965 {

    protected static String getInitialUUID() {
        if (JavaCIPUnknownScope.myRand == null) {
            JavaCIPUnknownScope.myRand = new Random();
        }
        long rand = JavaCIPUnknownScope.myRand.nextLong();
        String sid;
        try {
            sid = InetAddress.getLocalHost().toString();
        } catch (UnknownHostRuntimeException e) {
            sid = Thread.currentThread().getName();
        }
        StringBuffer sb = new StringBuffer();
        sb.append(sid);
        sb.append(":");
        sb.append(Long.toString(rand));
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new OMRuntimeException(e);
        }
        md5.update(sb.toString().getBytes());
        byte[] array = md5.digest();
        StringBuffer sb2 = new StringBuffer();
        for (int j = 0; j < array.length; ++j) {
            int b = array[j] & 0xFF;
            sb2.append(Integer.toHexString(b));
        }
        int begin = JavaCIPUnknownScope.myRand.nextInt();
        if (begin < 0)
            begin = begin * -1;
        begin = begin % 8;
        return sb2.toString().substring(begin, begin + 18).toUpperCase();
    }
}
