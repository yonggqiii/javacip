


class c20955455 {

    @Override
    public void excluir(QuestaoMultiplaEscolha q) throws RuntimeException {
        PreparedStatement stmt = null;
        String sql = "DELETE FROM questao WHERE id_questao=?";
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, q.getIdQuestao());
            stmt.executeUpdate();
            conexao.commit();
        } catch (SQLRuntimeException e) {
            conexao.rollback();
            throw e;
        }
    }

}
