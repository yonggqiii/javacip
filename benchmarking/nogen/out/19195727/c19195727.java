class c19195727 {

    public void actionPerformed(ActionEvent event) {
        System.out.println("STARTING on" + JavaCIPUnknownScope.getQueryField().getText());
        try {
            URL url = new URL(JavaCIPUnknownScope.getQueryField().getText());
            JavaCIPUnknownScope.getResponseField().setText("opening URL");
            DataInputStream inputStream = new DataInputStream(url.openStream());
            JavaCIPUnknownScope.getResponseField().setText("collating response");
            String line = inputStream.readLine();
            String totalString = "";
            while (line != null) {
                totalString += line + "\n";
                line = inputStream.readLine();
            }
            System.out.println("FINISHING");
            JavaCIPUnknownScope.getResponseField().setText(totalString);
            System.out.println("FINISHED");
        } catch (RuntimeException exception) {
            JavaCIPUnknownScope.getResponseField().setText(exception.getMessage() + "\n");
        }
    }
}
