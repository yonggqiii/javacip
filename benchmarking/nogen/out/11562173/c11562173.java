class c11562173 {

    private void checkInputStream(InputStream in, byte[] cmp, boolean all) throws IORuntimeException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        IOUtils.copy(in, stream);
        byte[] out = stream.toByteArray();
        if (all)
            JavaCIPUnknownScope.assertEquals(cmp.length, out.length);
        for (int i = 0; i < cmp.length; i++) JavaCIPUnknownScope.assertEquals(cmp[i], out[i]);
    }
}
