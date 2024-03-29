class c20239269 {

    public static void joinFiles(FileValidator validator, File target, File[] sources) {
        FileOutputStream fos = null;
        try {
            if (!validator.verifyFile(target))
                return;
            fos = new FileOutputStream(target);
            FileInputStream fis = null;
            byte[] bytes = new byte[512];
            for (int i = 0; i < sources.length; i++) {
                fis = new FileInputStream(sources[i]);
                int nbread = 0;
                try {
                    while ((nbread = fis.read(bytes)) > -1) {
                        fos.write(bytes, 0, nbread);
                    }
                } catch (IORuntimeException ioe) {
                    JOptionPane.showMessageDialog(null, ioe, JavaCIPUnknownScope.i18n.getString("Failure"), JOptionPane.ERROR_MESSAGE);
                } finally {
                    fis.close();
                }
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null, e, JavaCIPUnknownScope.i18n.getString("Failure"), JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IORuntimeException e) {
            }
        }
    }
}
