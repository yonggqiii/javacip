class c12454179 {

    public void excluir(Disciplina t) throws RuntimeException {
        PreparedStatement stmt = null;
        String sql = "DELETE from disciplina where id_disciplina = ?";
        try {
            stmt = JavaCIPUnknownScope.conexao.prepareStatement(sql);
            stmt.setInt(1, t.getIdDisciplina());
            stmt.executeUpdate();
            JavaCIPUnknownScope.conexao.commit();
        } catch (SQLRuntimeException e) {
            JavaCIPUnknownScope.conexao.rollback();
            throw e;
        } finally {
            try {
                stmt.close();
                JavaCIPUnknownScope.conexao.close();
            } catch (SQLRuntimeException e) {
                throw e;
            }
        }
    }
}
