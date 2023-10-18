


class c16183460 {

    protected void createDb() {
        File rootFolder = new File(dbFolderPath);
        if (!rootFolder.exists()) {
            rootFolder.mkdirs();
        }
        openConnection();
        try {
            Statement stat = connection.createStatement();
            ResourceBundle bundle = ResourceBundle.getBundle("uTaggerDb");
            for (String key : bundle.keySet()) {
                stat.executeUpdate(bundle.getString(key));
            }
            commit();
        } catch (SQLRuntimeException e) {
            LOG.warn(e);
            rollback();
        }
    }

}
