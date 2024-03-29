class c19627256 {

    public void compile(Project project) throws ProjectCompilerException {
        List<Resource> resources = project.getModel().getResource();
        for (Resource resource : resources) {
            try {
                IOUtils.copy(JavaCIPUnknownScope.srcDir.getRelative(resource.getLocation()).getInputStream(), JavaCIPUnknownScope.outDir.getRelative(resource.getLocation()).getOutputStream());
            } catch (IOException e) {
                throw new ProjectCompilerException("Resource cannot be copied. Compilation failed", e);
            }
        }
    }
}
