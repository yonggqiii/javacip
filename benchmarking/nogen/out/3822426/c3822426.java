class c3822426 {

    public int addPermissionsForUserAndAgenda(Integer userId, Integer agendaId, String permissions) throws TechnicalRuntimeException {
        if (permissions == null) {
            throw new TechnicalRuntimeException(new RuntimeException(new RuntimeException("Column 'permissions' cannot be null")));
        }
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getCurrentSession();
            transaction = session.beginTransaction();
            String query = "INSERT INTO j_user_agenda (userId, agendaId, permissions) VALUES(" + userId + "," + agendaId + ",\"" + permissions + "\")";
            Statement statement = session.connection().createStatement();
            int rowsUpdated = statement.executeUpdate(query);
            transaction.commit();
            return rowsUpdated;
        } catch (HibernateRuntimeException ex) {
            if (transaction != null)
                transaction.rollback();
            throw new TechnicalRuntimeException(ex);
        } catch (SQLRuntimeException e) {
            if (transaction != null)
                transaction.rollback();
            throw new TechnicalRuntimeException(e);
        }
    }
}
