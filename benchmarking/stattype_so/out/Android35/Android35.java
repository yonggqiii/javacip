public class Android35 {

    private void recordVideoStart() {
        MediaRecorder recorder = new MediaRecorder();
        if (recorder != null) {
            recorder.stop();
            recorder.release();
        }
        File file = new File("/sdcard/videoTest.3gpp");
        // try {
        file.createNewFile();
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        recorder = new MediaRecorder();
        recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(file.getAbsolutePath());
        recorder.setVideoSize(176, 144);
        recorder.setVideoFrameRate(15);
        // recorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
        recorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        // try {
        recorder.prepare();
        // } catch (IllegalStateException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        recorder.start();
    }
}
