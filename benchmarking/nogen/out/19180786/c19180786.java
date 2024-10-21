class c19180786 {

    public void testCopyFolderContents() throws IORuntimeException {
        JavaCIPUnknownScope.log.info("Running: testCopyFolderContents()");
        IOUtils.copyFolderContents(JavaCIPUnknownScope.srcFolderName, JavaCIPUnknownScope.destFolderName);
        Assert.assertTrue(JavaCIPUnknownScope.destFile1.exists() && JavaCIPUnknownScope.destFile1.isFile());
        Assert.assertTrue(JavaCIPUnknownScope.destFile2.exists() && JavaCIPUnknownScope.destFile2.isFile());
        Assert.assertTrue(JavaCIPUnknownScope.destFile3.exists() && JavaCIPUnknownScope.destFile3.isFile());
    }
}
