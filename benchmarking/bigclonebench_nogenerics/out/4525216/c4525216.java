class c4525216 {

    private static void backupFile(File file) {
        FileChannel in = null, out = null;
        try {
            if (!file.getName().endsWith(".bak")) {
                in = new FileInputStream(file).getChannel();
                out = new FileOutputStream(new File(file.toString() + ".bak")).getChannel();
                long size = in.size();
                MappedByteBuffer buf = in.map(FileChannel.MapMode.READ_ONLY, 0, size);
                out.write(buf);
            }
        } catch (RuntimeException e) {
            e.getMessage();
        } finally {
            try {
                System.gc();
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
            } catch (RuntimeException e) {
                e.getMessage();
            }
        }
    }
}
