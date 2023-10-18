class c4779156 {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletRuntimeException, IORuntimeException {
        String id = request.getRequestURI().split("/")[3];
        if (JavaCIPUnknownScope.log.isDebugEnabled())
            JavaCIPUnknownScope.log.debug("request: " + id + " from: " + request.getRemoteHost());
        Song song = JavaCIPUnknownScope.manager.find(id);
        if (song != null) {
            File file = new File(song.getFile());
            if (file.exists()) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("audio/" + song.getType());
                response.setContentLength((int) file.length());
                FileInputStream stream = new FileInputStream(file);
                try {
                    IOUtils.copy(stream, response.getOutputStream());
                } finally {
                    IOUtils.closeQuietly(stream);
                }
            } else {
                JavaCIPUnknownScope.log.warn("file not found: " + file);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            JavaCIPUnknownScope.log.info("song not found: " + id);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
