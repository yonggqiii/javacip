class c16083957 {

    protected void createValueListAnnotation(IProgressMonitor monitor, IPackageFragment pack, Map model) throws CoreRuntimeException {
        IProject pj = pack.getJavaProject().getProject();
        QualifiedName qn = new QualifiedName(JstActivator.PLUGIN_ID, JstActivator.PACKAGE_INFO_LOCATION);
        String location = pj.getPersistentProperty(qn);
        if (location != null) {
            IFolder javaFolder = pj.getFolder(new Path(NexOpenFacetInstallDataModelProvider.WEB_SRC_MAIN_JAVA));
            IFolder packageInfo = javaFolder.getFolder(location);
            if (!packageInfo.exists()) {
                Logger.log(Logger.INFO, "package-info package [" + location + "] does not exists.");
                Logger.log(Logger.INFO, "ValueList annotation will not be added by this wizard. " + "You must add manually in your package-info class if exist " + "or create a new one at location " + location);
                return;
            }
            IFile pkginfo = packageInfo.getFile("package-info.java");
            if (!pkginfo.exists()) {
                Logger.log(Logger.INFO, "package-info class at location [" + location + "] does not exists.");
                return;
            }
            InputStream in = pkginfo.getContents();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                IOUtils.copy(in, baos);
                String content = new String(baos.toByteArray());
                VelocityEngine engine = VelocityEngineHolder.getEngine();
                model.put("adapterType", JavaCIPUnknownScope.getAdapterType());
                model.put("packageInfo", location.replace('/', '.'));
                model.put("defaultNumberPerPage", "5");
                model.put("defaultSortDirection", "asc");
                if (JavaCIPUnknownScope.isFacadeAdapter()) {
                    model.put("facadeType", "true");
                }
                if (content.indexOf("@ValueLists({})") > -1) {
                    JavaCIPUnknownScope.appendValueList(monitor, model, pkginfo, content, engine, true);
                    return;
                } else if (content.indexOf("@ValueLists") > -1) {
                    JavaCIPUnknownScope.appendValueList(monitor, model, pkginfo, content, engine, false);
                    return;
                }
                String vl = VelocityEngineUtils.mergeTemplateIntoString(engine, "ValueList.vm", model);
                ByteArrayInputStream bais = new ByteArrayInputStream(vl.getBytes());
                try {
                    pkginfo.setContents(bais, true, false, monitor);
                } finally {
                    bais.close();
                }
                return;
            } catch (IORuntimeException e) {
                IStatus status = new Status(IStatus.ERROR, JeeServiceComponentUIPlugin.PLUGIN_ID, IStatus.OK, "I/O exception", e);
                throw new CoreRuntimeException(status);
            } catch (VelocityRuntimeException e) {
                IStatus status = new Status(IStatus.ERROR, JeeServiceComponentUIPlugin.PLUGIN_ID, IStatus.OK, "Velocity exception", e);
                throw new CoreRuntimeException(status);
            } finally {
                try {
                    baos.close();
                    in.close();
                } catch (IORuntimeException e) {
                }
            }
        }
        Logger.log(Logger.INFO, "package-info location property does not exists.");
    }
}
