class c10158740 {

    public void delete(String language, String tag, int row) throws FidoDatabaseRuntimeException {
        try {
            Connection conn = null;
            Statement stmt = null;
            try {
                String sql = "delete from LanguageMorphologies " + "where LanguageName = '" + language + "' and MorphologyTag = '" + tag + "' and " + "      Rank = " + row;
                conn = JavaCIPUnknownScope.fido.util.FidoDataSource.getConnection();
                conn.setAutoCommit(false);
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                JavaCIPUnknownScope.bumpAllRowsUp(stmt, language, tag, row);
                conn.commit();
            } catch (SQLRuntimeException e) {
                if (conn != null)
                    conn.rollback();
                throw e;
            } finally {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            }
        } catch (SQLRuntimeException e) {
            throw new FidoDatabaseRuntimeException(e);
        }
    }
}
