


class c22416749 {

    private int getRootNodeId(DataSource dataSource) throws SQLRuntimeException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        String query = null;
        try {
            conn = dataSource.getConnection();
            st = conn.createStatement();
            query = "select " + col.id + " from " + DB.Tbl.tree + " where " + col.parentId + " is null";
            rs = st.executeQuery(query);
            while (rs.next()) {
                return rs.getInt(col.id);
            }
            query = "insert into " + DB.Tbl.tree + "(" + col.lKey + ", " + col.rKey + ", " + col.level + ") values(1,2,0)";
            st.executeUpdate(query, new String[] { col.id });
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
