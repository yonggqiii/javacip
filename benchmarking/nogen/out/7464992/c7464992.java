class c7464992 {

    public void criarTopicoQuestao(Questao q, Integer idTopico) throws SQLRuntimeException {
        PreparedStatement stmt = null;
        String sql = "INSERT INTO questao_topico (id_questao, id_disciplina, id_topico) VALUES (?,?,?)";
        try {
            stmt = JavaCIPUnknownScope.conexao.prepareStatement(sql);
            stmt.setInt(1, q.getIdQuestao());
            stmt.setInt(2, q.getDisciplina().getIdDisciplina());
            stmt.setInt(3, idTopico);
            stmt.executeUpdate();
            JavaCIPUnknownScope.conexao.commit();
        } catch (SQLRuntimeException e) {
            JavaCIPUnknownScope.conexao.rollback();
            throw e;
        }
    }
}
