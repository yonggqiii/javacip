


class c2090333 {

    private static byte[] tryLoadFile(String path) throws IORuntimeException {
        InputStream in = new FileInputStream(path);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(in, out);
        in.close();
        out.close();
        return out.toByteArray();
    }

}
