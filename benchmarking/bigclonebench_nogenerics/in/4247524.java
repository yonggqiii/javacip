


class c4247524 {

    public static String backupFile(File source) {
        File backup = new File(source.getParent() + "/~" + source.getName());
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(source)));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(backup)));
            return FileUtil.backupFile(reader, writer, source.getAbsolutePath());
        } catch (FileNotFoundRuntimeException fe) {
            String msg = "Failed to find file for backup [" + source.getAbsolutePath() + "].";
            _log.error(msg, fe);
            throw new InvalidImplementationRuntimeException(msg, fe);
        }
    }

}
