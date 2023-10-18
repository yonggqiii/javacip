class c18803363 {

    public void patchFile(final File classFile) {
        if (!classFile.exists()) {
            JavaCIPUnknownScope.myErrors.add(new FormErrorInfo(null, "Class to bind does not exist: " + JavaCIPUnknownScope.myRootContainer.getClassToBind()));
            return;
        }
        FileInputStream fis;
        try {
            byte[] patchedData;
            fis = new FileInputStream(classFile);
            try {
                patchedData = JavaCIPUnknownScope.patchClass(fis);
                if (patchedData == null) {
                    return;
                }
            } finally {
                fis.close();
            }
            FileOutputStream fos = new FileOutputStream(classFile);
            try {
                fos.write(patchedData);
            } finally {
                fos.close();
            }
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.myErrors.add(new FormErrorInfo(null, "Cannot read or write class file " + classFile.getPath() + ": " + e.toString()));
        }
    }
}
