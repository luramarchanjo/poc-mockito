package app;

import app.service.HttpClientImpl;
import lombok.Data;
import okhttp3.Response;

@Data
public class HttpClientMock extends HttpClientImpl {

  private Response mockResponse;

  public HttpClientMock(String url) {
    super(url);
  }

  @Override
  public Response post(String path, Object payload) {
    return mockResponse;
  }

}
