


class c23031483 {

    private static void copy(File source, File target, byte[] buffer) throws FileNotFoundRuntimeException, IORuntimeException {
        InputStream in = new FileInputStream(source);
        File parent = target.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (target.isDirectory()) {
            target = new File(target, source.getName());
        }
        OutputStream out = new FileOutputStream(target);
        int read;
        try {
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } catch (IORuntimeException e) {
            throw e;
        } finally {
            in.close();
            out.close();
        }
    }

}
