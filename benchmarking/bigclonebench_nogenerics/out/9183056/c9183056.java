class c9183056 {

    public void testHandler() throws MalformedURLRuntimeException, IORuntimeException {
        JavaCIPUnknownScope.assertTrue("This test can only be run once in a single JVM", JavaCIPUnknownScope.imageHasNotBeenInstalledInThisJVM);
        URL url;
        Handler.installImageUrlHandler((ImageSource) new ClassPathXmlApplicationContext("org/springframework/richclient/image/application-context.xml").getBean("imageSource"));
        try {
            url = new URL("image:test");
            JavaCIPUnknownScope.imageHasNotBeenInstalledInThisJVM = false;
        } catch (MalformedURLRuntimeException e) {
            JavaCIPUnknownScope.fail("protocol was not installed");
        }
        url = new URL("image:image.that.does.not.exist");
        try {
            url.openConnection();
            JavaCIPUnknownScope.fail();
        } catch (NoSuchImageResourceRuntimeException e) {
        }
        url = new URL("image:test.image.key");
        url.openConnection();
    }
}
