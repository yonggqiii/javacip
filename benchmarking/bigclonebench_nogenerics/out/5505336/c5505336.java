class c5505336 {

    public void aprovarCandidato(Atividade atividade) throws SQLRuntimeException {
        Connection conn = null;
        String insert = "update Atividade_has_recurso_humano set ativo='true' " + "where atividade_idatividade=" + atividade.getIdAtividade() + " and " + " usuario_idusuario=" + atividade.getRecursoHumano().getIdUsuario();
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
