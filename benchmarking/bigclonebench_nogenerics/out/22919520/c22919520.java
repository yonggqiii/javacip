class c22919520 {

    public static void copyFile(String sourceFilePath, String destFilePath) throws IORuntimeException {
        FileChannel in = null;
        FileChannel out = null;
        try {
            in = new FileInputStream(sourceFilePath).getChannel();
            out = new FileOutputStream(destFilePath).getChannel();
            long inputSize = in.size();
            in.transferTo(0, inputSize, out);
            MappedByteBuffer buf = in.map(FileChannel.MapMode.READ_ONLY, 0, inputSize);
            out.write(buf);
        } finally {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
        }
    }
}
