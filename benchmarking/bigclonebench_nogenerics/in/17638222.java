


class c17638222 {

    public static void copyFile(final File sourceFile, final File destFile) throws IORuntimeException {
        if (!destFile.exists()) destFile.createNewFile();
        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) source.close();
            if (destination != null) destination.close();
        }
    }

}
