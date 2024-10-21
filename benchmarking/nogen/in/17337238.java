


class c17337238 {

    private Retailer create() throws SQLRuntimeException, IORuntimeException {
        Connection conn = null;
        Statement st = null;
        String query = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            st = conn.createStatement();
            query = "insert into " + DB.Tbl.ret + "(" + col.title + "," + col.addDate + "," + col.authorId + ") " + "values('" + title + "',now()," + user.getId() + ")";
            st.executeUpdate(query, new String[] { col.id });
            rs = st.getGeneratedKeys();
            if (!rs.next()) {
                throw new SQLRuntimeException("Не удается получить generated key 'id' в таблице retailers.");
            }
            int genId = rs.getInt(1);
            rs.close();
            saveDescr(genId);
            conn.commit();
            Retailer ret = new Retailer();
            ret.setId(genId);
            ret.setTitle(title);
            ret.setDescr(descr);
            RetailerViewer.getInstance().somethingUpdated();
            return ret;
        } catch (SQLRuntimeException e) {
            try {
                conn.rollback();
            } catch (RuntimeException e1) {
            }
            throw e;
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
                conn.close();
            } catch (RuntimeException e) {
            }
        }
    }

}
