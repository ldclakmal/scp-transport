import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author Chanaka Lakmal
 */
public class NewParsedURL {

    private String originalURL;
    private String scheme;
    private String username;
    private String password;
    private String hostname;
    private int port = -1;
    private String path;
    private String directory;
    private String file;
    private String trailer;
    private Map<String, String> options = null;

    public NewParsedURL(String url) throws MalformedURLException {
        this.originalURL = url;

        // 1. look for scheme://
        int pos = url.indexOf("://");
        if (pos == -1) {
            if (url.contains("@")) {
                // this is an email address
                return;
            } else {
                throw new MalformedURLException("Expected URL of the form <scheme>://... : " + url);
            }
        }
        scheme = url.substring(0, pos);
        url = url.substring(pos + 3);

        // 2. look for username:password
        pos = url.lastIndexOf("@");
        if (pos != -1) {
            String userAndPass = url.substring(0, pos);
            url = url.substring(pos + 1);

            // do we have a password as well?
            pos = userAndPass.indexOf(":");
            if (pos == -1) {
                // only a username
                username = userAndPass;
            } else {
                username = userAndPass.substring(0, pos);
                password = userAndPass.substring(pos + 1);
            }
        }

        // decode URL encoded usernames and passwords
        try {
            if (username != null) {
                username = URLDecoder.decode(username, "UTF-8");
            }
            if (password != null) {
                password = URLDecoder.decode(password, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            throw new MalformedURLException("Expected username and password to be UTF-8 encoded");
        }

        if (!scheme.equals("file")) {

            // 3. look for remote host
            pos = url.indexOf("/");
            String hostAndPort;
            if (pos == -1) {
                hostAndPort = url;
                url = "/";
            } else {
                hostAndPort = url.substring(0, pos);
                url = url.substring(pos);
            }

            // do we have a port as well?
            pos = hostAndPort.indexOf(":");
            if (pos == -1) {
                // only a host
                hostname = hostAndPort;
            } else {
                hostname = hostAndPort.substring(0, pos);
                if (!scheme.equals("scp")) {
                    port = Integer.parseInt(hostAndPort.substring(pos + 1));
                } else {
                    port = 22;
                }
            }
        }

        // 4. separate path and options of the form /directory/file.ext?option1=value;option2=value&option3=value
        pos = url.indexOf("?");
        if (pos != -1) {
            processOptions(url.substring(pos + 1));
            path = url.substring(0, pos);
        } else {
            path = url;
        }

        // 5. separate into directory and file
        pos = path.lastIndexOf("/");
        if (pos != -1) {
            directory = path.substring(0, pos);
            file = path.substring(pos + 1);
        } else {
            file = path;
        }
    }

    // option1=value;option2=value&option3=value
    private void processOptions(String opts) throws MalformedURLException {
        try {
            trailer = opts;
            options = new HashMap<>();
            StringTokenizer st = new StringTokenizer(opts, "&;");
            while (st.hasMoreTokens()) {
                String[] s = st.nextToken().split("=");
                options.put(s[0], s[1]);
            }
        } catch (Exception e) {
            throw new MalformedURLException("Expected URL options of the form ?opt=val;opt=val&opt=val etc : " + opts);
        }
    }
}
