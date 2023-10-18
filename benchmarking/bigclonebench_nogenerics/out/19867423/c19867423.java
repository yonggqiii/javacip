class c19867423 {

    public void testPersistor() throws RuntimeException {
        PreparedStatement ps;
        ps = JavaCIPUnknownScope.connection.prepareStatement("delete from privatadresse");
        ps.executeUpdate();
        ps.close();
        ps = JavaCIPUnknownScope.connection.prepareStatement("delete from adresse");
        ps.executeUpdate();
        ps.close();
        ps = JavaCIPUnknownScope.connection.prepareStatement("delete from person");
        ps.executeUpdate();
        ps.close();
        Persistor p;
        Adresse csd = new LieferAdresse();
        csd.setStrasse("Amalienstrasse 68");
        JavaCIPUnknownScope.modificationTracker.addNewParticipant(csd);
        Person markus = new Person();
        markus.setName("markus");
        JavaCIPUnknownScope.modificationTracker.addNewParticipant(markus);
        markus.getPrivatAdressen().add(csd);
        Person martin = new Person();
        martin.setName("martin");
        JavaCIPUnknownScope.modificationTracker.addNewParticipant(martin);
        p = new Persistor(JavaCIPUnknownScope.getSchemaMapping(), JavaCIPUnknownScope.idGenerator, JavaCIPUnknownScope.modificationTracker);
        p.persist();
        Adresse bia = new LieferAdresse();
        JavaCIPUnknownScope.modificationTracker.addNewParticipant(bia);
        bia.setStrasse("dr. boehringer gasse");
        markus.getAdressen().add(bia);
        bia.setPerson(martin);
        markus.setContactPerson(martin);
        p = new Persistor(JavaCIPUnknownScope.getSchemaMapping(), JavaCIPUnknownScope.idGenerator, JavaCIPUnknownScope.modificationTracker);
        try {
            p.persist();
            JavaCIPUnknownScope.connection.commit();
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.connection.rollback();
            throw e;
        }
    }
}
