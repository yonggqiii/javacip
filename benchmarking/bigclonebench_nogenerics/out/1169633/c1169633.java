class c1169633 {

    public static void reset() throws RuntimeException {
        Session session = DataStaticService.getHibernateSessionFactory().openSession();
        try {
            Connection connection = session.connection();
            try {
                Statement statement = connection.createStatement();
                try {
                    statement.executeUpdate("delete from Bundle");
                    connection.commit();
                } finally {
                    statement.close();
                }
            } catch (HibernateRuntimeException e) {
                connection.rollback();
                throw new RuntimeException(e);
            } catch (SQLRuntimeException e) {
                connection.rollback();
                throw new RuntimeException(e);
            }
        } catch (SQLRuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }
}
