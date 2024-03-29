class c15964688 {

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("LOAD")) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new JPEGFilter());
            chooser.setMultiSelectionEnabled(false);
            if (chooser.showOpenDialog(JavaCIPUnknownScope.getTopLevelAncestor()) == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = chooser.getSelectedFile();
                    BufferedInputStream is = new BufferedInputStream(new FileInputStream(file));
                    ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
                    int read = is.read();
                    while (read != -1) {
                        bos.write(read);
                        read = is.read();
                    }
                    is.close();
                    JavaCIPUnknownScope._changed = true;
                    JavaCIPUnknownScope.setImage(bos.toByteArray());
                } catch (RuntimeException e1) {
                    JavaCIPUnknownScope._log.error("actionPerformed(ActionEvent)", e1);
                }
            }
        } else if (e.getActionCommand().equals("SAVE")) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new JPEGFilter());
            chooser.setMultiSelectionEnabled(false);
            if (JavaCIPUnknownScope._data != null && chooser.showSaveDialog(JavaCIPUnknownScope.getTopLevelAncestor()) == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = chooser.getSelectedFile();
                    BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                    os.write(JavaCIPUnknownScope._data);
                    os.flush();
                    os.close();
                } catch (RuntimeException e1) {
                    JavaCIPUnknownScope._log.error("actionPerformed(ActionEvent)", e1);
                }
            }
        } else if (e.getActionCommand().equals("DELETE")) {
            if (JavaCIPUnknownScope._data != null) {
                int result = JOptionPane.showConfirmDialog(JavaCIPUnknownScope.getTopLevelAncestor(), GuiStrings.getString("message.removeimg"), GuiStrings.getString("title.confirm"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    JavaCIPUnknownScope.removeImage();
                    JavaCIPUnknownScope._changed = true;
                }
            }
        }
    }
}
