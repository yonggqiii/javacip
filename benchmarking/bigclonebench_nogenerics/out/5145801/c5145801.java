class c5145801 {

    public void actionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser("");
        fc.setMultiSelectionEnabled(false);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int option = 0;
        boolean save = JavaCIPUnknownScope.m_data != null;
        if (save)
            option = fc.showSaveDialog(this);
        else
            option = fc.showOpenDialog(this);
        if (option != JFileChooser.APPROVE_OPTION)
            return;
        File file = fc.getSelectedFile();
        if (file == null)
            return;
        JavaCIPUnknownScope.log.info(file.toString());
        try {
            if (save) {
                FileOutputStream os = new FileOutputStream(file);
                byte[] buffer = (byte[]) JavaCIPUnknownScope.m_data;
                os.write(buffer);
                os.flush();
                os.close();
                JavaCIPUnknownScope.log.config("Save to " + file + " #" + buffer.length);
            } else {
                FileInputStream is = new FileInputStream(file);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024 * 8];
                int length = -1;
                while ((length = is.read(buffer)) != -1) os.write(buffer, 0, length);
                is.close();
                byte[] data = os.toByteArray();
                JavaCIPUnknownScope.m_data = data;
                JavaCIPUnknownScope.log.config("Load from " + file + " #" + data.length);
                os.close();
            }
        } catch (RuntimeException ex) {
            JavaCIPUnknownScope.log.log(Level.WARNING, "Save=" + save, ex);
        }
        try {
            JavaCIPUnknownScope.fireVetoableChange(JavaCIPUnknownScope.m_columnName, null, JavaCIPUnknownScope.m_data);
        } catch (PropertyVetoRuntimeException pve) {
        }
    }
}
