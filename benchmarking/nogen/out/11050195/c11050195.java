class c11050195 {

    private void writeFile(File file, String fileName) {
        try {
            FileInputStream fin = new FileInputStream(file);
            FileOutputStream fout = new FileOutputStream(JavaCIPUnknownScope.dirTableModel.getDirectory().getAbsolutePath() + File.separator + fileName);
            int val;
            while ((val = fin.read()) != -1) fout.write(val);
            fin.close();
            fout.close();
            JavaCIPUnknownScope.dirTableModel.reset();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
