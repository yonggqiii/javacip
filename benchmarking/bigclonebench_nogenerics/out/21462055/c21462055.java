class c21462055 {

    public int unindexRecord(String uuid) throws SQLRuntimeException, CatalogIndexRuntimeException {
        Connection con = null;
        boolean autoCommit = true;
        PreparedStatement st = null;
        int nRows = 0;
        StringSet fids = new StringSet();
        if (JavaCIPUnknownScope.cswRemoteRepository.isActive()) {
            StringSet uuids = new StringSet();
            uuids.add(uuid);
            fids = JavaCIPUnknownScope.queryFileIdentifiers(uuids);
        }
        try {
            con = JavaCIPUnknownScope.returnConnection().getJdbcConnection();
            autoCommit = con.getAutoCommit();
            con.setAutoCommit(false);
            String sSql = "DELETE FROM " + JavaCIPUnknownScope.getResourceDataTableName() + " WHERE DOCUUID=?";
            JavaCIPUnknownScope.logExpression(sSql);
            st = con.prepareStatement(sSql);
            st.setString(1, uuid);
            nRows = st.executeUpdate();
            con.commit();
        } catch (SQLRuntimeException ex) {
            if (con != null) {
                con.rollback();
            }
            throw ex;
        } finally {
            JavaCIPUnknownScope.closeStatement(st);
            if (con != null) {
                con.setAutoCommit(autoCommit);
            }
        }
        CatalogIndexAdapter indexAdapter = JavaCIPUnknownScope.getCatalogIndexAdapter();
        if (indexAdapter != null) {
            indexAdapter.deleteDocument(uuid);
            if (JavaCIPUnknownScope.cswRemoteRepository.isActive()) {
                if (fids.size() > 0)
                    JavaCIPUnknownScope.cswRemoteRepository.onRecordsDeleted(fids);
            }
        }
        return nRows;
    }
}
