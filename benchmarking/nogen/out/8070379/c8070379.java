class c8070379 {

    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws RuntimeException {
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(JavaCIPUnknownScope.inputResource.getInputStream()));
        File targetDirectoryAsFile = new File(JavaCIPUnknownScope.targetDirectory);
        if (!targetDirectoryAsFile.exists()) {
            FileUtils.forceMkdir(targetDirectoryAsFile);
        }
        File target = new File(JavaCIPUnknownScope.targetDirectory, JavaCIPUnknownScope.targetFile);
        BufferedOutputStream dest = null;
        while (zis.getNextEntry() != null) {
            if (!target.exists()) {
                target.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(target);
            dest = new BufferedOutputStream(fos);
            IOUtils.copy(zis, dest);
            dest.flush();
            dest.close();
        }
        zis.close();
        if (!target.exists()) {
            throw new IllegalStateRuntimeException("Could not decompress anything from the archive!");
        }
        return RepeatStatus.FINISHED;
    }
}
