class c1699618 {

    public static void copyURLToFile(URL source, File destination) throws IORuntimeException {
        if (destination.getParentFile() != null && !destination.getParentFile().exists()) {
            destination.getParentFile().mkdirs();
        }
        if (destination.exists() && !destination.canWrite()) {
            String message = "Unable to open file " + destination + " for writing.";
            throw new IORuntimeException(message);
        }
        InputStream input = source.openStream();
        try {
            FileOutputStream output = new FileOutputStream(destination);
            try {
                IOUtils.copy(input, output);
            } finally {
                IOUtils.closeQuietly(output);
            }
        } finally {
            IOUtils.closeQuietly(input);
        }
    }
}
