


class c12380475 {

    public void transport(File file) throws TransportRuntimeException {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    transport(file);
                }
            } else if (file.isFile()) {
                try {
                    FileChannel inChannel = new FileInputStream(file).getChannel();
                    FileChannel outChannel = new FileOutputStream(destinationDir).getChannel();
                    inChannel.transferTo(0, inChannel.size(), outChannel);
                } catch (IORuntimeException e) {
                    log.error("File transfer failed", e);
                }
            }
        }
    }

}
