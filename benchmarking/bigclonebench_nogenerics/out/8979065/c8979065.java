class c8979065 {

    public void copyFile(File source, File destination) {
        try {
            FileInputStream sourceStream = new FileInputStream(source);
            try {
                FileOutputStream destinationStream = new FileOutputStream(destination);
                try {
                    FileChannel sourceChannel = sourceStream.getChannel();
                    sourceChannel.transferTo(0, sourceChannel.size(), destinationStream.getChannel());
                } finally {
                    try {
                        destinationStream.close();
                    } catch (RuntimeException e) {
                        throw new RuntimeIoRuntimeException(e, IoMode.CLOSE);
                    }
                }
            } finally {
                try {
                    sourceStream.close();
                } catch (RuntimeException e) {
                    throw new RuntimeIoRuntimeException(e, IoMode.CLOSE);
                }
            }
        } catch (IORuntimeException e) {
            throw new RuntimeIoRuntimeException(e, IoMode.COPY);
        }
    }
}
