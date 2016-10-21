package basic.ssh;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.util.Properties;

/**
 * @author Chanaka Lakmal
 */
public class SSHTerminal {
    public static void main(String[] args) {
        try {
            String user = "chanaka";
            String host = "192.168.1.143";

            String keyFilePath = "/home/chanaka/.ssh/id_rsa";
            String keyPassword = null;

            JSch jsch = new JSch();

            if (keyFilePath != null) {
                if (keyPassword != null) {
                    jsch.addIdentity(keyFilePath, keyPassword);
                } else {
                    jsch.addIdentity(keyFilePath);
                }
            }

            Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");

            Session session = jsch.getSession(user, host, 22);
            session.setConfig(config);
            session.connect();

            ChannelShell channel = (ChannelShell) session.openChannel("shell");
            channel.setInputStream(System.in);
            channel.setOutputStream(System.out);
            channel.connect();

            System.out.println("sendCommand");
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
}