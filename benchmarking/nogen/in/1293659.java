


class c1293659 {

    public void delete(int id) throws FidoDatabaseRuntimeException {
        try {
            Connection conn = null;
            Statement stmt = null;
            try {
                conn = fido.util.FidoDataSource.getConnection();
                conn.setAutoCommit(false);
                stmt = conn.createStatement();
                String sql = "delete from Instructions where InstructionId = " + id;
                stmt.executeUpdate(sql);
                sql = "delete from InstructionGroups where InstructionId = " + id;
                stmt.executeUpdate(sql);
                conn.commit();
            } catch (SQLRuntimeException e) {
                if (conn != null) conn.rollback();
                throw e;
            } finally {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            }
        } catch (SQLRuntimeException e) {
            throw new FidoDatabaseRuntimeException(e);
        }
    }

}
