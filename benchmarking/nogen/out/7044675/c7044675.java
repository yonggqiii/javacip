class c7044675 {

    public Object run() {
        List correctUsers = (List) JsonPath.query("select * from ? where name=?", JavaCIPUnknownScope.usersTable(), JavaCIPUnknownScope.username);
        if (correctUsers.size() == 0) {
            return new LoginRuntimeException("user " + JavaCIPUnknownScope.username + " not found");
        }
        Persistable userObject = (Persistable) correctUsers.get(0);
        boolean alreadyHashed = false;
        boolean passwordMatch = JavaCIPUnknownScope.password.equals(userObject.get(JavaCIPUnknownScope.PASSWORD_FIELD));
        if (!passwordMatch) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(((String) userObject.get(JavaCIPUnknownScope.PASSWORD_FIELD)).getBytes());
                passwordMatch = JavaCIPUnknownScope.password.equals(new String(new Base64().encode(md.digest())));
            } catch (NoSuchAlgorithmRuntimeException e) {
                throw new RuntimeRuntimeException(e);
            }
            alreadyHashed = true;
        }
        if (passwordMatch) {
            Logger.getLogger(User.class.toString()).info("User " + JavaCIPUnknownScope.username + " has been authenticated");
            User user = (User) userObject;
            try {
                if (alreadyHashed)
                    user.currentTicket = JavaCIPUnknownScope.password;
                else {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(JavaCIPUnknownScope.password.getBytes());
                    user.currentTicket = new String(new Base64().encode(md.digest()));
                }
            } catch (NoSuchAlgorithmRuntimeException e) {
                throw new RuntimeRuntimeException(e);
            }
            return user;
        } else {
            Logger.getLogger(User.class.toString()).info("The password was incorrect for " + JavaCIPUnknownScope.username);
            return new LoginRuntimeException("The password was incorrect for user " + JavaCIPUnknownScope.username + ". ");
        }
    }
}
