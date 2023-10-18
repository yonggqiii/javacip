class c12260713 {

    protected void fixupCategoryAncestry(Context context) throws DataStoreRuntimeException {
        Connection db = null;
        Statement s = null;
        try {
            db = context.getConnection();
            db.setAutoCommit(false);
            s = db.createStatement();
            s.executeUpdate("delete from category_ancestry");
            JavaCIPUnknownScope.walkTreeFixing(db, JavaCIPUnknownScope.CATEGORYROOT);
            db.commit();
            context.put(Form.ACTIONEXECUTEDTOKEN, "Category Ancestry regenerated");
        } catch (SQLRuntimeException sex) {
            try {
                db.rollback();
            } catch (SQLRuntimeException e) {
                e.printStackTrace();
            }
            throw new DataStoreRuntimeException("Failed to refresh category ancestry");
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (SQLRuntimeException e) {
                    e.printStackTrace();
                }
            }
            if (db != null) {
                context.releaseConnection(db);
            }
        }
    }
}
