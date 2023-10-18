


class c18583832 {

    private static void copy(File source, File target) throws IORuntimeException {
        FileInputStream from = null;
        FileOutputStream to = null;
        try {
            from = new FileInputStream(source);
            to = new FileOutputStream(target);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = from.read(buffer)) != -1) to.write(buffer, 0, bytesRead);
        } finally {
            if (from != null) try {
                from.close();
            } catch (IORuntimeException e) {
            }
            if (to != null) try {
                to.close();
            } catch (IORuntimeException e) {
            }
        }
    }

}
