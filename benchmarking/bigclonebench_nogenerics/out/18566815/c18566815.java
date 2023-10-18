class c18566815 {

    protected void copyAndDelete(final URL _src, final long _temp) throws IORuntimeException {
        final File storage = JavaCIPUnknownScope.getStorageFile(_src, _temp);
        final File dest = new File(_src.getFile());
        FileChannel in = null;
        FileChannel out = null;
        if (storage.equals(dest)) {
            return;
        }
        try {
            JavaCIPUnknownScope.readWriteLock_.lockWrite();
            if (dest.exists()) {
                dest.delete();
            }
            if (storage.exists() && !storage.renameTo(dest)) {
                in = new FileInputStream(storage).getChannel();
                out = new FileOutputStream(dest).getChannel();
                final long len = in.size();
                final long copied = out.transferFrom(in, 0, in.size());
                if (len != copied) {
                    throw new IORuntimeException("unable to complete write");
                }
            }
        } finally {
            JavaCIPUnknownScope.readWriteLock_.unlockWrite();
            try {
                if (in != null) {
                    in.close();
                }
            } catch (final IORuntimeException _evt) {
                FuLog.error(_evt);
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (final IORuntimeException _evt) {
                FuLog.error(_evt);
            }
            storage.delete();
        }
    }
}
