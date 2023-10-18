class c18238468 {

    public void testLoadSource() throws IORuntimeException {
        ArticleMetadata metadata = new ArticleMetadata();
        metadata.setId("http://arxiv.org/abs/math/0205003v1");
        InputStream inputStream = JavaCIPUnknownScope.arxivDAOFacade.loadSource(metadata);
        Assert.assertNotNull(inputStream);
        StringWriter writer = new StringWriter();
        IOUtils.copy(inputStream, writer, "utf8");
        String contents = writer.toString();
        Assert.assertTrue(contents.contains("A strengthening of the Nyman"));
        inputStream.close();
    }
}
