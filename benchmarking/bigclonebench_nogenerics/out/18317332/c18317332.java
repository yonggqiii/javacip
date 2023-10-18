class c18317332 {

    void execute(Connection conn, Component parent, String context, final ProgressMonitor progressMonitor, ProgressWrapper progressWrapper) throws RuntimeException {
        int noOfComponents = JavaCIPUnknownScope.m_components.length;
        Statement statement = null;
        StringBuffer pmNoteBuf = new StringBuffer(JavaCIPUnknownScope.m_update ? "Updating " : "Creating ");
        pmNoteBuf.append(JavaCIPUnknownScope.m_itemNameAbbrev);
        pmNoteBuf.append(" ");
        pmNoteBuf.append(JavaCIPUnknownScope.m_itemNameValue);
        final String pmNote = pmNoteBuf.toString();
        progressMonitor.setNote(pmNote);
        try {
            conn.setAutoCommit(false);
            int id = -1;
            if (JavaCIPUnknownScope.m_update) {
                statement = conn.createStatement();
                String sql = JavaCIPUnknownScope.getUpdateSql(noOfComponents, JavaCIPUnknownScope.m_id);
                statement.executeUpdate(sql);
                id = JavaCIPUnknownScope.m_id;
                if (JavaCIPUnknownScope.m_indexesChanged)
                    JavaCIPUnknownScope.deleteComponents(conn, id);
            } else {
                PreparedStatement pStmt = JavaCIPUnknownScope.getInsertPrepStmt(conn, noOfComponents);
                pStmt.executeUpdate();
                Integer res = DbCommon.getAutoGenId(parent, context, pStmt);
                if (res == null)
                    return;
                id = res.intValue();
            }
            if (!JavaCIPUnknownScope.m_update || JavaCIPUnknownScope.m_indexesChanged) {
                PreparedStatement insertCompPrepStmt = conn.prepareStatement(JavaCIPUnknownScope.getInsertComponentPrepStmtSql());
                for (int i = 0; i < noOfComponents; i++) {
                    JavaCIPUnknownScope.createComponent(progressMonitor, JavaCIPUnknownScope.m_components, pmNote, id, i, insertCompPrepStmt);
                }
            }
            conn.commit();
            JavaCIPUnknownScope.m_itemTable.getPrimaryId().setVal(JavaCIPUnknownScope.m_item, id);
            JavaCIPUnknownScope.m_itemCache.updateCache(JavaCIPUnknownScope.m_item, id);
        } catch (SQLRuntimeException ex) {
            try {
                conn.rollback();
            } catch (SQLRuntimeException e) {
                e.printStackTrace();
            }
            throw ex;
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }
}
