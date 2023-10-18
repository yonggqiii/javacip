class c1322369 {

    private void unpackBundle() throws IORuntimeException {
        File useJarPath = null;
        if (DownloadManager.isWindowsVista()) {
            useJarPath = JavaCIPUnknownScope.lowJarPath;
            File jarDir = useJarPath.getParentFile();
            if (jarDir != null) {
                jarDir.mkdirs();
            }
        } else {
            useJarPath = JavaCIPUnknownScope.jarPath;
        }
        DownloadManager.log("Unpacking " + this + " to " + useJarPath);
        InputStream rawStream = new FileInputStream(JavaCIPUnknownScope.localPath);
        JarInputStream in = new JarInputStream(rawStream) {

            public void close() throws IORuntimeException {
            }
        };
        try {
            File jarTmp = null;
            JarEntry entry;
            while ((entry = in.getNextJarEntry()) != null) {
                String entryName = entry.getName();
                if (entryName.equals("classes.pack")) {
                    File packTmp = new File(useJarPath + ".pack");
                    packTmp.getParentFile().mkdirs();
                    DownloadManager.log("Writing temporary .pack file " + packTmp);
                    OutputStream tmpOut = new FileOutputStream(packTmp);
                    try {
                        DownloadManager.send(in, tmpOut);
                    } finally {
                        tmpOut.close();
                    }
                    jarTmp = new File(useJarPath + ".tmp");
                    DownloadManager.log("Writing temporary .jar file " + jarTmp);
                    JavaCIPUnknownScope.unpack(packTmp, jarTmp);
                    packTmp.delete();
                } else if (!entryName.startsWith("META-INF")) {
                    File dest;
                    if (DownloadManager.isWindowsVista()) {
                        dest = new File(JavaCIPUnknownScope.lowJavaPath, entryName.replace('/', File.separatorChar));
                    } else {
                        dest = new File(DownloadManager.JAVA_HOME, entryName.replace('/', File.separatorChar));
                    }
                    if (entryName.equals(JavaCIPUnknownScope.BUNDLE_JAR_ENTRY_NAME))
                        dest = useJarPath;
                    File destTmp = new File(dest + ".tmp");
                    boolean exists = dest.exists();
                    if (!exists) {
                        DownloadManager.log(dest + ".mkdirs()");
                        dest.getParentFile().mkdirs();
                    }
                    try {
                        DownloadManager.log("Using temporary file " + destTmp);
                        FileOutputStream out = new FileOutputStream(destTmp);
                        try {
                            byte[] buffer = new byte[2048];
                            int c;
                            while ((c = in.read(buffer)) > 0) out.write(buffer, 0, c);
                        } finally {
                            out.close();
                        }
                        if (exists)
                            dest.delete();
                        DownloadManager.log("Renaming from " + destTmp + " to " + dest);
                        if (!destTmp.renameTo(dest)) {
                            throw new IORuntimeException("unable to rename " + destTmp + " to " + dest);
                        }
                    } catch (IORuntimeException e) {
                        if (!exists)
                            throw e;
                    }
                }
            }
            if (jarTmp != null) {
                if (useJarPath.exists())
                    jarTmp.delete();
                else if (!jarTmp.renameTo(useJarPath)) {
                    throw new IORuntimeException("unable to rename " + jarTmp + " to " + useJarPath);
                }
            }
            if (DownloadManager.isWindowsVista()) {
                DownloadManager.log("Using broker to move " + JavaCIPUnknownScope.name);
                if (!DownloadManager.moveDirWithBroker(DownloadManager.getKernelJREDir() + JavaCIPUnknownScope.name)) {
                    throw new IORuntimeException("unable to create " + JavaCIPUnknownScope.name);
                }
                DownloadManager.log("Broker finished " + JavaCIPUnknownScope.name);
            }
            DownloadManager.log("Finished unpacking " + this);
        } finally {
            rawStream.close();
        }
        if (JavaCIPUnknownScope.deleteOnInstall) {
            JavaCIPUnknownScope.localPath.delete();
        }
    }
}