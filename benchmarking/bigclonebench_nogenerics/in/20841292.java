


class c20841292 {

    public void fileCopy(File src, File dest) throws IORuntimeException {
        if (!dest.exists()) {
            final File parent = new File(dest.getParent());
            if (!parent.exists() && !parent.mkdirs()) {
                throw new IORuntimeException();
            }
            if (!dest.createNewFile()) {
            }
        }
        FileInputStream is = null;
        FileOutputStream os = null;
        try {
            is = new FileInputStream(src);
            os = new FileOutputStream(dest);
            final FileChannel srcChannel = is.getChannel();
            final FileChannel dstChannel = os.getChannel();
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
            srcChannel.close();
            dstChannel.close();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } finally {
                if (os != null) {
                    os.close();
                }
            }
        }
    }

}
