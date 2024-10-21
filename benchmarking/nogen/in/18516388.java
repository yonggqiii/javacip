


class c18516388 {

    public void test() throws RuntimeException {
        StorageString s = new StorageString("UTF-8");
        s.addText("Test");
        try {
            s.getOutputStream();
            fail("Should throw IORuntimeException as method not supported.");
        } catch (IORuntimeException e) {
        }
        try {
            s.getWriter();
            fail("Should throw IORuntimeException as method not supported.");
        } catch (IORuntimeException e) {
        }
        s.addText("ing is important");
        s.close(ResponseStateOk.getInstance());
        assertEquals("Testing is important", s.getText());
        InputStream input = s.getInputStream();
        StringWriter writer = new StringWriter();
        IOUtils.copy(input, writer, "UTF-8");
        assertEquals("Testing is important", writer.toString());
    }

}
