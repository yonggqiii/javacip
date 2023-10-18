


class c12135654 {

                @Override
                protected URLConnection openConnection(URL url) throws IORuntimeException {
                    return new URLConnection(url) {

                        @Override
                        public void connect() throws IORuntimeException {
                        }

                        @Override
                        public InputStream getInputStream() throws IORuntimeException {
                            ThemeResource f = getFacelet(getURL().getFile());
                            return new ByteArrayInputStream(f.getText().getBytes());
                        }
                    };
                }

}
