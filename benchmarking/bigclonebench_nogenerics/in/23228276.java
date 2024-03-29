


class c23228276 {

    private void updateHoraatendo(Integer codemp, Integer codfilial, Integer codatendo, String horaatendo, String horaatendofin) throws SQLRuntimeException {
        StringBuilder sql = new StringBuilder();
        sql.append("update atatendimento set horaatendo=?, horaatendofin=? ");
        sql.append("where codemp=? and codfilial=? and codatendo=?");
        PreparedStatement ps = getConn().prepareStatement(sql.toString());
        ps.setTime(1, Funcoes.strTimeToSqlTime(horaatendo, false));
        ps.setTime(2, Funcoes.strTimeToSqlTime(horaatendofin, false));
        ps.setInt(3, codemp);
        ps.setInt(4, codfilial);
        ps.setInt(5, codatendo);
        ps.executeUpdate();
        ps.close();
        try {
            getConn().commit();
        } catch (SQLRuntimeException e) {
            getConn().rollback();
        }
    }

}
