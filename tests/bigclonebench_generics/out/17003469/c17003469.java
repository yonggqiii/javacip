class c17003469 {

    public static String login() throws Exception {
        if (JavaCIPUnknownScope.sid == null) {
            String login = ROLAPClientAux.loadProperties().getProperty("user");
            String password = ROLAPClientAux.loadProperties().getProperty("password");
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            String password2 = JavaCIPUnknownScope.asHexString(md.digest());
            String query = "/server/login?user=" + login + "&extern_password=" + password + "&password=" + password2;
            Vector<String> res = ROLAPClientAux.sendRequest(query);
            String[] vals = res.get(0).split(";");
            JavaCIPUnknownScope.sid = vals[0];
        }
        return JavaCIPUnknownScope.sid;
    }
}
