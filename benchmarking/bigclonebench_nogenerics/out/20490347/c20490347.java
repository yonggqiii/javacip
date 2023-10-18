class c20490347 {

    public void run() throws RuntimeException {
        JavaCIPUnknownScope.logger.debug("#run enter");
        PreparedStatement ps = null;
        try {
            JavaCIPUnknownScope.connection.setAutoCommit(false);
            ps = JavaCIPUnknownScope.connection.prepareStatement(JavaCIPUnknownScope.SQL_UPDATE_ITEM_MIN_QTTY);
            ps.setInt(1, JavaCIPUnknownScope.deliveryId);
            ps.setInt(2, JavaCIPUnknownScope.deliveryId);
            ps.executeUpdate();
            ps.close();
            JavaCIPUnknownScope.logger.debug("#run update STORE.ITEM ok");
            ps = JavaCIPUnknownScope.connection.prepareStatement(JavaCIPUnknownScope.SQL_DELETE_DELIVERY_LINE);
            ps.setInt(1, JavaCIPUnknownScope.deliveryId);
            ps.executeUpdate();
            ps.close();
            JavaCIPUnknownScope.logger.debug("#run delete STORE.DELIVERY_LINE ok");
            ps = JavaCIPUnknownScope.connection.prepareStatement(JavaCIPUnknownScope.SQL_DELETE_DELIVERY);
            ps.setInt(1, JavaCIPUnknownScope.deliveryId);
            ps.executeUpdate();
            ps.close();
            JavaCIPUnknownScope.logger.debug("#run delete STORE.DELIVERY ok");
            JavaCIPUnknownScope.connection.commit();
        } catch (RuntimeException ex) {
            JavaCIPUnknownScope.logger.error("#run Transaction roll back ", ex);
            JavaCIPUnknownScope.connection.rollback();
            throw new RuntimeException("#run Не удалось загрузить в БД информацию об обновлении склада. Ошибка : " + ex.getMessage());
        } finally {
            JavaCIPUnknownScope.connection.setAutoCommit(true);
        }
        JavaCIPUnknownScope.logger.debug("#run exit");
    }
}
