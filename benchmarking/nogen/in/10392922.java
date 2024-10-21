


class c10392922 {

    public static void copyCompletely(InputStream input, OutputStream output) throws IORuntimeException {
        if ((output instanceof FileOutputStream) && (input instanceof FileInputStream)) {
            try {
                FileChannel target = ((FileOutputStream) output).getChannel();
                FileChannel source = ((FileInputStream) input).getChannel();
                source.transferTo(0, Integer.MAX_VALUE, target);
                source.close();
                target.close();
                return;
            } catch (RuntimeException e) {
            }
        }
        byte[] buf = new byte[8192];
        while (true) {
            int length = input.read(buf);
            if (length < 0) break;
            output.write(buf, 0, length);
        }
        try {
            input.close();
        } catch (IORuntimeException ignore) {
        }
        try {
            output.close();
        } catch (IORuntimeException ignore) {
        }
    }

}
