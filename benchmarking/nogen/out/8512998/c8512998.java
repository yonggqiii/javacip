class c8512998 {

    public boolean updateCalculatedHand(CalculateTransferObject query, BasicStartingHandTransferObject[] hands) throws CalculateDAORuntimeException {
        boolean retval = false;
        Connection connection = null;
        Statement statement = null;
        PreparedStatement prep = null;
        ResultSet result = null;
        StringBuffer sql = new StringBuffer(JavaCIPUnknownScope.SELECT_ID_SQL);
        sql.append(JavaCIPUnknownScope.appendQuery(query));
        try {
            connection = JavaCIPUnknownScope.getDataSource().getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            result = statement.executeQuery(sql.toString());
            if (result.first()) {
                String id = result.getString("id");
                prep = connection.prepareStatement(JavaCIPUnknownScope.UPDATE_HANDS_SQL);
                for (int i = 0; i < hands.length; i++) {
                    prep.setInt(1, hands[i].getWins());
                    prep.setInt(2, hands[i].getLoses());
                    prep.setInt(3, hands[i].getDraws());
                    prep.setString(4, id);
                    prep.setString(5, hands[i].getHand());
                    if (prep.executeUpdate() != 1) {
                        throw new SQLRuntimeException("updated too many records in calculatehands, " + id + "-" + hands[i].getHand());
                    }
                }
                connection.commit();
            }
        } catch (SQLRuntimeException sqle) {
            try {
                connection.rollback();
            } catch (SQLRuntimeException e) {
                e.setNextRuntimeException(sqle);
                throw new CalculateDAORuntimeException(e);
            }
            throw new CalculateDAORuntimeException(sqle);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLRuntimeException e) {
                    throw new CalculateDAORuntimeException(e);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLRuntimeException e) {
                    throw new CalculateDAORuntimeException(e);
                }
            }
            if (prep != null) {
                try {
                    prep.close();
                } catch (SQLRuntimeException e) {
                    throw new CalculateDAORuntimeException(e);
                }
            }
        }
        return retval;
    }
}
