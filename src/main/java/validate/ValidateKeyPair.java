package validate;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.Properties;

/**
 * @author Chanaka Lakmal
 */
public class ValidateKeyPair {

    public static void main(String[] args) throws Exception {
        ValidateKeyPair validateKeyPair = new ValidateKeyPair();
        validateKeyPair.connect();
        System.exit(0);
    }

    public void connect() throws Exception {
        JSch jsch = new JSch();

        final String keyFilePath = getClass().getClassLoader().getResource("id_rsa").toURI().getPath();
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
    }
}