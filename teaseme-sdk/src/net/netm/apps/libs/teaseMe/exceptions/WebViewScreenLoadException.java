package net.netm.apps.libs.teaseme.exceptions;

/**
 * Created by ahoban on 29.03.15.
 */
public class WebViewScreenLoadException extends Throwable {


    private int code;

    private Long screenId;

    private String failingUrl;


    public WebViewScreenLoadException(String message, int code) {
        super(message);
        this.code = code;

    }

    public WebViewScreenLoadException(String message, int code, String failingUrl, long screenId) {
       this(message,code);
       this.failingUrl = failingUrl;
       this.screenId = screenId;

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getFailingUrl() {
        return failingUrl;
    }

    public void setFailingUrl(String failingUrl) {
        this.failingUrl = failingUrl;
    }

    public Long getScreenId() {
        return screenId;
    }

    public void setScreenId(Long screenId) {
        this.screenId = screenId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebViewScreenLoadException that = (WebViewScreenLoadException) o;

        if (code != that.code) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return code;
    }
}
