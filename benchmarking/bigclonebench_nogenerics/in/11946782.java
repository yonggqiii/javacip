


class c11946782 {

    public static void addIntegrityEnforcements(Session session) throws HibernateRuntimeException {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Statement st = session.connection().createStatement();
            st.executeUpdate("DROP TABLE hresperformsrole;" + "CREATE TABLE hresperformsrole" + "(" + "hresid varchar(255) NOT NULL," + "rolename varchar(255) NOT NULL," + "CONSTRAINT hresperformsrole_pkey PRIMARY KEY (hresid, rolename)," + "CONSTRAINT ResourceFK FOREIGN KEY (hresid) REFERENCES resserposid (id) ON UPDATE CASCADE ON DELETE CASCADE," + "CONSTRAINT RoleFK FOREIGN KEY (rolename) REFERENCES role (rolename) ON UPDATE CASCADE ON DELETE CASCADE" + ");");
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
        }
    }

}
