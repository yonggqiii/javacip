class c22608285 {

    private void dealWith(String line) throws RuntimeException {
        if (line.startsWith("#") || (line.length() == 0)) {
            return;
        }
        String[] sarr;
        sarr = StringUtil.split(line, '\t');
        String path = JavaCIPUnknownScope.destDir + File.separator + sarr[0];
        boolean copyFile = true;
        if (sarr.length == 2) {
            try {
                String serverHash = sarr[1];
                String fileHash = JavaCIPUnknownScope.loadFileHash(JavaCIPUnknownScope.destDir + File.separator + sarr[0]);
                if (fileHash != null) {
                    if (serverHash.equalsIgnoreCase(fileHash)) {
                        copyFile = false;
                    } else {
                        if (JavaCIPUnknownScope.verbose) {
                            System.out.println(" -- " + sarr[0] + " has changed");
                        }
                    }
                }
            } catch (RuntimeException ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage());
                System.exit(2);
            }
        }
        if (copyFile) {
            int idx = path.lastIndexOf('/');
            if (idx > 0) {
                String dir = path.substring(0, idx);
                File f = new File(dir);
                f.mkdirs();
            }
            FileOutputStream os = new FileOutputStream(path);
            byte[] buf = new byte[1024];
            URLConnection urc = new URL(JavaCIPUnknownScope.urlStr + "/" + sarr[0]).openConnection();
            InputStream is = urc.getInputStream();
            boolean done = false;
            while (!done) {
                int read = is.read(buf, 0, 1024);
                if (read == -1) {
                    done = true;
                } else {
                    os.write(buf, 0, read);
                }
            }
            os.close();
            is.close();
            if (JavaCIPUnknownScope.verbose) {
                System.out.println(" -- Copied: " + sarr[0]);
            }
        }
    }
}
