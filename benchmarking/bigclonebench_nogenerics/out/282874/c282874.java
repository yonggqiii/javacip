class c282874 {

    public void elimina(Cliente cli) throws errorSQL, errorConexionBD {
        System.out.println("GestorCliente.elimina()");
        int id = cli.getId();
        String sql;
        Statement stmt = null;
        try {
            JavaCIPUnknownScope.gd.begin();
            sql = "DELETE FROM cliente WHERE cod_cliente =" + id;
            System.out.println("Ejecutando: " + sql);
            stmt = JavaCIPUnknownScope.gd.getConexion().createStatement();
            stmt.executeUpdate(sql);
            System.out.println("executeUpdate");
            sql = "DELETE FROM persona WHERE id =" + id;
            System.out.println("Ejecutando: " + sql);
            stmt.executeUpdate(sql);
            JavaCIPUnknownScope.gd.commit();
            System.out.println("commit");
            stmt.close();
        } catch (SQLRuntimeException e) {
            JavaCIPUnknownScope.gd.rollback();
            throw new errorSQL(e.toString());
        } catch (errorConexionBD e) {
            System.err.println("Error en GestorCliente.elimina(): " + e);
        } catch (errorSQL e) {
            System.err.println("Error en GestorCliente.elimina(): " + e);
        }
    }
}
