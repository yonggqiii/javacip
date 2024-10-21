class c23515205 {

    public boolean performFinish() {
        try {
            IJavaProject javaProject = JavaCore.create(JavaCIPUnknownScope.getProject());
            final IProjectDescription projectDescription = ResourcesPlugin.getWorkspace().newProjectDescription(JavaCIPUnknownScope.projectPage.getProjectName());
            projectDescription.setLocation(null);
            JavaCIPUnknownScope.getProject().create(projectDescription, null);
            List<IClasspathEntry> classpathEntries = new ArrayList<IClasspathEntry>();
            projectDescription.setNatureIds(JavaCIPUnknownScope.getNatures());
            List<String> builderIDs = new ArrayList<String>();
            JavaCIPUnknownScope.addBuilders(builderIDs);
            ICommand[] buildCMDS = new ICommand[builderIDs.size()];
            int i = 0;
            for (String builderID : builderIDs) {
                ICommand build = projectDescription.newCommand();
                build.setBuilderName(builderID);
                buildCMDS[i++] = build;
            }
            projectDescription.setBuildSpec(buildCMDS);
            JavaCIPUnknownScope.getProject().open(null);
            JavaCIPUnknownScope.getProject().setDescription(projectDescription, null);
            JavaCIPUnknownScope.addClasspaths(classpathEntries, JavaCIPUnknownScope.getProject());
            javaProject.setRawClasspath(classpathEntries.toArray(new IClasspathEntry[classpathEntries.size()]), null);
            javaProject.setOutputLocation(new Path("/" + JavaCIPUnknownScope.projectPage.getProjectName() + "/bin"), null);
            JavaCIPUnknownScope.createFiles();
            return true;
        } catch (RuntimeException exception) {
            StatusManager.getManager().handle(new Status(IStatus.ERROR, JavaCIPUnknownScope.getPluginID(), "Problem creating " + JavaCIPUnknownScope.getProjectTypeName() + " project. Ignoring.", exception));
            try {
                JavaCIPUnknownScope.getProject().delete(true, null);
            } catch (RuntimeException e) {
            }
            return false;
        }
    }
}
