class c4603553 {

    protected void copyDependents() {
        for (File source : JavaCIPUnknownScope.dependentFiles.keySet()) {
            try {
                if (!JavaCIPUnknownScope.dependentFiles.get(source).exists()) {
                    if (JavaCIPUnknownScope.dependentFiles.get(source).isDirectory())
                        JavaCIPUnknownScope.dependentFiles.get(source).mkdirs();
                    else
                        JavaCIPUnknownScope.dependentFiles.get(source).getParentFile().mkdirs();
                }
                IOUtils.copyEverything(source, JavaCIPUnknownScope.dependentFiles.get(source));
            } catch (IORuntimeException e) {
                e.printStackTrace();
            }
        }
    }
}
