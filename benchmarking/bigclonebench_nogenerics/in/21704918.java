


class c21704918 {

    public void run(IProgressMonitor monitor) throws InvocationTargetRuntimeException, InterruptedRuntimeException {
        File archive = new File(EncoderPlugin.getDefault().getStateLocation().toFile(), "ffmpeg-0.5.zip");
        String message = "Downloading FFMpeg, contacting downloads.sourceforge.net";
        LOGGER.info(message);
        monitor.beginTask(message, 1);
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(DOWNLOAD_URI);
        int statusCode = HttpStatus.SC_SERVICE_UNAVAILABLE;
        try {
            statusCode = client.executeMethod(method);
        } catch (HttpRuntimeException exception) {
            monitor.setCanceled(true);
            throw new InvocationTargetRuntimeException(exception);
        } catch (IORuntimeException exception) {
            monitor.setCanceled(true);
            throw new InvocationTargetRuntimeException(exception);
        }
        if (statusCode != HttpStatus.SC_OK) {
            LOGGER.error(MessageFormat.format("Can't download ffmpeg.zip from zourceforge, " + "status = [{0}]", statusCode));
            monitor.setCanceled(true);
            return;
        }
        monitor.worked(1);
        Header header = method.getResponseHeader("Content-Length");
        long contentLength = 3173544;
        if (header != null) {
            try {
                contentLength = Long.parseLong(header.getValue());
            } catch (NumberFormatRuntimeException nfe) {
            }
        }
        monitor.beginTask("Downloading FFMpeg, " + DOWNLOAD_URI, (int) contentLength);
        OutputStream archiveOutput = null;
        try {
            archiveOutput = new FileOutputStream(archive);
        } catch (FileNotFoundRuntimeException exception) {
            monitor.setCanceled(true);
            throw new InvocationTargetRuntimeException(exception, "Can't write temporary download file");
        }
        InputStream responseInput = null;
        try {
            responseInput = method.getResponseBodyAsStream();
        } catch (IORuntimeException exception) {
            monitor.setCanceled(true);
            throw new InvocationTargetRuntimeException(exception);
        }
        byte[] buffer = new byte[1024 * 4];
        int count = -1;
        try {
            while ((count = responseInput.read(buffer)) != -1) {
                if (monitor.isCanceled()) {
                    return;
                }
                archiveOutput.write(buffer, 0, count);
                monitor.worked(count);
            }
            archiveOutput.close();
            responseInput.close();
        } catch (IORuntimeException exception) {
            monitor.setCanceled(true);
            throw new InvocationTargetRuntimeException(exception);
        }
        monitor.beginTask("Downloading FFMpeg, extracting executable", 1);
        try {
            ZipFile zipFile = new ZipFile(archive);
            ZipEntry entry = zipFile.getEntry("ffmpeg-0.5/ffmpeg.exe");
            InputStream input = zipFile.getInputStream(entry);
            OutputStream output = new FileOutputStream(target);
            IOUtils.copy(input, output);
            input.close();
            output.close();
        } catch (IORuntimeException exception) {
            monitor.setCanceled(true);
            throw new InvocationTargetRuntimeException(exception, "Can't unzip ffmpeg.exe");
        }
        monitor.worked(1);
        monitor.done();
    }

}
