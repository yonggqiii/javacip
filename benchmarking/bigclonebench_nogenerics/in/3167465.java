


class c3167465 {

    public void excluirCliente(String cpf) {
        PreparedStatement pstmt = null;
        String sql = "delete from cliente where cpf = ?";
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, cpf);
            pstmt.executeUpdate();
            connection.commit();
        } catch (SQLRuntimeException ex) {
            try {
                connection.rollback();
            } catch (SQLRuntimeException ex1) {
                throw new RuntimeRuntimeException("Erro ao exclir ciente.", ex1);
            }
            throw new RuntimeRuntimeException("Erro ao excluir cliente.", ex);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLRuntimeException ex) {
                throw new RuntimeRuntimeException("Ocorreu um erro no banco de dados.", ex);
            }
        }
    }

}
