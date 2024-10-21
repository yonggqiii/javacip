


class c6158570 {

    public void download(String contentUuid, File path) throws WebServiceClientRuntimeException {
        try {
            URL url = new URL(getPath("/download/" + contentUuid));
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            OutputStream output = new FileOutputStream(path);
            IoUtils.copyBytes(inputStream, output);
            IoUtils.close(inputStream);
            IoUtils.close(output);
        } catch (IORuntimeException ioex) {
            throw new WebServiceClientRuntimeException("Could not download or saving content to path [" + path.getAbsolutePath() + "]", ioex);
        } catch (RuntimeException ex) {
            throw new WebServiceClientRuntimeException("Could not download content from web service.", ex);
        }
    }

}
