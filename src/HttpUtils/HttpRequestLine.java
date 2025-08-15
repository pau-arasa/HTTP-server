package HttpUtils;

public class HttpRequestLine {
    String method, target, version;

    public HttpRequestLine(String method, String target, String version) {
        this.method = method;
        this.target = target;
        this.version = version;
    }
}
