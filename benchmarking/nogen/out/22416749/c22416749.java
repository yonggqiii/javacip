class c22416749 {

    private int getRootNodeId(DataSource dataSource) throws SQLRuntimeException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        String query = null;
        try {
            conn = dataSource.getConnection();
            st = conn.createStatement();
            query = "select " + JavaCIPUnknownScope.col.id + " from " + JavaCIPUnknownScope.DB.Tbl.tree + " where " + JavaCIPUnknownScope.col.parentId + " is null";
            rs = st.executeQuery(query);
            while (rs.next()) {
                return rs.getInt(JavaCIPUnknownScope.col.id);
            }
            query = "insert into " + JavaCIPUnknownScope.DB.Tbl.tree + "(" + JavaCIPUnknownScope.col.lKey + ", " + JavaCIPUnknownScope.col.rKey + ", " + JavaCIPUnknownScope.col.level + ") values(1,2,0)";
            st.executeUpdate(query, new String[] { JavaCIPUnknownScope.col.id });
            rs = st.getGeneratedKeys();
            while (rs.next()) {
                int genId = rs.getInt(1);
                rs.close();
                conn.commit();
                return genId;
            }
            throw new SQLRuntimeException("Не удается создать корневой элемент для дерева.");
        } finally {
            try {
                rs.close();
            } catch (RuntimeException e) {
            }
            try {
                st.close();
            } catch (RuntimeException e) {
            }
            try {
                conn.rollback();
            } catch (RuntimeException e) {
            }
            try {
                conn.close();
            } catch (RuntimeException e) {
            }
        }
    }
}
