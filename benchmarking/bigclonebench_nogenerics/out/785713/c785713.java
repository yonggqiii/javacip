class c785713 {

    public void run() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            ChannelMap cm = new ChannelMap();
            for (int i = 0; i < JavaCIPUnknownScope.picm.NumberOfChannels(); i++) {
                cm.Add(JavaCIPUnknownScope.picm.GetName(i));
            }
            String[] folder = JavaCIPUnknownScope.picm.GetFolderList();
            for (int i = 0; i < folder.length; i++) {
                cm.AddFolder(folder[i]);
            }
            JavaCIPUnknownScope.sink.Request(cm, JavaCIPUnknownScope.picm.GetRequestStart(), JavaCIPUnknownScope.picm.GetRequestDuration(), JavaCIPUnknownScope.picm.GetRequestReference());
            cm = JavaCIPUnknownScope.sink.Fetch(JavaCIPUnknownScope.timeout);
            if (cm.GetIfFetchTimedOut()) {
                System.err.println("Signature Data Fetch Timed Out!");
                JavaCIPUnknownScope.picm.Clear();
            } else {
                md.reset();
                folder = cm.GetFolderList();
                for (int i = 0; i < folder.length; i++) JavaCIPUnknownScope.picm.AddFolder(folder[i]);
                int sigIdx = -1;
                for (int i = 0; i < cm.NumberOfChannels(); i++) {
                    String chan = cm.GetName(i);
                    if (chan.endsWith("/_signature")) {
                        sigIdx = i;
                        continue;
                    }
                    int idx = JavaCIPUnknownScope.picm.GetIndex(chan);
                    if (idx == -1)
                        idx = JavaCIPUnknownScope.picm.Add(chan);
                    JavaCIPUnknownScope.picm.PutTimeRef(cm, i);
                    JavaCIPUnknownScope.picm.PutDataRef(idx, cm, i);
                    md.update(cm.GetData(i));
                    md.update((new Double(cm.GetTimeStart(i))).toString().getBytes());
                }
                if (cm.NumberOfChannels() > 0) {
                    byte[] amd = md.digest(JavaCIPUnknownScope.signature.getBytes());
                    if (sigIdx >= 0) {
                        if (MessageDigest.isEqual(amd, cm.GetDataAsByteArray(sigIdx)[0])) {
                            System.err.println(JavaCIPUnknownScope.pluginName + ": signature matched for: " + cm.GetName(0));
                        } else {
                            System.err.println(JavaCIPUnknownScope.pluginName + ": failed signature test, sending null response");
                            JavaCIPUnknownScope.picm.Clear();
                        }
                    } else {
                        System.err.println(JavaCIPUnknownScope.pluginName + ": _signature attached for: " + cm.GetName(0));
                        int idx = JavaCIPUnknownScope.picm.Add("_signature");
                        JavaCIPUnknownScope.picm.PutTime(0., 0.);
                        JavaCIPUnknownScope.picm.PutDataAsByteArray(idx, amd);
                    }
                }
            }
            JavaCIPUnknownScope.plugin.Flush(JavaCIPUnknownScope.picm);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        if (JavaCIPUnknownScope.threadStack.size() < 4)
            JavaCIPUnknownScope.threadStack.push(this);
        else
            JavaCIPUnknownScope.sink.CloseRBNBConnection();
    }
}
