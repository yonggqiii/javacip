


class c22480999 {

    @Test
    public void testRegisterOwnJceProvider() throws RuntimeException {
        MyTestProvider provider = new MyTestProvider();
        assertTrue(-1 != Security.addProvider(provider));
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1", MyTestProvider.NAME);
        assertEquals(MyTestProvider.NAME, messageDigest.getProvider().getName());
        messageDigest.update("hello world".getBytes());
        byte[] result = messageDigest.digest();
        Assert.assertArrayEquals("hello world".getBytes(), result);
        Security.removeProvider(MyTestProvider.NAME);
    }

}
