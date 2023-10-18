class c8912110 {

    private static void salvarArtista(Artista artista) throws RuntimeException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = C3P0Pool.getConnection();
            String sql = "insert into artista VALUES (?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, artista.getNumeroInscricao());
            ps.setString(2, artista.getNome());
            ps.setBoolean(3, artista.isSexo());
            ps.setString(4, artista.getEmail());
            ps.setString(5, artista.getObs());
            ps.setString(6, artista.getTelefone());
            ps.setNull(7, Types.INTEGER);
            ps.executeUpdate();
            JavaCIPUnknownScope.salvarEndereco(conn, ps, artista);
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
