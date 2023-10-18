class c17175193 {

    public void run() {
        try {
            JavaCIPUnknownScope.jButton1.setEnabled(false);
            JavaCIPUnknownScope.jButton2.setEnabled(false);
            URL url = new URL(JavaCIPUnknownScope.updatePath + "currentVersion.txt");
            URLConnection con = url.openConnection();
            con.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            for (int i = 0; (line = in.readLine()) != null; i++) {
                URL fileUrl = new URL(JavaCIPUnknownScope.updatePath + line);
                URLConnection filecon = fileUrl.openConnection();
                InputStream stream = fileUrl.openStream();
                int oneChar, count = 0;
                int size = filecon.getContentLength();
                JavaCIPUnknownScope.jProgressBar1.setMaximum(size);
                JavaCIPUnknownScope.jProgressBar1.setValue(0);
                File testFile = new File(line);
                String build = "";
                for (String dirtest : line.split("/")) {
                    build += dirtest;
                    if (!build.contains(".")) {
                        File dirfile = new File(build);
                        if (!dirfile.exists()) {
                            dirfile.mkdir();
                        }
                    }
                    build += "/";
                }
                if (testFile.length() == size) {
                } else {
                    JavaCIPUnknownScope.transferFile(line, fileUrl, size);
                    if (line.endsWith("documents.zip")) {
                        ZipInputStream in2 = new ZipInputStream(new FileInputStream(line));
                        ZipEntry entry;
                        String pathDoc = line.split("documents.zip")[0];
                        File docDir = new File(pathDoc + "documents");
                        if (!docDir.exists()) {
                            docDir.mkdir();
                        }
                        while ((entry = in2.getNextEntry()) != null) {
                            String outFilename = pathDoc + "documents/" + entry.getName();
                            OutputStream out = new BufferedOutputStream(new FileOutputStream(outFilename));
                            byte[] buf = new byte[1024];
                            int len;
                            while ((len = in2.read(buf)) > 0) {
                                out.write(buf, 0, len);
                            }
                            out.close();
                        }
                        in2.close();
                    }
                    if (line.endsWith("mysql.zip")) {
                        ZipFile zipfile = new ZipFile(line);
                        Enumeration entries = zipfile.entries();
                        String pathDoc = line.split("mysql.zip")[0];
                        File docDir = new File(pathDoc + "mysql");
                        if (!docDir.exists()) {
                            docDir.mkdir();
                        }
                        while (entries.hasMoreElements()) {
                            ZipEntry entry = (ZipEntry) entries.nextElement();
                            if (entry.isDirectory()) {
                                System.err.println("Extracting directory: " + entry.getName());
                                (new File(pathDoc + "mysql/" + entry.getName())).mkdir();
                                continue;
                            }
                            System.err.println("Extracting file: " + entry.getName());
                            InputStream in2 = zipfile.getInputStream(entry);
                            OutputStream out = new BufferedOutputStream(new FileOutputStream(pathDoc + "mysql/" + entry.getName()));
                            byte[] buf = new byte[1024];
                            int len;
                            while ((len = in2.read(buf)) > 0) {
                                out.write(buf, 0, len);
                            }
                            in2.close();
                            out.close();
                        }
                    }
                }
                JavaCIPUnknownScope.jProgressBar2.setValue(i + 1);
                JavaCIPUnknownScope.labelFileProgress.setText((i + 1) + "/" + JavaCIPUnknownScope.numberFiles);
            }
            JavaCIPUnknownScope.labelStatus.setText("Update Finished");
            JavaCIPUnknownScope.jButton1.setVisible(false);
            JavaCIPUnknownScope.jButton2.setText("Finished");
            JavaCIPUnknownScope.jButton1.setEnabled(true);
            JavaCIPUnknownScope.jButton2.setEnabled(true);
        } catch (IORuntimeException ex) {
            Logger.getLogger(Updater.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
