


class c15537156 {

    private void copy(String inputPath, String outputPath, String name) {
        try {
            FileReader in = new FileReader(inputPath + name);
            FileWriter out = new FileWriter(outputPath + name);
            int c;
            while ((c = in.read()) != -1) out.write(c);
            in.close();
            out.close();
        } catch (FileNotFoundRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
    }

}
