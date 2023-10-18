class c11986970 {

    public void Copy() throws IORuntimeException {
        if (!JavaCIPUnknownScope.FileDestination.exists()) {
            JavaCIPUnknownScope.FileDestination.createNewFile();
        }
        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(JavaCIPUnknownScope.FileSource).getChannel();
            destination = new FileOutputStream(JavaCIPUnknownScope.FileDestination).getChannel();
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
