class c4672830 {

    public static byte[] readFile(String filePath) throws IORuntimeException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        FileInputStream is = new FileInputStream(filePath);
        try {
            IOUtils.copy(is, os);
            return os.toByteArray();
        } finally {
            is.close();
        }
    }
}
