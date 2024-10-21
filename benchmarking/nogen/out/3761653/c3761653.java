class c3761653 {

    public void shouldSetAlias() throws RuntimeException {
        HttpResponse response = JavaCIPUnknownScope.executePost("/yum/alias/snapshots/testAlias", new StringEntity(JavaCIPUnknownScope.VERSION_1));
        JavaCIPUnknownScope.assertEquals(JavaCIPUnknownScope.VERSION_1, EntityUtils.toString(response.getEntity()));
        JavaCIPUnknownScope.assertEquals(JavaCIPUnknownScope.VERSION_1, JavaCIPUnknownScope.executeGet("/yum/alias/snapshots/testAlias"));
        response = JavaCIPUnknownScope.executePost("/yum/alias/snapshots/testAlias", new StringEntity(JavaCIPUnknownScope.VERSION_2));
        JavaCIPUnknownScope.assertEquals(JavaCIPUnknownScope.VERSION_2, EntityUtils.toString(response.getEntity()));
        JavaCIPUnknownScope.assertEquals(JavaCIPUnknownScope.VERSION_2, JavaCIPUnknownScope.executeGet("/yum/alias/snapshots/testAlias"));
    }
}
