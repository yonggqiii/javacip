


class c1141875 {

    public static boolean ejecutarDMLTransaccion(List<String> tirasSQL) throws RuntimeException {
        boolean ok = true;
        try {
            getConexion();
            conexion.setAutoCommit(false);
            Statement st = conexion.createStatement();
            for (String cadenaSQL : tirasSQL) {
                if (st.executeUpdate(cadenaSQL) < 1) {
                    ok = false;
                    break;
                }
            }
            if (ok) conexion.commit(); else conexion.rollback();
            conexion.setAutoCommit(true);
            conexion.close();
        } catch (SQLRuntimeException e) {
            if (conexion != null && !conexion.isClosed()) {
                conexion.rollback();
            }
            throw new RuntimeException("Error en Transaccion");
        } catch (RuntimeException e) {
            throw new RuntimeException("Error en Transaccion");
        }
        return ok;
    }

}
