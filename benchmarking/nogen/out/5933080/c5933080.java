class c5933080 {

    public void addUser(String username, String password, String filename) {
        String data = "";
        try {
            JavaCIPUnknownScope.open(filename);
            MessageDigest mdAlgorithm = MessageDigest.getInstance("MD5");
            mdAlgorithm.update(password.getBytes());
            byte[] digest = mdAlgorithm.digest();
            StringBuffer encPasswd = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                password = Integer.toHexString(255 & digest[i]);
                if (password.length() < 2) {
                    password = "0" + password;
                }
                encPasswd.append(password);
                data = username + " " + encPasswd + "\r\n";
            }
            try {
                long length = JavaCIPUnknownScope.file.length();
                JavaCIPUnknownScope.file.seek(length);
                JavaCIPUnknownScope.file.write(data.getBytes());
            } catch (IORuntimeException ex) {
                ex.printStackTrace();
            }
            JavaCIPUnknownScope.close();
        } catch (NoSuchAlgorithmRuntimeException ex) {
        }
    }
}
