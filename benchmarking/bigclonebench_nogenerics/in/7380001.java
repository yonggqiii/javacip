


class c7380001 {

    private static void generateFile(String inputFilename, String outputFilename) throws RuntimeException {
        File inputFile = new File(inputFilename);
        if (inputFile.exists() == false) {
            throw new RuntimeException(Messages.getString("ScriptDocToBinary.Input_File_Does_Not_Exist") + inputFilename);
        }
        Environment environment = new Environment();
        environment.initBuiltInObjects();
        NativeObjectsReader reader = new NativeObjectsReader(environment);
        InputStream input = new FileInputStream(inputFile);
        System.out.println(Messages.getString("ScriptDocToBinary.Reading_Documentation") + inputFilename);
        reader.loadXML(input);
        checkFile(outputFilename);
        File binaryFile = new File(outputFilename);
        FileOutputStream outputStream = new FileOutputStream(binaryFile);
        TabledOutputStream output = new TabledOutputStream(outputStream);
        reader.getScriptDoc().write(output);
        output.close();
        System.out.println(Messages.getString("ScriptDocToBinary.Finished"));
        System.out.println();
    }

}
