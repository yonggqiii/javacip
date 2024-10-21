class c19488257 {

    public static boolean copy(final File from, final File to) {
        if (from.isDirectory()) {
            to.mkdirs();
            for (final String name : Arrays.asList(from.list())) {
                if (!JavaCIPUnknownScope.copy(from, to, name)) {
                    if (JavaCIPUnknownScope.COPY_DEBUG) {
                        System.out.println("Failed to copy " + name + " from " + from + " to " + to);
                    }
                    return false;
                }
            }
        } else {
            try {
                final FileInputStream is = new FileInputStream(from);
                final FileChannel ifc = is.getChannel();
                final FileOutputStream os = JavaCIPUnknownScope.makeFile(to);
                if (JavaCIPUnknownScope.USE_NIO) {
                    final FileChannel ofc = os.getChannel();
                    ofc.transferFrom(ifc, 0, from.length());
                } else {
                    JavaCIPUnknownScope.pipe(is, os, false);
                }
                is.close();
                os.close();
            } catch (final IORuntimeException ex) {
                if (JavaCIPUnknownScope.COPY_DEBUG) {
                    System.out.println("Failed to copy " + from + " to " + to + ": " + ex);
                }
                return false;
            }
        }
        final long time = from.lastModified();
        JavaCIPUnknownScope.setLastModified(to, time);
        final long newtime = to.lastModified();
        if (JavaCIPUnknownScope.COPY_DEBUG) {
            if (newtime != time) {
                System.out.println("Failed to set timestamp for file " + to + ": tried " + new Date(time) + ", have " + new Date(newtime));
                to.setLastModified(time);
                final long morenewtime = to.lastModified();
                return false;
            } else {
                System.out.println("Timestamp for " + to + " set successfully.");
            }
        }
        return time == newtime;
    }
}
