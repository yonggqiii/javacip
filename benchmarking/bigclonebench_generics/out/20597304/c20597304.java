class c20597304 {

    public void addPropertyColumns(WCAChannel destination, Set<Property> properties) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Connection con = session.connection();
        try {
            JavaCIPUnknownScope.createPropertyTable(destination);
            JavaCIPUnknownScope.extendPropertyList(destination, properties);
            Statement statement = con.createStatement();
            for (Property property : properties) {
                String propertyName = JavaCIPUnknownScope.removeBadChars(property.getName());
                statement.executeUpdate(JavaCIPUnknownScope.alterTable.format(new Object[] { JavaCIPUnknownScope.getTableName(destination), propertyName, property.getDBColumnType() }));
            }
            con.commit();
            con.close();
            session.close();
        } catch (SQLException e) {
            con.rollback();
            session.close();
            throw e;
        }
    }
}
