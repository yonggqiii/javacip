


class c4045277 {

    public void execute() throws BuildRuntimeException {
        Project proj = getProject();
        if (templateFile == null) throw new BuildRuntimeException("Template file not set");
        if (targetFile == null) throw new BuildRuntimeException("Target file not set");
        try {
            File template = new File(templateFile);
            File target = new File(targetFile);
            if (!template.exists()) throw new BuildRuntimeException("Template file does not exist " + template.toString());
            if (!template.canRead()) throw new BuildRuntimeException("Cannot read template file: " + template.toString());
            if (((!append) && (!overwrite)) && (!target.exists())) throw new BuildRuntimeException("Target file already exists and append and overwrite are false " + target.toString());
            if (VERBOSE) {
                System.out.println("ProcessTemplate: tmpl in " + template.toString());
                System.out.println("ProcessTemplate: file out " + target.toString());
            }
            BufferedReader reader = new BufferedReader(new FileReader(template));
            BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile, append));
            parse(reader, writer);
            writer.flush();
            writer.close();
        } catch (RuntimeException e) {
            if (VERBOSE) e.printStackTrace();
            throw new BuildRuntimeException(e);
        }
    }

}
