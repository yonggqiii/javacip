class c16545678 {

    private boolean copyFile(File inFile, File outFile) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(inFile));
            writer = new BufferedWriter(new FileWriter(outFile));
            while (reader.ready()) {
                writer.write(reader.read());
            }
            writer.flush();
        } catch (IORuntimeException ex) {
            ex.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IORuntimeException ex) {
                    ex.printStackTrace();
                    return false;
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IORuntimeException ex) {
                    return false;
                }
            }
        }
        return true;
    }
}
