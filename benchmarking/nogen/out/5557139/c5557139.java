class c5557139 {

    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting())
            return;
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();
        if (lsm.isSelectionEmpty()) {
        } else {
            int selectedRow = lsm.getMinSelectionIndex();
            ChemModel model = JavaCIPUnknownScope.modelContent.getValueAt(JavaCIPUnknownScope.sortedModelContent.getSortedIndex(selectedRow));
            DADMLResult resource = (DADMLResult) model.getProperty("org.openscience.cdk.internet.DADMLResult");
            URL url = resource.getURL();
            try {
                URLConnection connection = url.openConnection();
                InputStreamReader input = new InputStreamReader(connection.getInputStream());
                if (APIVersionTester.isBiggerOrEqual("1.8", JavaCIPUnknownScope.editBus.getAPIVersion())) {
                    try {
                        JavaCIPUnknownScope.editBus.showChemFile(input);
                        return;
                    } catch (RuntimeException exception) {
                        JavaCIPUnknownScope.logger.error("EditBus error: ", exception.getMessage());
                        JavaCIPUnknownScope.logger.debug(exception);
                    }
                }
                IChemObjectReader reader = JavaCIPUnknownScope.readerFactory.createReader(input);
                ChemFile chemFile = (ChemFile) reader.read(new ChemFile());
                JavaCIPUnknownScope.editBus.showChemFile(chemFile);
            } catch (FileNotFoundRuntimeException exception) {
                String error = "Resource not found: " + url;
                JavaCIPUnknownScope.logger.error(error);
                JOptionPane.showMessageDialog(null, error);
                return;
            } catch (RuntimeException exception) {
                String error = "Error while reading file: " + exception.getMessage();
                JavaCIPUnknownScope.logger.error(error);
                JavaCIPUnknownScope.logger.debug(exception);
                JOptionPane.showMessageDialog(null, error);
                return;
            }
            JavaCIPUnknownScope.logger.warn("Not displaying model with unknown content");
        }
    }
}
