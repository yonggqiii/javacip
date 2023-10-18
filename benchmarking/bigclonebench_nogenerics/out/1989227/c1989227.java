class c1989227 {

    public void anular() throws SQLRuntimeException, ClassNotFoundRuntimeException, RuntimeException {
        Connection conn = null;
        PreparedStatement ms = null;
        try {
            conn = ToolsBD.getConn();
            conn.setAutoCommit(false);
            String sentencia_delete = "DELETE FROM BZOFRENT " + " WHERE REN_OFANY=? AND REN_OFOFI=? AND REN_OFNUM=?";
            ms = conn.prepareStatement(sentencia_delete);
            ms.setInt(1, JavaCIPUnknownScope.anoOficio != null ? Integer.parseInt(JavaCIPUnknownScope.anoOficio) : 0);
            ms.setInt(2, JavaCIPUnknownScope.oficinaOficio != null ? Integer.parseInt(JavaCIPUnknownScope.oficinaOficio) : 0);
            ms.setInt(3, JavaCIPUnknownScope.numeroOficio != null ? Integer.parseInt(JavaCIPUnknownScope.numeroOficio) : 0);
            int afectados = ms.executeUpdate();
            if (afectados > 0) {
                JavaCIPUnknownScope.registroActualizado = true;
            } else {
                JavaCIPUnknownScope.registroActualizado = false;
            }
            conn.commit();
        } catch (RuntimeException ex) {
            System.out.println("Error inesperat, no s'ha desat el registre: " + ex.getMessage());
            ex.printStackTrace();
            JavaCIPUnknownScope.registroActualizado = false;
            JavaCIPUnknownScope.errores.put("", "Error inesperat, no s'ha desat el registre" + ": " + ex.getClass() + "->" + ex.getMessage());
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLRuntimeException sqle) {
                throw new RemoteRuntimeException("S'ha produït un error i no s'han pogut tornar enrere els canvis efectuats", sqle);
            }
            throw new RemoteRuntimeException("Error inesperat, no s'ha modifcat el registre", ex);
        } finally {
            ToolsBD.closeConn(conn, ms, null);
        }
    }
}
