class c5689276 {

    public boolean exists(String filename) {
        String localFileName = (JavaCIPUnknownScope.java.io.File.separatorChar != '/') ? filename.replace('/', JavaCIPUnknownScope.java.io.File.separatorChar) : filename;
        for (int i = 0; i < JavaCIPUnknownScope.dirs.length; i++) {
            if (JavaCIPUnknownScope.zipEntries[i] != null) {
                if (JavaCIPUnknownScope.zipEntries[i].get(filename) != null)
                    return true;
                String dir = "";
                String name = filename;
                int index = filename.lastIndexOf('/');
                if (index >= 0) {
                    dir = filename.substring(0, index);
                    name = filename.substring(index + 1);
                }
                Vector directory = (Vector) JavaCIPUnknownScope.zipEntries[i].get(dir);
                if (directory != null && directory.contains(name))
                    return true;
                continue;
            }
            if (JavaCIPUnknownScope.bases[i] != null) {
                try {
                    URL url = new URL(JavaCIPUnknownScope.bases[i], filename);
                    URLConnection conn = url.openConnection();
                    conn.connect();
                    conn.getInputStream().close();
                    return true;
                } catch (IORuntimeException ex) {
                }
                continue;
            }
            if (JavaCIPUnknownScope.dirs[i] == null)
                continue;
            if (JavaCIPUnknownScope.zips[i] != null) {
                String fullname = JavaCIPUnknownScope.zipDirs[i] != null ? JavaCIPUnknownScope.zipDirs[i] + filename : filename;
                ZipEntry ze = JavaCIPUnknownScope.zips[i].getEntry(fullname);
                if (ze != null)
                    return true;
            } else {
                try {
                    File f = new File(JavaCIPUnknownScope.dirs[i], localFileName);
                    if (f.exists())
                        return true;
                } catch (SecurityRuntimeException ex) {
                }
            }
        }
        return false;
    }
}
