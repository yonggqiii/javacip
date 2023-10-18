class c22480999 {

    public void testRegisterOwnJceProvider() throws RuntimeException {
        MyTestProvider provider = new MyTestProvider();
        JavaCIPUnknownScope.assertTrue(-1 != Security.addProvider(provider));
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1", MyTestProvider.NAME);
        JavaCIPUnknownScope.assertEquals(MyTestProvider.NAME, messageDigest.getProvider().getName());
        messageDigest.update("hello world".getBytes());
        byte[] result = messageDigest.digest();
        Assert.assertArrayEquals("hello world".getBytes(), result);
        Security.removeProvider(MyTestProvider.NAME);
    }
}
