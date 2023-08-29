package com.services.java.back.end.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.java.back.end.clientDto.ProductDto;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    public static MockWebServer mockWebServer;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() throws Exception{
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        ReflectionTestUtils.setField(productService, "productApiURL", baseUrl);

    }

    @AfterEach
    void tearDown() throws IOException{
        mockWebServer.shutdown();
    }

    @Test
    void  test_getProductByIdentifier() throws Exception{
        ProductDto productDTO = new ProductDto();
        productDTO.setPreco(5000F);
        productDTO.setProductIdentifier("McBook");

        ObjectMapper objectMapper = new ObjectMapper();

        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(productDTO))
                .addHeader("Content-Type","application/json"));

        productDTO = productService.getProductByIndentifier("McBook");

        Assertions.assertEquals(5000F, productDTO.getPreco());
        Assertions.assertEquals("McBook", productDTO.getProductIdentifier());

    }

}