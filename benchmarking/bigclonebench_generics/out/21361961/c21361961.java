class c21361961 {

    private void transform(CommandLine commandLine) throws IOException {
        Reader reader;
        if (commandLine.hasOption('i')) {
            reader = JavaCIPUnknownScope.createFileReader(commandLine.getOptionValue('i'));
        } else {
            reader = JavaCIPUnknownScope.createStandardInputReader();
        }
        Writer writer;
        if (commandLine.hasOption('o')) {
            writer = JavaCIPUnknownScope.createFileWriter(commandLine.getOptionValue('o'));
        } else {
            writer = JavaCIPUnknownScope.createStandardOutputWriter();
        }
        String mapRule = commandLine.getOptionValue("m");
        try {
            SrxTransformer transformer = new SrxAnyTransformer();
            Map<String, Object> parameterMap = new HashMap<String, Object>();
            if (mapRule != null) {
                parameterMap.put(Srx1Transformer.MAP_RULE_NAME, mapRule);
            }
            transformer.transform(reader, writer, parameterMap);
        } finally {
            JavaCIPUnknownScope.cleanupReader(reader);
            JavaCIPUnknownScope.cleanupWriter(writer);
        }
    }
}
