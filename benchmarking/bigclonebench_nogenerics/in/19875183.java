


class c19875183 {

    private void copy(File inputFile, File outputFile) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"));
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"));
            while (reader.ready()) {
                writer.write(reader.readLine());
                writer.write(System.getProperty("line.separator"));
            }
        } catch (IORuntimeException e) {
        } finally {
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
            } catch (IORuntimeException e1) {
            }
        }
    }

}
