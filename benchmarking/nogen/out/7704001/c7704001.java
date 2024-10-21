class c7704001 {

    public void deleteGroupInstruction(int id, int rank) throws FidoDatabaseRuntimeException, InstructionNotFoundRuntimeException {
        try {
            Connection conn = null;
            Statement stmt = null;
            try {
                conn = JavaCIPUnknownScope.fido.util.FidoDataSource.getConnection();
                conn.setAutoCommit(false);
                stmt = conn.createStatement();
                String sql = "delete from InstructionGroups " + "where InstructionId = " + id + " and Rank = " + rank;
                stmt.executeUpdate(sql);
                JavaCIPUnknownScope.bumpAllRowsUp(stmt, id, rank);
                conn.commit();
            } catch (SQLRuntimeException e) {
                if (conn != null)
                    conn.rollback();
                throw e;
            } finally {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            }
        } catch (SQLRuntimeException e) {
            throw new FidoDatabaseRuntimeException(e);
        }
    }
}
