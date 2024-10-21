class c20955455 {

    public void excluir(QuestaoMultiplaEscolha q) throws RuntimeException {
        PreparedStatement stmt = null;
        String sql = "DELETE FROM questao WHERE id_questao=?";
        try {
            stmt = JavaCIPUnknownScope.conexao.prepareStatement(sql);
            stmt.setInt(1, q.getIdQuestao());
            stmt.executeUpdate();
            JavaCIPUnknownScope.conexao.commit();
        } catch (SQLRuntimeException e) {
            JavaCIPUnknownScope.conexao.rollback();
            throw e;
        }
    }
}
