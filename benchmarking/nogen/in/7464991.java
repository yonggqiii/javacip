


class c7464991 {

    public void criarQuestaoDiscursiva(QuestaoDiscursiva q) throws SQLRuntimeException {
        PreparedStatement stmt = null;
        String sql = "INSERT INTO discursiva (id_questao,gabarito) VALUES (?,?)";
        try {
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, q.getIdQuestao());
            stmt.setString(2, q.getGabarito());
            stmt.executeUpdate();
            conexao.commit();
        } catch (SQLRuntimeException e) {
            conexao.rollback();
            throw e;
        }
    }

}
