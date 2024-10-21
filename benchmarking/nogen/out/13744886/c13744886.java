class c13744886 {

    public void run() {
        try {
            FileChannel in = new FileInputStream(JavaCIPUnknownScope.inputfile).getChannel();
            long pos = 0;
            for (int i = 1; i <= JavaCIPUnknownScope.noofparts; i++) {
                FileChannel out = new FileOutputStream(JavaCIPUnknownScope.outputfile.getAbsolutePath() + "." + "v" + i).getChannel();
                JavaCIPUnknownScope.status.setText("Rozdělovač: Rozděluji část " + i + "..");
                if (JavaCIPUnknownScope.remainingsize >= JavaCIPUnknownScope.splitsize) {
                    in.transferTo(pos, JavaCIPUnknownScope.splitsize, out);
                    pos += JavaCIPUnknownScope.splitsize;
                    JavaCIPUnknownScope.remainingsize -= JavaCIPUnknownScope.splitsize;
                } else {
                    in.transferTo(pos, JavaCIPUnknownScope.remainingsize, out);
                }
                JavaCIPUnknownScope.pb.setValue(100 * i / JavaCIPUnknownScope.noofparts);
                out.close();
            }
            in.close();
            if (JavaCIPUnknownScope.deleteOnFinish)
                new File(JavaCIPUnknownScope.inputfile + "").delete();
            JavaCIPUnknownScope.status.setText("Rozdělovač: Hotovo..");
            JOptionPane.showMessageDialog(null, "Rozděleno!", "Rozdělovač", JOptionPane.INFORMATION_MESSAGE);
        } catch (IORuntimeException ex) {
        }
    }
}
