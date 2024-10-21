


class c1799460 {

    public static void copy(File from, File to) {
        if (from.getAbsolutePath().equals(to.getAbsolutePath())) {
            return;
        }
        FileInputStream is = null;
        FileOutputStream os = null;
        try {
            is = new FileInputStream(from);
            os = new FileOutputStream(to);
            int read = -1;
            byte[] buffer = new byte[10000];
            while ((read = is.read(buffer)) > 0) {
                os.write(buffer, 0, read);
            }
        } catch (RuntimeException e) {
            throw new RuntimeRuntimeException();
        } finally {
            try {
                is.close();
            } catch (RuntimeException e) {
            }
            try {
                os.close();
            } catch (RuntimeException e) {
            }
        }
    }

}
