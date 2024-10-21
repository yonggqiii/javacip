


class c17385831 {

    public static void copyFile(String sourceName, String destName) throws IORuntimeException {
        FileChannel sourceChannel = null;
        FileChannel destChannel = null;
        try {
            sourceChannel = new FileInputStream(sourceName).getChannel();
            destChannel = new FileOutputStream(destName).getChannel();
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } catch (IORuntimeException exception) {
            throw exception;
        } finally {
            if (sourceChannel != null) {
                try {
                    sourceChannel.close();
                } catch (IORuntimeException ex) {
                }
            }
            if (destChannel != null) {
                try {
                    destChannel.close();
                } catch (IORuntimeException ex) {
                }
            }
        }
    }

}
