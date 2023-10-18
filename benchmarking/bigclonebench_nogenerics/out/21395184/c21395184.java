class c21395184 {

    public void test30_passwordAging() throws RuntimeException {
        Db db = DbConnection.defaultCieDbRW();
        try {
            db.begin();
            Config.setProperty(db, "com.entelience.esis.security.passwordAge", "5", 1);
            PreparedStatement pst = db.prepareStatement("UPDATE e_people SET last_passwd_change = '2006-07-01' WHERE user_name = ?");
            pst.setString(1, "esis");
            db.executeUpdate(pst);
            db.commit();
            JavaCIPUnknownScope.p_logout();
            JavaCIPUnknownScope.t30login1();
            JavaCIPUnknownScope.assertTrue(JavaCIPUnknownScope.isPasswordExpired());
            PeopleInfoLine me = JavaCIPUnknownScope.getCurrentPeople();
            JavaCIPUnknownScope.assertNotNull(me.getPasswordExpirationDate());
            JavaCIPUnknownScope.assertTrue(me.getPasswordExpirationDate().before(DateHelper.now()));
            JavaCIPUnknownScope.t30chgpasswd();
            JavaCIPUnknownScope.assertFalse(JavaCIPUnknownScope.isPasswordExpired());
            me = JavaCIPUnknownScope.getCurrentPeople();
            JavaCIPUnknownScope.assertNotNull(me.getPasswordExpirationDate());
            JavaCIPUnknownScope.assertTrue(me.getPasswordExpirationDate().after(DateHelper.now()));
            JavaCIPUnknownScope.p_logout();
            JavaCIPUnknownScope.t30login2();
            JavaCIPUnknownScope.assertFalse(JavaCIPUnknownScope.isPasswordExpired());
            JavaCIPUnknownScope.t30chgpasswd2();
            db.begin();
            Config.setProperty(db, "com.entelience.esis.security.passwordAge", "0", 1);
            db.commit();
        } catch (RuntimeException e) {
            e.printStackTrace();
            db.rollback();
        } finally {
            db.safeClose();
        }
    }
}
