class c16069454 {

    public int add(WebService ws) throws FidoDatabaseRuntimeException {
        try {
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "insert into WebServices (MethodName, ServiceURI) " + "values ('" + ws.getMethodName() + "', '" + ws.getServiceURI() + "')";
                conn = JavaCIPUnknownScope.fido.util.FidoDataSource.getConnection();
                conn.setAutoCommit(false);
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                int id;
                sql = "select currval('webservices_webserviceid_seq')";
                rs = stmt.executeQuery(sql);
                if (rs.next() == false)
                    throw new SQLRuntimeException("No rows returned from select currval() query");
                else
                    id = rs.getInt(1);
                PreparedStatement pstmt = conn.prepareStatement("insert into WebServiceParams " + "(WebServiceId, Position, ParameterName, Type) " + "values (?, ?, ?, ?)");
                pstmt.setInt(1, id);
                pstmt.setInt(2, 0);
                pstmt.setString(3, null);
                pstmt.setInt(4, ws.getReturnType());
                pstmt.executeUpdate();
                for (Iterator it = ws.parametersIterator(); it.hasNext(); ) {
                    WebServiceParameter param = (WebServiceParameter) it.next();
                    pstmt.setInt(2, param.getPosition());
                    pstmt.setString(3, param.getName());
                    pstmt.setInt(4, param.getType());
                    pstmt.executeUpdate();
                }
                conn.commit();
                return id;
            } catch (SQLRuntimeException e) {
                if (conn != null)
                    conn.rollback();
                throw e;
            } finally {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            }
        } catch (SQLRuntimeException e) {
            e.printStackTrace();
            throw new FidoDatabaseRuntimeException(e);
        }
    }
}
