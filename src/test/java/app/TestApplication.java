package app;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import app.exception.RequestException;
import app.payload.ProductPayload;
import app.service.HttpClient;
import app.service.HttpClientImpl;
import app.service.ProductService;
import app.service.ProductServiceImpl;
import java.io.IOException;
import java.util.UUID;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Test;

public class TestApplication {

  @Test(expected = RequestException.class)
  public void testWithoutLibrary() {

    HttpClientMock httpClient = new HttpClientMock("http://google.com");
    ProductService productService = new ProductServiceImpl(httpClient);

    // Mock HttpClientMock
    Request request = new Request.Builder()
      .url("http://test.com")
      .build();

    Response response = new Response.Builder()
      .code(500)
      .request(request)
      .protocol(Protocol.HTTP_1_1)
      .message(UUID.randomUUID().toString())
      .build();

    httpClient.setMockResponse(response);

    // Test request
    ProductPayload productPayload = ProductPayload.builder()
      .model("Iphone XR")
      .price(5358.99)
      .build();

    productService.createProduct(productPayload);
  }

  @Test(expected = RequestException.class)
  public void testMockito() {
    ProductPayload productPayload = ProductPayload.builder()
      .model("Iphone Xr")
      .price(5358.99)
      .build();

    HttpClient httpClient = mock(HttpClient.class);
    when(httpClient.post("/api/v1/products", productPayload))
      .thenThrow(new RequestException("Test"));

    ProductService productService = new ProductServiceImpl(httpClient);
    productService.createProduct(productPayload);
  }

  @Test(expected = RequestException.class)
  public void testMockWebServer() throws IOException {
    // Start MockWebServer
    MockWebServer mockWebServer = new MockWebServer();
    mockWebServer.start();

    // Mock response
    MockResponse mockResponse = new MockResponse();
    mockResponse.setResponseCode(500);
    mockResponse.setBody("{}");

    // Enqueue response
    mockWebServer.enqueue(mockResponse);

    // Test request
    String url = String.format("http://%s:%s", mockWebServer.getHostName(), mockWebServer.getPort());
    HttpClient httpClient = new HttpClientImpl(url);
    ProductService productService = new ProductServiceImpl(httpClient);

    ProductPayload productPayload = ProductPayload.builder()
      .model("Iphone XR")
      .price(5358.99)
      .build();

    productService.createProduct(productPayload);
  }

}
