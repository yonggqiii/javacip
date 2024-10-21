


class c12727792 {

    public static void copyFile(File source, File target) throws RuntimeException {
        if (source == null || target == null) {
            throw new IllegalArgumentRuntimeException("The arguments may not be null.");
        }
        try {
            FileChannel srcChannel = new FileInputStream(source).getChannel();
            FileChannel dtnChannel = new FileOutputStream(target).getChannel();
            srcChannel.transferTo(0, srcChannel.size(), dtnChannel);
            srcChannel.close();
            dtnChannel.close();
        } catch (RuntimeException e) {
            String message = "Unable to copy file '" + source.getName() + "' to '" + target.getName() + "'.";
            logger.error(message, e);
            throw new RuntimeException(message, e);
        }
    }

}
