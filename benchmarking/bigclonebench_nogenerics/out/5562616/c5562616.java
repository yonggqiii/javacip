class c5562616 {

    public void convert(CSVReader reader, Writer writer, int nbTotalRows) throws IORuntimeException, InterruptedRuntimeException {
        Validate.notNull(reader, "CSVReader");
        Validate.notNull(writer, "Writer");
        Writer bufferedWriter = new BufferedWriter(writer);
        File fileForColsDef = JavaCIPUnknownScope.createTempFileForCss();
        BufferedWriter colsDefWriter = new BufferedWriter(new FileWriter(fileForColsDef));
        File fileForTable = JavaCIPUnknownScope.createTempFileForTable();
        BufferedWriter tableWriter = new BufferedWriter(new FileWriter(fileForTable));
        try {
            int currentRow = 0;
            String[] nextLine = reader.readNext();
            if (nextLine != null) {
                int[] colsCharCount = new int[nextLine.length];
                JavaCIPUnknownScope.writeTableRowHeader(tableWriter, nextLine);
                while ((nextLine = reader.readNext()) != null) {
                    currentRow++;
                    if (JavaCIPUnknownScope.progress != null) {
                        float percent = ((float) currentRow / (float) nbTotalRows) * 100f;
                        JavaCIPUnknownScope.progress.updateProgress(ConvertionStepEnum.PROCESSING_ROWS, percent);
                    }
                    JavaCIPUnknownScope.writeTableRow(tableWriter, nextLine, colsCharCount);
                }
                JavaCIPUnknownScope.writeTableStart(colsDefWriter, colsCharCount);
                JavaCIPUnknownScope.writeColsDefinitions(colsDefWriter, colsCharCount);
            }
            JavaCIPUnknownScope.writeConverterInfos(bufferedWriter);
            JavaCIPUnknownScope.writeTableEnd(tableWriter);
            JavaCIPUnknownScope.flushAndClose(tableWriter);
            JavaCIPUnknownScope.flushAndClose(colsDefWriter);
            BufferedReader colsDefReader = new BufferedReader(new FileReader(fileForColsDef));
            BufferedReader tableReader = new BufferedReader(new FileReader(fileForTable));
            JavaCIPUnknownScope.mergeFiles(bufferedWriter, colsDefReader, tableReader);
        } finally {
            JavaCIPUnknownScope.closeQuietly(tableWriter);
            JavaCIPUnknownScope.closeQuietly(colsDefWriter);
            fileForTable.delete();
            fileForColsDef.delete();
        }
    }
}
