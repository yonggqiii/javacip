class c12454178 {

    public void alterar(Disciplina t) throws RuntimeException {
        PreparedStatement stmt = null;
        String sql = "UPDATE disciplina SET nm_disciplina = ?, cod_disciplina = ? WHERE id_disciplina = ?";
        try {
            stmt = JavaCIPUnknownScope.conexao.prepareStatement(sql);
            stmt.setString(1, t.getNomeDisciplina());
            stmt.setString(2, t.getCodDisciplina());
            stmt.setInt(3, t.getIdDisciplina());
            stmt.executeUpdate();
            JavaCIPUnknownScope.conexao.commit();
            int id_disciplina = t.getIdDisciplina();
            JavaCIPUnknownScope.excluirTopico(t.getIdDisciplina());
            for (Topico item : t.getTopicos()) {
                JavaCIPUnknownScope.criarTopico(item, id_disciplina);
            }
        } catch (SQLRuntimeException e) {
            JavaCIPUnknownScope.conexao.rollback();
            throw e;
        }
    }
}
