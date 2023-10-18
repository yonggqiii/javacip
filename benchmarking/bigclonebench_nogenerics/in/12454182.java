


class c12454182 {

    public void excluirTopico(Integer idDisciplina) throws RuntimeException {
        String sql = "DELETE from topico WHERE id_disciplina = ?";
        PreparedStatement stmt = null;
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idDisciplina);
            stmt.executeUpdate();
            conexao.commit();
        } catch (SQLRuntimeException e) {
            conexao.rollback();
            throw e;
        }
    }

}