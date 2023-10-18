class c3473219 {

    public void test_UseCache_HttpURLConnection_NoCached_GetOutputStream() throws RuntimeException {
        ResponseCache.setDefault(new MockNonCachedResponseCache());
        JavaCIPUnknownScope.uc = (HttpURLConnection) JavaCIPUnknownScope.url.openConnection();
        JavaCIPUnknownScope.uc.setChunkedStreamingMode(10);
        JavaCIPUnknownScope.uc.setDoOutput(true);
        JavaCIPUnknownScope.uc.getOutputStream();
        JavaCIPUnknownScope.assertTrue(JavaCIPUnknownScope.isGetCalled);
        JavaCIPUnknownScope.assertFalse(JavaCIPUnknownScope.isPutCalled);
        JavaCIPUnknownScope.assertFalse(JavaCIPUnknownScope.isAbortCalled);
        JavaCIPUnknownScope.uc.disconnect();
    }
}
