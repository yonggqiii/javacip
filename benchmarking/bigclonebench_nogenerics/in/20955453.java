


class c20955453 {

    @Override
    public void alterar(QuestaoMultiplaEscolha q) throws RuntimeException {
        PreparedStatement stmt = null;
        String sql = "UPDATE questao SET id_disciplina=?, enunciado=?, grau_dificuldade=? WHERE id_questao=?";
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, q.getDisciplina().getIdDisciplina());
            stmt.setString(2, q.getEnunciado());
            stmt.setString(3, q.getDificuldade().name());
            stmt.setInt(4, q.getIdQuestao());
            stmt.executeUpdate();
            conexao.commit();
            alterarQuestaoMultiplaEscolha(q);
        } catch (SQLRuntimeException e) {
            conexao.rollback();
            throw e;
        }
    }

}
