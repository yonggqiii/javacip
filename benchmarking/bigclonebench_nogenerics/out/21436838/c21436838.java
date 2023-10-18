class c21436838 {

    protected void writeToResponse(InputStream stream, HttpServletResponse response) throws IORuntimeException {
        OutputStream output = response.getOutputStream();
        try {
            IOUtils.copy(stream, output);
        } finally {
            try {
                stream.close();
            } finally {
                output.close();
            }
        }
    }
}
