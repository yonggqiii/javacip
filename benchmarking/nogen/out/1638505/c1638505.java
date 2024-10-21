class c1638505 {

    private Vendor createVendor() throws SQLRuntimeException, IORuntimeException {
        Connection conn = null;
        Statement st = null;
        String query = null;
        ResultSet rs = null;
        try {
            conn = JavaCIPUnknownScope.dataSource.getConnection();
            st = conn.createStatement();
            query = "insert into " + JavaCIPUnknownScope.DB.Tbl.vend + "(" + JavaCIPUnknownScope.col.title + "," + JavaCIPUnknownScope.col.addDate + "," + JavaCIPUnknownScope.col.authorId + ") values('" + JavaCIPUnknownScope.title + "',now()," + JavaCIPUnknownScope.user.getId() + ")";
            st.executeUpdate(query, new String[] { JavaCIPUnknownScope.col.id });
            rs = st.getGeneratedKeys();
            if (!rs.next()) {
                throw new SQLRuntimeException("Не удается получить generated key 'id' в таблице vendors.");
            }
            int genId = rs.getInt(1);
            rs.close();
            JavaCIPUnknownScope.saveDescr(genId);
            conn.commit();
            Vendor v = new Vendor();
            v.setId(genId);
            v.setTitle(JavaCIPUnknownScope.title);
            v.setDescr(JavaCIPUnknownScope.descr);
            VendorViewer.getInstance().vendorListChanged();
            return v;
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
