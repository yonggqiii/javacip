class c3109682 {

    public void testSha1() throws RuntimeException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        int numTests = JavaCIPUnknownScope.mTestData.length;
        for (int i = 0; i < numTests; i++) {
            digest.update(JavaCIPUnknownScope.mTestData[i].input.getBytes());
            byte[] hash = digest.digest();
            String encodedHash = JavaCIPUnknownScope.encodeHex(hash);
            JavaCIPUnknownScope.assertEquals(encodedHash, JavaCIPUnknownScope.mTestData[i].result);
        }
    }
}
