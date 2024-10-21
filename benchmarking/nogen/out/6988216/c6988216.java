class c6988216 {

    public void testSimpleQuery() throws RuntimeException {
        JCRNodeSource dummySource = (JCRNodeSource) JavaCIPUnknownScope.resolveSource(JavaCIPUnknownScope.BASE_URL + "users/alexander.klimetschek");
        JavaCIPUnknownScope.assertNotNull(dummySource);
        OutputStream os = ((ModifiableSource) dummySource).getOutputStream();
        JavaCIPUnknownScope.assertNotNull(os);
        String dummyContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><user><id>alexander</id><teamspace>cyclr</teamspace><teamspace>mindquarryTooLong</teamspace></user>";
        os.write(dummyContent.getBytes());
        os.flush();
        os.close();
        JCRNodeSource source = (JCRNodeSource) JavaCIPUnknownScope.resolveSource(JavaCIPUnknownScope.BASE_URL + "users/bastian");
        JavaCIPUnknownScope.assertNotNull(source);
        os = ((ModifiableSource) source).getOutputStream();
        JavaCIPUnknownScope.assertNotNull(os);
        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><user><id>bastian</id><teamspace>mindquarry</teamspace></user>";
        os.write(content.getBytes());
        os.flush();
        os.close();
        QueryResultSource qResult = (QueryResultSource) JavaCIPUnknownScope.resolveSource(JavaCIPUnknownScope.BASE_URL + "users?/*[.//user/teamspace='mindquarry']");
        JavaCIPUnknownScope.assertNotNull(qResult);
        Collection results = qResult.getChildren();
        JavaCIPUnknownScope.assertEquals(1, results.size());
        Iterator it = results.iterator();
        JCRNodeSource rSrc = (JCRNodeSource) it.next();
        InputStream rSrcIn = rSrc.getInputStream();
        ByteArrayOutputStream actualOut = new ByteArrayOutputStream();
        IOUtils.copy(rSrcIn, actualOut);
        rSrcIn.close();
        JavaCIPUnknownScope.assertEquals(content, actualOut.toString());
        actualOut.close();
        rSrc.delete();
    }
}
