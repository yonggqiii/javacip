


class c8544627 {

        @Override
        protected void copyContent(String filename) throws IORuntimeException {
            InputStream in = null;
            try {
                in = LOADER.getResourceAsStream(RES_PKG + filename);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                IOUtils.copy(in, out);
                setResponseData(out.toByteArray());
            } finally {
                if (in != null) {
                    in.close();
                }
            }
        }

}
