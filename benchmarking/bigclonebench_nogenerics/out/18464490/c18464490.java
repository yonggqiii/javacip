class c18464490 {

    public void actionPerformed(ActionEvent e) {
        if (JavaCIPUnknownScope.copiedFiles_ != null) {
            File[] tmpFiles = new File[JavaCIPUnknownScope.copiedFiles_.length];
            File tmpDir = new File(Settings.getPropertyString(ConstantKeys.project_dir), "tmp/");
            tmpDir.mkdirs();
            for (int i = JavaCIPUnknownScope.copiedFiles_.length - 1; i >= 0; i--) {
                Frame f = FrameManager.getInstance().getFrameAtIndex(i);
                try {
                    File in = f.getFile();
                    File out = new File(tmpDir, f.getFile().getName());
                    FileChannel inChannel = new FileInputStream(in).getChannel();
                    FileChannel outChannel = new FileOutputStream(out).getChannel();
                    inChannel.transferTo(0, inChannel.size(), outChannel);
                    if (inChannel != null)
                        inChannel.close();
                    if (outChannel != null)
                        outChannel.close();
                    tmpFiles[i] = out;
                } catch (IORuntimeException e1) {
                    e1.printStackTrace();
                }
            }
            try {
                FrameManager.getInstance().insertFrames(JavaCIPUnknownScope.getTable().getSelectedRow(), FrameManager.INSERT_TYPE.MOVE, tmpFiles);
            } catch (IORuntimeException e1) {
                e1.printStackTrace();
            }
        }
    }
}
