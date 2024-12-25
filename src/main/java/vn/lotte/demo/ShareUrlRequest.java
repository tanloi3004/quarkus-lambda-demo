package vn.lotte.demo;

import java.util.List;
import java.util.UUID;


public class ShareUrlRequest {
  private String url;
  private List<Option> options;
  private String requestId;

  public ShareUrlRequest() {
    // Default constructor for deserialization
  }

  public ShareUrlRequest(String url, List<Option> options) {
    this.url = url;
    this.options = options;
    this.requestId = UUID.randomUUID().toString();
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public List<Option> getOptions() {
    return options;
  }

  public void setOptions(List<Option> options) {
    this.options = options;
  }

  public String getRequestId() {
    return requestId;
  }

  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public static class Option {
    private String key;
    private String value;

    public Option(String key, String value) {
      this.key = key;
      this.value = value;
    }

    public String getKey() {
      return key;
    }

    public void setKey(String key) {
      this.key = key;
    }

    public String getValue() {
      return value;
    }

    public void setValue(String value) {
      this.value = value;
    }
  }
}