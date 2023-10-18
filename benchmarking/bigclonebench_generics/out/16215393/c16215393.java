class c16215393 {

    public void importSequences() {
        JavaCIPUnknownScope.names = new ArrayList<String>();
        JavaCIPUnknownScope.sequences = new ArrayList<String>();
        try {
            InputStream is = JavaCIPUnknownScope.urls[JavaCIPUnknownScope.urlComboBox.getSelectedIndex()].openStream();
            ImportHelper helper = new ImportHelper(new InputStreamReader(is));
            int ch = helper.read();
            while (ch != '>') {
                ch = helper.read();
            }
            do {
                String line = helper.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line, " \t");
                String name = tokenizer.nextToken();
                StringBuffer seq = new StringBuffer();
                helper.readSequence(seq, ">", Integer.MAX_VALUE, "-", "?", "", null);
                ch = helper.getLastDelimiter();
                JavaCIPUnknownScope.names.add(name);
                JavaCIPUnknownScope.sequences.add(seq.toString());
            } while (ch == '>');
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (EOFException e) {
        } catch (IOException e) {
        }
    }
}
