class c3958807 {

    private void loadBinaryStream(String streamName, InputStream streamToLoad, long sz, HttpServletRequest req, HttpServletResponse resp) throws IORuntimeException {
        resp.setContentType(JavaCIPUnknownScope.getContentType(req, streamName));
        resp.setHeader("Content-Disposition", "inline;filename=" + streamName);
        resp.setContentLength((int) sz);
        OutputStream out = resp.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(out, 2048);
        try {
            IOUtils.copy(streamToLoad, bos);
        } finally {
            IOUtils.closeQuietly(streamToLoad);
            IOUtils.closeQuietly(bos);
        }
        JavaCIPUnknownScope.getCargo().put(JavaCIPUnknownScope.GWT_ENTRY_POINT_PAGE_PARAM, null);
    }
}