class c20955454 {

    public void alterarQuestaoMultiplaEscolha(QuestaoMultiplaEscolha q) throws SQLRuntimeException {
        PreparedStatement stmt = null;
        String sql = "UPDATE multipla_escolha SET texto=?, gabarito=? WHERE id_questao=?";
        try {
            for (Alternativa alternativa : q.getAlternativa()) {
                stmt = JavaCIPUnknownScope.conexao.prepareStatement(sql);
                stmt.setString(1, alternativa.getTexto());
                stmt.setBoolean(2, alternativa.getGabarito());
                stmt.setInt(3, q.getIdQuestao());
                stmt.executeUpdate();
                JavaCIPUnknownScope.conexao.commit();
            }
        } catch (SQLRuntimeException e) {
            JavaCIPUnknownScope.conexao.rollback();
            throw e;
        }
    }
}
