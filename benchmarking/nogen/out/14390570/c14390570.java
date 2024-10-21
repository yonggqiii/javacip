class c14390570 {

    public void actionPerformed(ActionEvent e) {
        int returnVal = JavaCIPUnknownScope.chooser.showSaveDialog(JavaCIPUnknownScope.jd);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = JavaCIPUnknownScope.chooser.getSelectedFile();
            String fileName = file.getPath();
            String ext = StringUtil.getLowerExtension(fileName);
            if (!"png".equals(ext)) {
                fileName += ".png";
                file = new File(fileName);
            }
            boolean doIt = true;
            if (file.exists()) {
                int i = JOptionPane.showConfirmDialog(JavaCIPUnknownScope.jd, JavaCIPUnknownScope.getMessage("warn_file_exist"));
                if (i != JOptionPane.YES_OPTION)
                    doIt = false;
            } else if (!file.getParentFile().exists()) {
                doIt = file.getParentFile().mkdirs();
            }
            if (doIt) {
                FileChannel src = null;
                FileChannel dest = null;
                try {
                    src = new FileInputStream(JavaCIPUnknownScope.imageURL.getPath()).getChannel();
                    dest = new FileOutputStream(fileName).getChannel();
                    src.transferTo(0, src.size(), dest);
                } catch (FileNotFoundRuntimeException e1) {
                    JavaCIPUnknownScope.warn(JavaCIPUnknownScope.jd, JavaCIPUnknownScope.getMessage("err_no_source_file"));
                } catch (IORuntimeException e2) {
                    JavaCIPUnknownScope.warn(JavaCIPUnknownScope.jd, JavaCIPUnknownScope.getMessage("err_output_target"));
                } finally {
                    try {
                        if (src != null)
                            src.close();
                    } catch (IORuntimeException e1) {
                    }
                    try {
                        if (dest != null)
                            dest.close();
                    } catch (IORuntimeException e1) {
                    }
                    src = null;
                    dest = null;
                }
            }
        }
    }
}
