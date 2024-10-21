class c5505322 {

    public Atividade insertAtividade(Atividade atividade) throws SQLRuntimeException {
        Connection conn = null;
        String insert = "insert into Atividade (idatividade, requerente_idrequerente, datacriacao, datatermino, valor, tipoatividade, descricao, fase_idfase, estado) " + "values " + "(nextval('seq_atividade'), " + atividade.getRequerente().getIdRequerente() + ", " + "'" + atividade.getDataCriacao() + "', '" + atividade.getDataTermino() + "', '" + atividade.getValor() + "', '" + atividade.getTipoAtividade().getIdTipoAtividade() + "', '" + atividade.getDescricao() + "', " + atividade.getFaseIdFase() + ", " + atividade.getEstado() + ")";
        try {
            conn = JavaCIPUnknownScope.connectionFactory.getConnection(true);
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            Integer result = stmt.executeUpdate(insert);
            if (result == 1) {
                String sqlSelect = "select last_value from seq_atividade";
                ResultSet rs = stmt.executeQuery(sqlSelect);
                while (rs.next()) {
                    atividade.setIdAtividade(rs.getInt("last_value"));
                }
            }
            conn.commit();
        } catch (SQLRuntimeException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.close();
        }
        return null;
    }
}
