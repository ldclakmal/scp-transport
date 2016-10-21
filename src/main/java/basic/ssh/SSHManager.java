package basic.ssh;

import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Chanaka Lakmal
 */
public class SSHManager {

    private JSch jschSSHChannel;
    private String userName;
    private String host;
    private int port;
    private int timeOut;
    private Session sesConnection;

    public SSHManager(String userName, String host, int port, String keyFilePath,
                      String keyPassword, int timeOut) {

        this.userName = userName;
        this.host = host;
        this.port = port;
        this.timeOut = timeOut;

        jschSSHChannel = new JSch();

        try {
            if (keyFilePath != null) {
                if (keyPassword != null) {
                    jschSSHChannel.addIdentity(keyFilePath, keyPassword);
                } else {
                    jschSSHChannel.addIdentity(keyFilePath);
                }
            }
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    public Session connect() {
        try {
            sesConnection = jschSSHChannel.getSession(userName, host, port);
            sesConnection.setConfig("StrictHostKeyChecking", "no");
            sesConnection.connect(timeOut);
            return sesConnection;
        } catch (JSchException jschX) {
            jschX.printStackTrace();
            return null;
        }
    }

    public String sendCommand(String command) {
        StringBuilder outputBuffer = new StringBuilder();

        try {
            Channel channel = sesConnection.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            InputStream commandOutput = channel.getInputStream();
            channel.connect();
            int readByte = commandOutput.read();

            while (readByte != 0xffffffff) {
                outputBuffer.append((char) readByte);
                readByte = commandOutput.read();
            }

            channel.disconnect();
        } catch (IOException ioX) {
            ioX.printStackTrace();
            return null;
        } catch (JSchException jschX) {
            jschX.printStackTrace();
            return null;
        }

        return outputBuffer.toString();
    }

    public void close() {
        sesConnection.disconnect();
    }

}