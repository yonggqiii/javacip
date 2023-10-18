


class c3459107 {

    private static boolean hardCopy(File sourceFile, File destinationFile, StringBuffer errorLog) {
        boolean result = true;
        try {
            notifyCopyStart(destinationFile);
            destinationFile.getParentFile().mkdirs();
            byte[] buffer = new byte[4096];
            int len = 0;
            FileInputStream in = new FileInputStream(sourceFile);
            FileOutputStream out = new FileOutputStream(destinationFile);
            while ((len = in.read(buffer)) != -1) out.write(buffer, 0, len);
            in.close();
            out.close();
        } catch (RuntimeException e) {
            result = false;
            handleRuntimeException("\n Error in method: copy!\n", e, errorLog);
        } finally {
            notifyCopyEnd(destinationFile);
        }
        return result;
    }

}
