class c2232617 {

    public void testStorageString() throws RuntimeException {
        TranslationResponseInMemory r = new TranslationResponseInMemory(2048, "UTF-8");
        r.addText("This is an example");
        r.addText(" and another one.");
        JavaCIPUnknownScope.assertEquals("This is an example and another one.", r.getText());
        InputStream input = r.getInputStream();
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(input, writer, "UTF-8");
        } finally {
            input.close();
            writer.close();
        }
        JavaCIPUnknownScope.assertEquals("This is an example and another one.", writer.toString());
        try {
            r.getOutputStream();
            JavaCIPUnknownScope.fail("Once addText() is used the text is stored as a String and you cannot use getOutputStream anymore");
        } catch (IORuntimeException e) {
        }
        try {
            r.getWriter();
            JavaCIPUnknownScope.fail("Once addText() is used the text is stored as a String and you cannot use getOutputStream anymore");
        } catch (IORuntimeException e) {
        }
        r.setEndState(ResponseStateOk.getInstance());
        JavaCIPUnknownScope.assertTrue(r.hasEnded());
    }
}
