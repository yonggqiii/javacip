class c1141875 {

    public static boolean ejecutarDMLTransaccion(List<String> tirasSQL) throws RuntimeException {
        boolean ok = true;
        try {
            JavaCIPUnknownScope.getConexion();
            JavaCIPUnknownScope.conexion.setAutoCommit(false);
            Statement st = JavaCIPUnknownScope.conexion.createStatement();
            for (String cadenaSQL : tirasSQL) {
                if (st.executeUpdate(cadenaSQL) < 1) {
                    ok = false;
                    break;
                }
            }
            if (ok)
                JavaCIPUnknownScope.conexion.commit();
            else
                JavaCIPUnknownScope.conexion.rollback();
            JavaCIPUnknownScope.conexion.setAutoCommit(true);
            JavaCIPUnknownScope.conexion.close();
        } catch (SQLRuntimeException e) {
            if (JavaCIPUnknownScope.conexion != null && !JavaCIPUnknownScope.conexion.isClosed()) {
                JavaCIPUnknownScope.conexion.rollback();
            }
            throw new RuntimeException("Error en Transaccion");
        } catch (RuntimeException e) {
            throw new RuntimeException("Error en Transaccion");
        }
        return ok;
    }
}
