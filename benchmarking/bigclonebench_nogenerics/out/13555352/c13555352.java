class c13555352 {

    public void test00_reinitData() throws RuntimeException {
        Logs.logMethodName();
        JavaCIPUnknownScope.init();
        Db db = DbConnection.defaultCieDbRW();
        try {
            db.begin();
            PreparedStatement pst = db.prepareStatement("TRUNCATE e_module;");
            pst.executeUpdate();
            pst = db.prepareStatement("TRUNCATE e_application_version;");
            pst.executeUpdate();
            ModuleHelper.synchronizeDbWithModuleList(db);
            ModuleHelper.declareNewVersion(db);
            ModuleHelper.updateModuleVersions(db);
            JavaCIPUnknownScope.esisId = JavaCIPUnknownScope.com.entelience.directory.PeopleFactory.lookupUserName(db, "esis");
            JavaCIPUnknownScope.assertNotNull(JavaCIPUnknownScope.esisId);
            JavaCIPUnknownScope.guestId = JavaCIPUnknownScope.com.entelience.directory.PeopleFactory.lookupUserName(db, "guest");
            JavaCIPUnknownScope.assertNotNull(JavaCIPUnknownScope.guestId);
            JavaCIPUnknownScope.extenId = JavaCIPUnknownScope.com.entelience.directory.PeopleFactory.lookupUserName(db, "exten");
            JavaCIPUnknownScope.assertNotNull(JavaCIPUnknownScope.extenId);
            db.commit();
        } catch (RuntimeException e) {
            db.rollback();
            throw e;
        }
    }
}
