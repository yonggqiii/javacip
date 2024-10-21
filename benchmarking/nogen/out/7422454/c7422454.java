class c7422454 {

    public void run() {
        if (JavaCIPUnknownScope.name == null) {
            System.err.println("Must set file name for retrieval");
            return;
        }
        if (JavaCIPUnknownScope.handle == null) {
            System.err.println("Must set CCNHandle");
            return;
        }
        if (JavaCIPUnknownScope.htmlPane == null) {
            System.err.println("Must set JEditorPane");
            return;
        }
        JFrame frame = new JFrame();
        JFileChooser chooser = new JFileChooser();
        File f = null;
        chooser.setCurrentDirectory(null);
        int returnVal = chooser.showSaveDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            f = chooser.getSelectedFile();
        } else {
            JavaCIPUnknownScope.htmlPane.setText("Save File cancelled");
            return;
        }
        boolean overwrite = false;
        try {
            if (f.exists()) {
                JavaCIPUnknownScope.htmlPane.setText("Overwriting contents of " + f.getPath());
                overwrite = true;
            } else {
                f.createNewFile();
            }
            if (f.canWrite()) {
            } else {
                JavaCIPUnknownScope.htmlPane.setText("The ContentExplorer is unable to write the content to the specified file.");
                return;
            }
        } catch (IORuntimeException e) {
            System.err.println("could not create " + f.getPath() + " for saving content to filesystem");
            JavaCIPUnknownScope.htmlPane.setText("could not create " + f.getPath() + " for saving content to filesystem");
        }
        try {
            if (!overwrite)
                JavaCIPUnknownScope.htmlPane.setText("saving " + JavaCIPUnknownScope.name + " to " + f.getCanonicalPath());
            else
                JavaCIPUnknownScope.htmlPane.setText("overwriting contents of " + f.getCanonicalPath() + " to save " + JavaCIPUnknownScope.name);
            CCNFileInputStream fis = new CCNFileInputStream(JavaCIPUnknownScope.name, JavaCIPUnknownScope.handle);
            FileOutputStream output = new FileOutputStream(f);
            byte[] buffer = new byte[JavaCIPUnknownScope.readsize];
            int readcount = 0;
            int readtotal = 0;
            while ((readcount = fis.read(buffer)) != -1) {
                readtotal += readcount;
                output.write(buffer, 0, readcount);
                output.flush();
            }
            JavaCIPUnknownScope.htmlPane.setText("Saved " + JavaCIPUnknownScope.name + " to " + f.getCanonicalPath());
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.htmlPane.setText("Could not save " + JavaCIPUnknownScope.name + " to " + f.getPath() + " This may be a prefix for an object or just may not be available at this time.");
            System.err.println("Could not retrieve file: " + JavaCIPUnknownScope.name);
        }
    }
}
