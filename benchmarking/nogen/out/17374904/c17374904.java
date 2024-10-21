class c17374904 {

    protected void saveSelectedFiles() {
        if (JavaCIPUnknownScope.dataList.getSelectedRowCount() == 0) {
            return;
        }
        if (JavaCIPUnknownScope.dataList.getSelectedRowCount() == 1) {
            Object obj = JavaCIPUnknownScope.model.getItemAtRow(JavaCIPUnknownScope.sorter.convertRowIndexToModel(JavaCIPUnknownScope.dataList.getSelectedRow()));
            AttachFile entry = (AttachFile) obj;
            JFileChooser fc = new JFileChooser();
            fc.setSelectedFile(new File(fc.getCurrentDirectory(), entry.getCurrentPath().getName()));
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File current = entry.getCurrentPath();
                File dest = fc.getSelectedFile();
                try {
                    FileInputStream in = new FileInputStream(current);
                    FileOutputStream out = new FileOutputStream(dest);
                    byte[] readBuf = new byte[1024 * 512];
                    int readLength;
                    while ((readLength = in.read(readBuf)) != -1) {
                        out.write(readBuf, 0, readLength);
                    }
                    in.close();
                    out.close();
                } catch (FileNotFoundRuntimeException e) {
                    e.printStackTrace();
                } catch (IORuntimeException e) {
                    e.printStackTrace();
                }
            }
            return;
        } else {
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                for (Integer idx : JavaCIPUnknownScope.dataList.getSelectedRows()) {
                    Object obj = JavaCIPUnknownScope.model.getItemAtRow(JavaCIPUnknownScope.sorter.convertRowIndexToModel(idx));
                    AttachFile entry = (AttachFile) obj;
                    File current = entry.getCurrentPath();
                    File dest = new File(fc.getSelectedFile(), entry.getName());
                    try {
                        FileInputStream in = new FileInputStream(current);
                        FileOutputStream out = new FileOutputStream(dest);
                        byte[] readBuf = new byte[1024 * 512];
                        int readLength;
                        while ((readLength = in.read(readBuf)) != -1) {
                            out.write(readBuf, 0, readLength);
                        }
                        in.close();
                        out.close();
                    } catch (FileNotFoundRuntimeException e) {
                        e.printStackTrace();
                    } catch (IORuntimeException e) {
                        e.printStackTrace();
                    }
                }
            }
            return;
        }
    }
}
