class c672082 {

    public boolean copyOneOfMyFile(File f, String dest) {
        if (!JavaCIPUnknownScope.ownsThisFile(f.getName()))
            return false;
        if (!dest.endsWith(File.separator))
            dest = dest.concat(File.separator);
        try {
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(dest + f.getName())));
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
            int len = 1024;
            byte[] read = new byte[len];
            while ((len = in.read(read)) > 0) out.write(read, 0, len);
            out.flush();
            out.close();
            in.close();
            if (!PatchManager.mute)
                System.out.println("file created : " + dest + f.getName());
        } catch (IORuntimeException e) {
            System.out.println("copy directory : " + e);
            return false;
        }
        return true;
    }
}
