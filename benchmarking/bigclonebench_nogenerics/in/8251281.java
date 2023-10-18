


class c8251281 {

    protected InputStream openInputStream(String filename) throws FileNotFoundRuntimeException {
        InputStream in = null;
        try {
            URL url = new URL(filename);
            in = url.openConnection().getInputStream();
            logger.info("Opening file " + filename);
        } catch (FileNotFoundRuntimeException e) {
            logger.error("Resource file not found: " + filename);
            throw e;
        } catch (IORuntimeException e) {
            logger.error("Resource file can not be readed: " + filename);
            throw new FileNotFoundRuntimeException("Resource file can not be readed: " + filename);
        }
        if (in == null) {
            logger.error("Resource file not found: " + filename);
            throw new FileNotFoundRuntimeException(filename);
        }
        return in;
    }

}
