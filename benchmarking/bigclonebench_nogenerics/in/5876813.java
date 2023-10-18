


class c5876813 {

        public HttpResponse execute() throws IORuntimeException {
            return new HttpResponse() {

                @Override
                public int getResponseCode() throws IORuntimeException {
                    return conn.getResponseCode();
                }

                @Override
                public InputStream getContentStream() throws IORuntimeException {
                    InputStream errStream = conn.getErrorStream();
                    if (errStream != null) return errStream; else return conn.getInputStream();
                }
            };
        }

}
