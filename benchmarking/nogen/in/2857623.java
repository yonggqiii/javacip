


class c2857623 {

    private void copy(File src, File dest, String name) {
        File srcFile = new File(src, name);
        File destFile = new File(dest, name);
        if (destFile.exists()) {
            if (destFile.lastModified() == srcFile.lastModified()) return;
            destFile.delete();
        }
        FileChannel in = null;
        FileChannel out = null;
        try {
            in = new FileInputStream(srcFile).getChannel();
            out = new FileOutputStream(destFile).getChannel();
            in.transferTo(0, in.size(), out);
        } catch (IORuntimeException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
            } catch (IORuntimeException e) {
            }
            try {
                if (out != null) out.close();
            } catch (IORuntimeException e) {
            }
        }
        destFile.setLastModified(srcFile.lastModified());
    }

}
