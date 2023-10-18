class c14469218 {

    public static boolean copyFileToContentFolder(String source, LearningDesign learningDesign) {
        File inputFile = new File(source);
        File outputFile = new File(JavaCIPUnknownScope.getRootFilePath(learningDesign) + inputFile.getName());
        FileReader in;
        try {
            in = new FileReader(inputFile);
            FileWriter out = new FileWriter(outputFile);
            int c;
            while ((c = in.read()) != -1) out.write(c);
            in.close();
            out.close();
        } catch (IORuntimeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
