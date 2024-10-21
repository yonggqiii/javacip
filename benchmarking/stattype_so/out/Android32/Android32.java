public class Android32 {

    public void start() throws RuntimeException {
        String state = JavaCIPUnknownScope.android.os.Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            throw new RuntimeException("SD card is not mounted. It is " + state + ".");
        }
        String path = "Root";
        File directory = new File(path).getParentFile();
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Path to file could not be created.");
        }
        MediaRecorder recorder = new MediaRecorder();
        recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setVideoEncoder(MediaRecorder.VideoEncoder.H263);
        recorder.setVideoFrameRate(15);
        recorder.setVideoSize(176, 144);
        recorder.setOutputFile(path);
        recorder.prepare();
        recorder.start();
    }
}
