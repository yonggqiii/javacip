class c2232619 {

    public void testStorageStringWriter() throws RuntimeException {
        TranslationResponseInMemory r = new TranslationResponseInMemory(2048, "UTF-8");
        {
            Writer w = r.getWriter();
            w.write("This is an example");
            w.write(" and another one.");
            w.flush();
            JavaCIPUnknownScope.assertEquals("This is an example and another one.", r.getText());
        }
        {
            InputStream input = r.getInputStream();
            StringWriter writer = new StringWriter();
            try {
                IOUtils.copy(input, writer, "UTF-8");
            } finally {
                input.close();
                writer.close();
            }
            JavaCIPUnknownScope.assertEquals("This is an example and another one.", writer.toString());
        }
        try {
            r.getOutputStream();
            JavaCIPUnknownScope.fail("Is not allowed as you already called getWriter().");
        } catch (IORuntimeException e) {
        }
        {
            Writer output = r.getWriter();
            output.write(" and another line");
            output.write(" and write some more");
            JavaCIPUnknownScope.assertEquals("This is an example and another one. and another line and write some more", r.getText());
        }
        {
            r.addText(" and some more.");
            JavaCIPUnknownScope.assertEquals("This is an example and another one. and another line and write some more and some more.", r.getText());
        }
        r.setEndState(ResponseStateOk.getInstance());
        JavaCIPUnknownScope.assertEquals(ResponseStateOk.getInstance(), r.getEndState());
        try {
            r.getWriter();
            JavaCIPUnknownScope.fail("Previous line should throw IORuntimeException as result closed.");
        } catch (IORuntimeException e) {
        }
    }
}
