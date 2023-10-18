class c15287816 {

    public Vector<Question> reload() throws IOException {
        Vector<Question> questions = new Vector<Question>();
        InputStream is = JavaCIPUnknownScope.url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        JavaCIPUnknownScope.shortName = br.readLine();
        if (JavaCIPUnknownScope.shortName != null && JavaCIPUnknownScope.shortName.equals("SHORTNAME")) {
            JavaCIPUnknownScope.shortName = br.readLine();
            JavaCIPUnknownScope.author = br.readLine();
            if (JavaCIPUnknownScope.author != null && JavaCIPUnknownScope.author.equals("AUTHOR")) {
                JavaCIPUnknownScope.author = br.readLine();
                JavaCIPUnknownScope.description = br.readLine();
                if (JavaCIPUnknownScope.description != null && JavaCIPUnknownScope.description.equals("DESCRIPTION")) {
                    JavaCIPUnknownScope.description = br.readLine();
                    try {
                        questions = QuestionLoader.getQuestions(br);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                        throw ioe;
                    } finally {
                        br.close();
                        is.close();
                    }
                } else {
                    throw new IllegalArgumentException();
                }
            } else {
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException();
        }
        return questions;
    }
}
