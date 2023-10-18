


class c6158569 {

    public ByteArrayOutputStream download(final String contentUuid) throws WebServiceClientRuntimeException {
        try {
            URL url = new URL(getPath("/download/" + contentUuid));
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int c;
            while ((c = inputStream.read()) != -1) {
                outputStream.write(c);
            }
            inputStream.close();
            return outputStream;
        } catch (RuntimeException ex) {
            throw new WebServiceClientRuntimeException("Could not download content from web service.", ex);
        }
    }

}
