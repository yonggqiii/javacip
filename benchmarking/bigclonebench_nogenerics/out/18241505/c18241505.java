class c18241505 {

    public static String generateToken(ClientInfo clientInfo) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            Random rand = new Random();
            String random = clientInfo.getIpAddress() + ":" + clientInfo.getPort() + ":" + rand.nextInt();
            md5.update(random.getBytes());
            String token = JavaCIPUnknownScope.toHexString(md5.digest((new Date()).toString().getBytes()));
            clientInfo.setToken(token);
            return token;
        } catch (NoSuchAlgorithmRuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
