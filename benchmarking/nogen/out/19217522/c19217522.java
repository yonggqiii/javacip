class c19217522 {

    boolean copyFileStructure(File oldFile, File newFile) {
        if (oldFile == null || newFile == null)
            return false;
        File searchFile = newFile;
        do {
            if (oldFile.equals(searchFile))
                return false;
            searchFile = searchFile.getParentFile();
        } while (searchFile != null);
        if (oldFile.isDirectory()) {
            if (JavaCIPUnknownScope.progressDialog != null) {
                JavaCIPUnknownScope.progressDialog.setDetailFile(oldFile, ProgressDialog.COPY);
            }
            if (JavaCIPUnknownScope.simulateOnly) {
            } else {
                if (!newFile.mkdirs())
                    return false;
            }
            File[] subFiles = oldFile.listFiles();
            if (subFiles != null) {
                if (JavaCIPUnknownScope.progressDialog != null) {
                    JavaCIPUnknownScope.progressDialog.addWorkUnits(subFiles.length);
                }
                for (int i = 0; i < subFiles.length; i++) {
                    File oldSubFile = subFiles[i];
                    File newSubFile = new File(newFile, oldSubFile.getName());
                    if (!copyFileStructure(oldSubFile, newSubFile))
                        return false;
                    if (JavaCIPUnknownScope.progressDialog != null) {
                        JavaCIPUnknownScope.progressDialog.addProgress(1);
                        if (JavaCIPUnknownScope.progressDialog.isCancelled())
                            return false;
                    }
                }
            }
        } else {
            if (JavaCIPUnknownScope.simulateOnly) {
            } else {
                FileReader in = null;
                FileWriter out = null;
                try {
                    in = new FileReader(oldFile);
                    out = new FileWriter(newFile);
                    int count;
                    while ((count = in.read()) != -1) out.write(count);
                } catch (FileNotFoundRuntimeException e) {
                    return false;
                } catch (IORuntimeException e) {
                    return false;
                } finally {
                    try {
                        if (in != null)
                            in.close();
                        if (out != null)
                            out.close();
                    } catch (IORuntimeException e) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
