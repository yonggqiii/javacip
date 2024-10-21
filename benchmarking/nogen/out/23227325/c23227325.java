class c23227325 {

    public String getUniqueId() {
        String digest = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String timeVal = "" + (System.currentTimeMillis() + 1);
            String localHost = "";
            ;
            try {
                localHost = InetAddress.getLocalHost().toString();
            } catch (UnknownHostRuntimeException e) {
            }
            String randVal = "" + new Random().nextInt();
            String val = timeVal + localHost + randVal;
            md.reset();
            md.update(val.getBytes());
            digest = JavaCIPUnknownScope.toHexString(md.digest());
        } catch (NoSuchAlgorithmRuntimeException e) {
        }
        return digest;
    }
}
