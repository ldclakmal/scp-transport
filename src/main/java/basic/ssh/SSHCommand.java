package basic.ssh;

import org.junit.Assert;

/**
 * @author Chanaka Lakmal
 */
public class SSHCommand {

    private static String userName = "chanaka";
    private static String host = "192.168.1.143";
    private static String keyFilePath = "/home/chanaka/.ssh/id_rsa";
    private static String keyPassword = null;
    private static int timeOut = 60000;

    public static void main(String[] args) {
        try {
            String command = "ls /tmp/scp/remote-a/";
            SSHManager instance = new SSHManager(userName, host, 22, keyFilePath, keyPassword, timeOut);
            instance.connect();

            String expResult = "abc.txt\n";
            String result = instance.sendCommand(command);
            instance.close();
            Assert.assertEquals(expResult, result);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}