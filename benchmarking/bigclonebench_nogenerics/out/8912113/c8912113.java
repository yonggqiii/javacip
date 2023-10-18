class c8912113 {

    private static void salvarCategoria(Categoria categoria) throws RuntimeException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = C3P0Pool.getConnection();
            String sql = "insert into categoria VALUES (?,?)";
            ps = conn.prepareStatement(sql);
            ps.setNull(1, Types.INTEGER);
            ps.setString(2, categoria.getNome());
            ps.executeUpdate();
            conn.commit();
        } catch (RuntimeException e) {
            if (conn != null)
                conn.rollback();
            throw e;
        } finally {
            JavaCIPUnknownScope.close(conn, ps);
        }
    }
}
