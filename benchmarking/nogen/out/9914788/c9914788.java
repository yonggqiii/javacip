class c9914788 {

    public void insert(IIDGenerator idGenerators, AIDADocument item) throws SQLRuntimeException {
        AIDAActivityObjectDB.getManager(JavaCIPUnknownScope.token).insert(idGenerators, item);
        Connection con = JavaCIPUnknownScope.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement(JavaCIPUnknownScope.DOC_INSERT);
            ps.setLong(1, item.getId());
            ps.setString(2, item.getName());
            ps.setString(3, item.getRelativeLink());
            ps.executeUpdate();
            ps.close();
            JavaCIPUnknownScope.insertDescriptions(con, item);
        } catch (SQLRuntimeException sqlEx) {
            con.rollback();
            throw sqlEx;
        } finally {
            con.close();
        }
        return;
    }
}
