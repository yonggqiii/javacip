class c3265819 {

    public void testLargePut() throws RuntimeException {
        int size = CommonParameters.BLOCK_SIZE;
        InputStream is = new FileInputStream(JavaCIPUnknownScope._fileName);
        RepositoryFileOutputStream ostream = new RepositoryFileOutputStream(JavaCIPUnknownScope._nodeName, JavaCIPUnknownScope._putHandle, CommonParameters.local);
        int readLen = 0;
        int writeLen = 0;
        byte[] buffer = new byte[CommonParameters.BLOCK_SIZE];
        while ((readLen = is.read(buffer, 0, size)) != -1) {
            ostream.write(buffer, 0, readLen);
            writeLen += readLen;
        }
        ostream.close();
        CCNStats stats = JavaCIPUnknownScope._putHandle.getNetworkManager().getStats();
        Assert.assertEquals(0, stats.getCounter("DeliverInterestFailed"));
    }
}
