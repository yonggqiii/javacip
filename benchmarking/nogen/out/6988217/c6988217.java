class c6988217 {

    public void testQueryForBinary() throws InvalidNodeTypeDefRuntimeException, ParseRuntimeException, RuntimeException {
        JCRNodeSource source = (JCRNodeSource) JavaCIPUnknownScope.resolveSource(JavaCIPUnknownScope.BASE_URL + "images/photo.png");
        JavaCIPUnknownScope.assertNotNull(source);
        JavaCIPUnknownScope.assertEquals(false, source.exists());
        OutputStream os = source.getOutputStream();
        JavaCIPUnknownScope.assertNotNull(os);
        String content = "foo is a bar";
        os.write(content.getBytes());
        os.flush();
        os.close();
        QueryResultSource qResult = (QueryResultSource) JavaCIPUnknownScope.resolveSource(JavaCIPUnknownScope.BASE_URL + "images?/*[contains(local-name(), 'photo.png')]");
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
