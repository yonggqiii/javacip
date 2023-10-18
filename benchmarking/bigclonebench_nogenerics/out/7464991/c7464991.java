class c7464991 {

    public void criarQuestaoDiscursiva(QuestaoDiscursiva q) throws SQLRuntimeException {
        PreparedStatement stmt = null;
        String sql = "INSERT INTO discursiva (id_questao,gabarito) VALUES (?,?)";
        try {
            stmt = JavaCIPUnknownScope.conexao.prepareStatement(sql);
            stmt.setInt(1, q.getIdQuestao());
            stmt.setString(2, q.getGabarito());
            stmt.executeUpdate();
            JavaCIPUnknownScope.conexao.commit();
        } catch (SQLRuntimeException e) {
            JavaCIPUnknownScope.conexao.rollback();
            throw e;
        }
    }
}
