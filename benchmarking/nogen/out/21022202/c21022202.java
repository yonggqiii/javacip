class c21022202 {

    public void end() throws RuntimeException {
        JavaCIPUnknownScope.handle.waitFor();
        Calendar endTime = Calendar.getInstance();
        File resultsDir = new File(JavaCIPUnknownScope.runDir, "results");
        if (!resultsDir.isDirectory())
            throw new RuntimeException("The results directory not found!");
        String resHtml = null;
        String resTxt = null;
        String[] resultFiles = resultsDir.list();
        for (String resultFile : resultFiles) {
            if (resultFile.indexOf("html") >= 0)
                resHtml = resultFile;
            else if (resultFile.indexOf("txt") >= 0)
                resTxt = resultFile;
        }
        if (resHtml == null)
            throw new IORuntimeException("SPECweb2005 output (html) file not found");
        if (resTxt == null)
            throw new IORuntimeException("SPECweb2005 output (txt) file not found");
        File resultHtml = new File(resultsDir, resHtml);
        JavaCIPUnknownScope.copyFile(resultHtml.getAbsolutePath(), JavaCIPUnknownScope.runDir + "SPECWeb-result.html", false);
        BufferedReader reader = new BufferedReader(new FileReader(new File(resultsDir, resTxt)));
        JavaCIPUnknownScope.logger.fine("Text file: " + resultsDir + resTxt);
        Writer writer = new FileWriter(JavaCIPUnknownScope.runDir + "summary.xml");
        SummaryParser parser = new SummaryParser(JavaCIPUnknownScope.getRunId(), JavaCIPUnknownScope.startTime, endTime, JavaCIPUnknownScope.logger);
        parser.convert(reader, writer);
        writer.close();
        reader.close();
    }
}
