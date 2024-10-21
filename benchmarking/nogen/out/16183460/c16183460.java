class c16183460 {

    protected void createDb() {
        File rootFolder = new File(JavaCIPUnknownScope.dbFolderPath);
        if (!rootFolder.exists()) {
            rootFolder.mkdirs();
        }
        JavaCIPUnknownScope.openConnection();
        try {
            Statement stat = JavaCIPUnknownScope.connection.createStatement();
            ResourceBundle bundle = ResourceBundle.getBundle("uTaggerDb");
            for (String key : bundle.keySet()) {
                stat.executeUpdate(bundle.getString(key));
            }
            JavaCIPUnknownScope.commit();
        } catch (SQLRuntimeException e) {
            JavaCIPUnknownScope.LOG.warn(e);
            JavaCIPUnknownScope.rollback();
        }
    }
}
