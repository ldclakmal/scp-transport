import java.net.MalformedURLException;

/**
 * @author Chanaka Lakmal
 */
public class Test {

    public static void main(String[] args) throws MalformedURLException {
        String sftp = "sftp://user@host/pat/?keyFilePath=/keyFile/id_dsa";
        String scp = "scp://user@host:/pat/a.txt?keyFilePath=/keyFile/id_dsa&keyPassword=1234";

        NewParsedURL parsedURLSFTP = new NewParsedURL(sftp);
        NewParsedURL parsedURLSFP = new NewParsedURL(scp);
        System.out.println("Done !");
    }
}
