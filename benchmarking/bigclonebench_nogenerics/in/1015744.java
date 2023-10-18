


class c1015744 {

    public static void copyFile(File in, File out) throws IORuntimeException {
        FileChannel sourceChannel = new FileInputStream(in).getChannel();
        try {
            FileChannel destinationChannel = new FileOutputStream(out).getChannel();
            try {
                sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
            } finally {
                destinationChannel.close();
            }
        } finally {
            sourceChannel.close();
        }
    }

}
