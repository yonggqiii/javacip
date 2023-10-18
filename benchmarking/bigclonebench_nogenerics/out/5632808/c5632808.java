class c5632808 {

    public void respondGet(HttpServletResponse resp) throws IORuntimeException {
        JavaCIPUnknownScope.setHeaders(resp);
        final OutputStream os;
        if (JavaCIPUnknownScope.willDeflate()) {
            resp.setHeader("Content-Encoding", "gzip");
            os = new GZIPOutputStream(resp.getOutputStream(), JavaCIPUnknownScope.bufferSize);
        } else
            os = resp.getOutputStream();
        JavaCIPUnknownScope.transferStreams(JavaCIPUnknownScope.url.openStream(), os);
    }
}
