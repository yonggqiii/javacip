class c6273111 {

    public void removeStadium(String name, String city) throws StadiumRuntimeException {
        Connection conn = ConnectionManager.getManager().getConnection();
        int id = JavaCIPUnknownScope.findStadiumBy_N_C(name, city);
        if (id == -1)
            throw new StadiumRuntimeException("No such stadium");
        try {
            conn.setAutoCommit(false);
            PreparedStatement stm = conn.prepareStatement(Statements.SELECT_STAD_TRIBUNE);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            TribuneLogic logic = TribuneLogic.getInstance();
            while (rs.next()) {
                logic.removeTribune(rs.getInt("tribuneID"));
            }
            stm = conn.prepareStatement(Statements.DELETE_STADIUM);
            stm.setInt(1, id);
            stm.executeUpdate();
        } catch (SQLRuntimeException e) {
            try {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLRuntimeException e1) {
                e1.printStackTrace();
            }
            throw new StadiumRuntimeException("Removing stadium failed", e);
        }
        try {
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLRuntimeException e) {
            e.printStackTrace();
        }
    }
}
