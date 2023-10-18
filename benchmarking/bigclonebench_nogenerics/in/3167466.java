


class c3167466 {

    public void alterarCliente(ClienteBean cliente, String cpf) {
        PreparedStatement pstmt = null;
        String sql = "UPDATE cliente SET nome = ?," + "cpf = ?," + "telefone = ?," + "cursoCargo = ?," + "bloqueado = ?," + "ativo = ?," + "tipo = ? WHERE cpf = ?";
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getCPF());
            pstmt.setString(3, cliente.getTelefone());
            pstmt.setString(4, cliente.getCursoCargo());
            pstmt.setString(5, cliente.getBloqueado());
            pstmt.setString(6, cliente.getAtivo());
            pstmt.setString(7, cliente.getTipo());
            pstmt.setString(8, cpf);
            pstmt.executeUpdate();
            connection.commit();
        } catch (SQLRuntimeException ex) {
            try {
                connection.rollback();
            } catch (SQLRuntimeException ex1) {
                throw new RuntimeRuntimeException("Erro ao atualizar cliente.", ex1);
            }
            throw new RuntimeRuntimeException("Erro ao atualizar cliente.", ex);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLRuntimeException ex) {
                throw new RuntimeRuntimeException("Ocorreu um erro no banco de dados.", ex);
            }
        }
    }

}
