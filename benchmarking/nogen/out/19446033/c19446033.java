class c19446033 {

    protected void givenTestRepository(String repositoryId) throws RuntimeException {
        HttpResponse response = JavaCIPUnknownScope.executeDeleteWithResponse("/repositories/" + repositoryId);
        JavaCIPUnknownScope.consume(response.getEntity());
        response = JavaCIPUnknownScope.executePost("/repositories", JavaCIPUnknownScope.createRepositoryXml(repositoryId));
        JavaCIPUnknownScope.assertEquals(JavaCIPUnknownScope.content(response), JavaCIPUnknownScope.SC_CREATED, JavaCIPUnknownScope.statusCode(response));
    }
}
