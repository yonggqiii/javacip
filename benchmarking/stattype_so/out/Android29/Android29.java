public class Android29 extends Service {

    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public void onCreate() {
        PowerManager pm = (PowerManager) JavaCIPUnknownScope.getSystemService(Context.POWER_SERVICE);
        WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Tag");
        wl.acquire();
    }

    public void onDestroy() {
        WakeLock wl = null;
        wl.release();
    }
}
