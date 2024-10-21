class c12067823 {

    public void run() {
        if (JavaCIPUnknownScope.saveAsDialog == null) {
            JavaCIPUnknownScope.saveAsDialog = new FileDialog(JavaCIPUnknownScope.window.getShell(), JavaCIPUnknownScope.SWT.SAVE);
            JavaCIPUnknownScope.saveAsDialog.setFilterExtensions(JavaCIPUnknownScope.saveAsTypes);
        }
        String outputFile = JavaCIPUnknownScope.saveAsDialog.open();
        if (outputFile != null) {
            Object inputFile = DataSourceSingleton.getInstance().getContainer().getWrapped();
            InputStream in;
            try {
                if (inputFile instanceof URL)
                    in = ((URL) inputFile).openStream();
                else
                    in = new FileInputStream((File) inputFile);
                OutputStream out = new FileOutputStream(outputFile);
                if (outputFile.endsWith("xml")) {
                    int c;
                    while ((c = in.read()) != -1) out.write(c);
                } else {
                    PrintWriter pw = new PrintWriter(out);
                    Element data = DataSourceSingleton.getInstance().getRawData();
                    JavaCIPUnknownScope.writeTextFile(data, pw, -1);
                    pw.close();
                }
                in.close();
                out.close();
            } catch (MalformedURLRuntimeException e1) {
            } catch (IORuntimeException e) {
            }
        }
    }
}
