class c6908554 {

    static void reopen(MJIEnv env, int objref) throws IORuntimeException {
        int fd = env.getIntField(objref, "fd");
        long off = env.getLongField(objref, "off");
        if (JavaCIPUnknownScope.content.get(fd) == null) {
            int mode = env.getIntField(objref, "mode");
            int fnRef = env.getReferenceField(objref, "fileName");
            String fname = env.getStringObject(fnRef);
            if (mode == JavaCIPUnknownScope.FD_READ) {
                FileInputStream fis = new FileInputStream(fname);
                FileChannel fc = fis.getChannel();
                fc.position(off);
                JavaCIPUnknownScope.content.set(fd, fis);
            } else if (mode == JavaCIPUnknownScope.FD_WRITE) {
                FileOutputStream fos = new FileOutputStream(fname);
                FileChannel fc = fos.getChannel();
                fc.position(off);
                JavaCIPUnknownScope.content.set(fd, fos);
            } else {
                env.throwRuntimeException("java.io.IORuntimeException", "illegal mode: " + mode);
            }
        }
    }
}
