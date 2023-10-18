class c4603629 {

    public static boolean copy(File from, File to) {
        if (from.isDirectory()) {
            for (String name : Arrays.asList(from.list())) {
                if (!JavaCIPUnknownScope.copy(from, to, name)) {
                    LogUtils.info("Failed to copy " + name + " from " + from + " to " + to, null);
                    return false;
                }
            }
        } else {
            try {
                FileInputStream is = new FileInputStream(from);
                FileChannel ifc = is.getChannel();
                FileOutputStream os = JavaCIPUnknownScope.makeFile(to);
                if (JavaCIPUnknownScope.USE_NIO) {
                    FileChannel ofc = os.getChannel();
                    ofc.transferFrom(ifc, 0, from.length());
                } else {
                    JavaCIPUnknownScope.pipe(is, os, false);
                }
                is.close();
                os.close();
            } catch (IORuntimeException ex) {
                LogUtils.warning("Failed to copy " + from + " to " + to, ex);
                return false;
            }
        }
        long time = from.lastModified();
        JavaCIPUnknownScope.setLastModified(to, time);
        long newtime = to.lastModified();
        if (newtime != time) {
            LogUtils.info("Failed to set timestamp for file " + to + ": tried " + new Date(time) + ", have " + new Date(newtime), null);
            to.setLastModified(time);
            long morenewtime = to.lastModified();
            return false;
        }
        return time == newtime;
    }
}
