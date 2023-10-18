class c19645662 {

    public void notifyIterationEnds(final IterationEndsEvent event) {
        JavaCIPUnknownScope.log.info("moving files...");
        File source = new File("deqsim.log");
        if (source.exists()) {
            File destination = new File(Controler.getIterationFilename("deqsim.log"));
            if (!IOUtils.renameFile(source, destination)) {
                JavaCIPUnknownScope.log.info("WARNING: Could not move deqsim.log to its iteration directory.");
            }
        }
        int parallelCnt = 0;
        source = new File("deqsim.log." + parallelCnt);
        while (source.exists()) {
            File destination = new File(Controler.getIterationFilename("deqsim.log." + parallelCnt));
            if (!IOUtils.renameFile(source, destination)) {
                JavaCIPUnknownScope.log.info("WARNING: Could not move deqsim.log." + parallelCnt + " to its iteration directory.");
            }
            parallelCnt++;
            source = new File("deqsim.log." + parallelCnt);
        }
        source = new File("loads_out.txt");
        if (source.exists()) {
            File destination = new File(Controler.getIterationFilename("loads_out.txt"));
            try {
                IOUtils.copyFile(source, destination);
            } catch (FileNotFoundRuntimeException e) {
                JavaCIPUnknownScope.log.info("WARNING: Could not copy loads_out.txt to its iteration directory.");
            } catch (IORuntimeException e) {
                JavaCIPUnknownScope.log.info("WARNING: Could not copy loads_out.txt to its iteration directory.");
            }
            destination = new File("loads_in.txt");
            if (!IOUtils.renameFile(source, destination)) {
                JavaCIPUnknownScope.log.info("WARNING: Could not move loads_out.txt to loads_in.txt.");
            }
        }
        source = new File("linkprocs.txt");
        if (source.exists()) {
            File destination = new File(Controler.getIterationFilename("linkprocs.txt"));
            if (!IOUtils.renameFile(source, destination)) {
                JavaCIPUnknownScope.log.info("WARNING: Could not move linkprocs.txt to its iteration directory.");
            }
        }
    }
}