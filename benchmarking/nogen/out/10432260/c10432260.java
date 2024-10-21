class c10432260 {

    private void appendArchive() throws RuntimeException {
        String cmd;
        if (JavaCIPUnknownScope.profile == CompilationProfile.UNIX_GCC) {
            cmd = "cat";
        } else if (JavaCIPUnknownScope.profile == CompilationProfile.MINGW_WINDOWS) {
            cmd = "type";
        } else {
            throw new RuntimeException("Unknown cat equivalent for profile " + JavaCIPUnknownScope.profile);
        }
        JavaCIPUnknownScope.compFrame.writeLine("<span style='color: green;'>" + cmd + " \"" + JavaCIPUnknownScope.imageArchive.getAbsolutePath() + "\" >> \"" + JavaCIPUnknownScope.outputFile.getAbsolutePath() + "\"</span>");
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(JavaCIPUnknownScope.outputFile, true));
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(JavaCIPUnknownScope.imageArchive));
        int read;
        while ((read = in.read()) != -1) {
            out.write(read);
        }
        in.close();
        out.close();
    }
}
