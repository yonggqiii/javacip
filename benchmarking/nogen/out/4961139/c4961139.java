class c4961139 {

    public void delArtista(Integer numeroInscricao) throws RuntimeException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = C3P0Pool.getConnection();
            String sql = "delete from artista where numeroinscricao = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, numeroInscricao);
            ps.executeUpdate();
            JavaCIPUnknownScope.delEndereco(conn, ps, numeroInscricao);
            JavaCIPUnknownScope.delObras(conn, ps, numeroInscricao);
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
