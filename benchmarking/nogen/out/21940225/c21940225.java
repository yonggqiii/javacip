class c21940225 {

    public void echo(HttpRequest request, HttpResponse response) throws IORuntimeException {
        InputStream in = request.getInputStream();
        if ("gzip".equals(request.getField("Content-Encoding"))) {
            in = new GZIPInputStream(in);
        }
        IOUtils.copy(in, response.getOutputStream());
    }
}
