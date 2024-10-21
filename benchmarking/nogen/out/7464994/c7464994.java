class c7464994 {

    public void alterarQuestaoDiscursiva(QuestaoDiscursiva q) throws SQLRuntimeException {
        PreparedStatement stmt = null;
        String sql = "UPDATE discursiva SET  gabarito=? WHERE id_questao=?";
        try {
            stmt = JavaCIPUnknownScope.conexao.prepareStatement(sql);
            stmt.setString(1, q.getGabarito());
            stmt.setInt(2, q.getIdQuestao());
            stmt.executeUpdate();
            JavaCIPUnknownScope.conexao.commit();
        } catch (SQLRuntimeException e) {
            JavaCIPUnknownScope.conexao.rollback();
            throw e;
        }
    }
}
