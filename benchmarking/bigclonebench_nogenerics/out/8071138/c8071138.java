class c8071138 {

    public void sendContent(OutputStream out, Range range, Map map, String string) throws IORuntimeException, NotAuthorizedRuntimeException, BadRequestRuntimeException {
        System.out.println("sendContent " + JavaCIPUnknownScope.file);
        RFileInputStream in = new RFileInputStream(JavaCIPUnknownScope.file);
        try {
            IOUtils.copyLarge(in, out);
        } finally {
            in.close();
        }
    }
}
