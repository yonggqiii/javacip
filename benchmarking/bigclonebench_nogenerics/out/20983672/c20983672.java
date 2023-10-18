class c20983672 {

    public void sendResponse(DjdocRequest req, HttpServletResponse res) throws IORuntimeException {
        File file = (File) req.getResult();
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            IOUtils.copy(in, res.getOutputStream());
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }
}
