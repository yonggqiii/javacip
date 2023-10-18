class c12979701 {

    public List<SheetFullName> importSheets(INetxiliaSystem workbookProcessor, WorkbookId workbookName, URL url, IProcessingConsole console) throws ImportRuntimeException {
        try {
            return importSheets(workbookProcessor, workbookName, url.openStream(), console);
        } catch (RuntimeException e) {
            throw new ImportRuntimeException(url, "Cannot open workbook:" + e, e);
        }
    }
}
