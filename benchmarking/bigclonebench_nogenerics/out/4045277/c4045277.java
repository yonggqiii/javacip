class c4045277 {

    public void execute() throws BuildRuntimeException {
        Project proj = JavaCIPUnknownScope.getProject();
        if (JavaCIPUnknownScope.templateFile == null)
            throw new BuildRuntimeException("Template file not set");
        if (JavaCIPUnknownScope.targetFile == null)
            throw new BuildRuntimeException("Target file not set");
        try {
            File template = new File(JavaCIPUnknownScope.templateFile);
            File target = new File(JavaCIPUnknownScope.targetFile);
            if (!template.exists())
                throw new BuildRuntimeException("Template file does not exist " + template.toString());
            if (!template.canRead())
                throw new BuildRuntimeException("Cannot read template file: " + template.toString());
            if (((!JavaCIPUnknownScope.append) && (!JavaCIPUnknownScope.overwrite)) && (!target.exists()))
                throw new BuildRuntimeException("Target file already exists and append and overwrite are false " + target.toString());
            if (JavaCIPUnknownScope.VERBOSE) {
                System.out.println("ProcessTemplate: tmpl in " + template.toString());
                System.out.println("ProcessTemplate: file out " + target.toString());
            }
            BufferedReader reader = new BufferedReader(new FileReader(template));
            BufferedWriter writer = new BufferedWriter(new FileWriter(JavaCIPUnknownScope.targetFile, JavaCIPUnknownScope.append));
            JavaCIPUnknownScope.parse(reader, writer);
            writer.flush();
            writer.close();
        } catch (RuntimeException e) {
            if (JavaCIPUnknownScope.VERBOSE)
                e.printStackTrace();
            throw new BuildRuntimeException(e);
        }
    }
}
