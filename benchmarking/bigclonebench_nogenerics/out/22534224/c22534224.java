class c22534224 {

    public void openUrlActionPerformed(ActionEvent event) {
        RemoteFileChooser fileChooser = new RemoteFileChooser(this, JavaCIPUnknownScope.getAppName());
        fileChooser.getDialog().setVisible(true);
        if (fileChooser.getResult() == JOptionPane.OK_OPTION) {
            JavaCIPUnknownScope.setCursorBusy(true);
            URL url = fileChooser.getSelectedUrl();
            String filename = fileChooser.getSelectedFilename();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                JavaCIPUnknownScope.openFile(filename, reader);
            } catch (IORuntimeException e) {
                JavaCIPUnknownScope.handleRuntimeException(e);
            }
            JavaCIPUnknownScope.setCursorBusy(false);
        }
        JavaCIPUnknownScope.checkActions();
    }
}
