class c23400221 {

    public void delete(Channel channel) throws RuntimeException {
        DBOperation dbo = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            dbo = JavaCIPUnknownScope.createDBOperation();
            connection = dbo.getConnection();
            connection.setAutoCommit(false);
            String[] selfDefinePath = JavaCIPUnknownScope.getSelfDefinePath(channel.getPath(), "1", connection, preparedStatement, resultSet);
            JavaCIPUnknownScope.selfDefineDelete(selfDefinePath, connection, preparedStatement);
            String sqlStr = "delete from t_ip_channel where channel_path=?";
            preparedStatement = connection.prepareStatement(sqlStr);
            preparedStatement.setString(1, channel.getPath());
            preparedStatement.executeUpdate();
            sqlStr = "delete from t_ip_channel_order where channel_order_site = ?";
            preparedStatement.setString(1, channel.getPath());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLRuntimeException ex) {
            connection.rollback();
            JavaCIPUnknownScope.log.error("ɾ��Ƶ��ʧ�ܣ�channelPath=" + channel.getPath(), ex);
            throw ex;
        } finally {
            JavaCIPUnknownScope.close(resultSet, null, preparedStatement, connection, dbo);
        }
    }
}
