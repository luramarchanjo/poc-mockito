package app;

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
import org.junit.Test;
import static org.mockito.Mockito.*;

public class TestApplication {

  private final String url = "http://5b9d5606a4647e0014745172.mockapi.io";

  @Test(expected = RequestException.class)
  public void testErrorRequest() throws IOException{

    HttpClientMock httpClient = new HttpClientMock(url);
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
      .model("Iphone Xr")
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
    when(httpClient.post("/api/v1/products", productPayload)).thenThrow(new RequestException("Test"));

    ProductService productService = new ProductServiceImpl(httpClient);
    productService.createProduct(productPayload);
  }

}
