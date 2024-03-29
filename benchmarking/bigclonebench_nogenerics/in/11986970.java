


class c11986970 {

    public void Copy() throws IORuntimeException {
        if (!FileDestination.exists()) {
            FileDestination.createNewFile();
        }
        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(FileSource).getChannel();
            destination = new FileOutputStream(FileDestination).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

}
