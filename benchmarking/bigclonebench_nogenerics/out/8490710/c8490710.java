class c8490710 {

    public void actualizar() throws SQLRuntimeException, ClassNotFoundRuntimeException, RuntimeException {
        Connection conn = null;
        PreparedStatement ms = null;
        JavaCIPUnknownScope.registroActualizado = false;
        try {
            conn = ToolsBD.getConn();
            conn.setAutoCommit(false);
            Date fechaSystem = new Date();
            DateFormat aaaammdd = new SimpleDateFormat("yyyyMMdd");
            int fzafsis = Integer.parseInt(aaaammdd.format(fechaSystem));
            DateFormat hhmmss = new SimpleDateFormat("HHmmss");
            DateFormat sss = new SimpleDateFormat("S");
            String ss = sss.format(fechaSystem);
            if (ss.length() > 2) {
                ss = ss.substring(0, 2);
            }
            int fzahsis = Integer.parseInt(hhmmss.format(fechaSystem) + ss);
            ms = conn.prepareStatement(JavaCIPUnknownScope.SENTENCIA_UPDATE);
            ms.setString(1, JavaCIPUnknownScope.descartadoEntrada);
            ms.setString(2, JavaCIPUnknownScope.usuarioEntrada);
            ms.setString(3, JavaCIPUnknownScope.motivosDescarteEntrada);
            ms.setInt(4, Integer.parseInt(JavaCIPUnknownScope.anoOficio));
            ms.setInt(5, Integer.parseInt(JavaCIPUnknownScope.oficinaOficio));
            ms.setInt(6, Integer.parseInt(JavaCIPUnknownScope.numeroOficio));
            ms.setInt(7, JavaCIPUnknownScope.anoEntrada != null ? Integer.parseInt(JavaCIPUnknownScope.anoEntrada) : 0);
            ms.setInt(8, JavaCIPUnknownScope.oficinaEntrada != null ? Integer.parseInt(JavaCIPUnknownScope.oficinaEntrada) : 0);
            ms.setInt(9, JavaCIPUnknownScope.numeroEntrada != null ? Integer.parseInt(JavaCIPUnknownScope.numeroEntrada) : 0);
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
