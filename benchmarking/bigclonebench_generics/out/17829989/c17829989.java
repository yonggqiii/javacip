class c17829989 {

    public void run() {
        File dir = new File(JavaCIPUnknownScope.loggingDir);
        if (!dir.isDirectory()) {
            JavaCIPUnknownScope.logger.error("Logging directory \"" + dir.getAbsolutePath() + "\" does not exist.");
            return;
        }
        File file = new File(dir, new Date().toString().replaceAll("[ ,:]", "") + "LoadBalancerLog.txt");
        FileWriter writer;
        try {
            writer = new FileWriter(file);
        } catch (IORuntimeException e) {
            e.printStackTrace();
            return;
        }
        int counter = 0;
        while (!JavaCIPUnknownScope.isInterrupted() && counter < JavaCIPUnknownScope.numProbes) {
            try {
                writer.write(System.currentTimeMillis() + "," + JavaCIPUnknownScope.currentPending + "," + JavaCIPUnknownScope.currentThreads + "," + JavaCIPUnknownScope.droppedTasks + "," + JavaCIPUnknownScope.executionRuntimeExceptions + "," + JavaCIPUnknownScope.currentWeight + "," + JavaCIPUnknownScope.averageWaitTime + "," + JavaCIPUnknownScope.averageExecutionTime + "#");
                writer.flush();
            } catch (IORuntimeException e) {
                e.printStackTrace();
                break;
            }
            counter++;
            try {
                JavaCIPUnknownScope.sleep(JavaCIPUnknownScope.probeTime);
            } catch (InterruptedRuntimeException e) {
                e.printStackTrace();
                break;
            }
        }
        try {
            writer.close();
        } catch (IORuntimeException e) {
            e.printStackTrace();
            return;
        }
        FileReader reader;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundRuntimeException e2) {
            e2.printStackTrace();
            return;
        }
        Vector<StatStorage> dataV = new Vector<StatStorage>();
        int c;
        try {
            c = reader.read();
        } catch (IORuntimeException e1) {
            e1.printStackTrace();
            c = -1;
        }
        String entry = "";
        Date startTime = null;
        Date stopTime = null;
        while (c != -1) {
            if (c == 35) {
                String[] parts = entry.split(",");
                if (startTime == null)
                    startTime = new Date(Long.parseLong(parts[0]));
                if (parts.length > 0)
                    dataV.add(JavaCIPUnknownScope.parse(parts));
                stopTime = new Date(Long.parseLong(parts[0]));
                entry = "";
            } else {
                entry += (char) c;
            }
            try {
                c = reader.read();
            } catch (IORuntimeException e) {
                e.printStackTrace();
            }
        }
        try {
            reader.close();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        if (dataV.size() > 0) {
            int[] dataPending = new int[dataV.size()];
            int[] dataOccupied = new int[dataV.size()];
            long[] dataDropped = new long[dataV.size()];
            long[] dataRuntimeException = new long[dataV.size()];
            int[] dataWeight = new int[dataV.size()];
            long[] dataExecution = new long[dataV.size()];
            long[] dataWait = new long[dataV.size()];
            for (int i = 0; i < dataV.size(); i++) {
                dataPending[i] = dataV.get(i).pending;
                dataOccupied[i] = dataV.get(i).occupied;
                dataDropped[i] = dataV.get(i).dropped;
                dataRuntimeException[i] = dataV.get(i).exceptions;
                dataWeight[i] = dataV.get(i).currentWeight;
                dataExecution[i] = (long) dataV.get(i).executionTime;
                dataWait[i] = (long) dataV.get(i).waitTime;
            }
            String startName = startTime.toString();
            startName = startName.replaceAll("[ ,:]", "");
            file = new File(dir, startName + "pending.gif");
            SimpleChart.drawChart(file, 640, 480, dataPending, startTime, stopTime, new Color(0, 0, 0));
            file = new File(dir, startName + "occupied.gif");
            SimpleChart.drawChart(file, 640, 480, dataOccupied, startTime, stopTime, new Color(0, 0, 0));
            file = new File(dir, startName + "dropped.gif");
            SimpleChart.drawChart(file, 640, 480, dataDropped, startTime, stopTime, new Color(0, 0, 0));
            file = new File(dir, startName + "exceptions.gif");
            SimpleChart.drawChart(file, 640, 480, dataRuntimeException, startTime, stopTime, new Color(0, 0, 0));
            file = new File(dir, startName + "weight.gif");
            SimpleChart.drawChart(file, 640, 480, dataWeight, startTime, stopTime, new Color(0, 0, 0));
            file = new File(dir, startName + "execution.gif");
            SimpleChart.drawChart(file, 640, 480, dataExecution, startTime, stopTime, new Color(0, 0, 0));
            file = new File(dir, startName + "wait.gif");
            SimpleChart.drawChart(file, 640, 480, dataWait, startTime, stopTime, new Color(0, 0, 0));
        }
        JavaCIPUnknownScope.recordedExecutionThreads = 0;
        JavaCIPUnknownScope.recordedWaitingThreads = 0;
        JavaCIPUnknownScope.averageExecutionTime = 0;
        JavaCIPUnknownScope.averageWaitTime = 0;
        if (!JavaCIPUnknownScope.isLocked) {
            JavaCIPUnknownScope.debugThread = new DebugThread();
            JavaCIPUnknownScope.debugThread.start();
        }
    }
}
