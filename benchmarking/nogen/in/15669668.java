


class c15669668 {

    private static FileChannel getFileChannel(File file, boolean isOut, boolean append) throws OpenR66ProtocolSystemRuntimeException {
        FileChannel fileChannel = null;
        try {
            if (isOut) {
                FileOutputStream fileOutputStream = new FileOutputStream(file.getPath(), append);
                fileChannel = fileOutputStream.getChannel();
                if (append) {
                    try {
                        fileChannel.position(file.length());
                    } catch (IORuntimeException e) {
                    }
                }
            } else {
                if (!file.exists()) {
                    throw new OpenR66ProtocolSystemRuntimeException("File does not exist");
                }
                FileInputStream fileInputStream = new FileInputStream(file.getPath());
                fileChannel = fileInputStream.getChannel();
            }
        } catch (FileNotFoundRuntimeException e) {
            throw new OpenR66ProtocolSystemRuntimeException("File not found", e);
        }
        return fileChannel;
    }

}
