class c10118207 {

    public static final boolean zipUpdate(String zipfile, String name, String oldname, byte[] contents, boolean delete) {
        try {
            File temp = File.createTempFile("atf", ".zip");
            InputStream<Integer> in = x;
            OutputStream os;
            ZipInputStream zin;
            ZipOutputStream zout;
            ZipEntry e;
            ZipEntry e2;
            byte buffer[];
            int bytesRead;
            boolean found = false;
            boolean rename = false;
            String oname = name;
            if (oldname != null) {
                name = oldname;
                rename = true;
            }
            while ((e = zin.getNextEntry()) != null) {
                if (!e.isDirectory()) {
                    String ename = e.getName();
                    if (delete && ename.equals(name)) continue;
                    zout.putNextEntry(e2);
                    if (ename.equals(name)) {
                        found = true;
                        zout.write(contents);
                    } else {
                        while ((bytesRead = zin.read(buffer)) != -1) zout.write(buffer, 0, bytesRead);
                    }
                    zout.closeEntry();
                }
            }
            if (!found && !delete) {
                zout.putNextEntry(e);
                zout.write(contents);
                zout.closeEntry();
            }
            zin.close();
            zout.close();
            File fp;
            fp.delete();
            MLUtil.copyFile(temp, fp);
            temp.delete();
            return (true);
        } catch (FileNotFoundException e) {
            MLUtil.runtimeError(e, "updateZip " + zipfile + " " + name);
        } catch (IOException e) {
            MLUtil.runtimeError(e, "updateZip " + zipfile + " " + name);
        }
        return (false);
    }

}