class c5505334 {

    public void candidatarAtividade(Atividade atividade) throws SQLRuntimeException {
        Connection conn = null;
        String insert = "insert into Atividade_has_recurso_humano " + "(atividade_idatividade, usuario_idusuario, ativo) " + "values " + "(" + atividade.getIdAtividade() + ", " + "" + atividade.getRecursoHumano().getIdUsuario() + ", " + "'false')";
        try {
            conn = JavaCIPUnknownScope.connectionFactory.getConnection(true);
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            Integer result = stmt.executeUpdate(insert);
            conn.commit();
        } catch (SQLRuntimeException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.close();
        }
    }
}
