import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.util.Properties;

/**
 * @author Chanaka Lakmal
 */
public class ValidateKeyPair {

    public static void main(String[] args) throws JSchException {
        JSch jsch = new JSch();

        final String keyFilePath = "/home/chanaka/.ssh/id_rsa";
        final String keyPassword = null;

        if (keyFilePath != null) {
            if (keyPassword != null) {
                jsch.addIdentity(keyFilePath, keyPassword);
            } else {
                jsch.addIdentity(keyFilePath);
            }
        }

        Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");

        Session session = jsch.getSession("chanaka", "localhost");
        session.setConfig(config);
        session.connect();
        System.out.println("Connected !");
        System.exit(0);
    }
}