package basic.ssh;

import com.jcraft.jsch.Session;

/**
 * @author Chanaka Lakmal
 */
public class SSHFileList {

    public static void main(String[] args) {
        try {
            String remoteA = "/tmp/scp/remote-a/";
            String remoteB = "/tmp/scp/remote-b/";
            String local = "/tmp/scp/local/";
            String file = "abc.txt";

            String userName = "chanaka";
            String host = "192.168.1.143";
            String keyFilePath = "/home/chanaka/.ssh/id_rsa";
            String keyPassword = null;
            int timeOut = 60000;

            SSHManager instance = new SSHManager(userName, host, 22, keyFilePath, keyPassword, timeOut);
            Session jschSession = instance.connect();

            String command = "ls /tmp/scp/remote-a/";
            String out = instance.sendCommand(command);
            String arr[] = out.split("\n");

            for (String s : arr) {
                System.out.println("> " + s);
            }

            instance.close();
            System.out.println("Done !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
