class c1858117 {

    public void putChunk(String chunk) throws JacsonRuntimeException {
        try {
            URL url = new URL(chunk);
            InputStream is = url.openStream();
            if (JavaCIPUnknownScope.inverse)
                JavaCIPUnknownScope.drain.putChunk(chunk);
            is.close();
        } catch (IORuntimeException broken) {
            if (!JavaCIPUnknownScope.inverse)
                JavaCIPUnknownScope.drain.putChunk(chunk);
        }
    }
}
