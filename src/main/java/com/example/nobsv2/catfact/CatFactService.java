package com.example.nobsv2.catfact;

import com.example.nobsv2.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class CatFactService implements Query<Integer, CatFactDTO> {

    private final RestTemplate restTemplate;

    private final static String url = "https://catfact.ninja/fact";

    private final static String MAX_LENGTH = "max_length";

    private final static String ACCEPT = "Accept";

    private final static String MEDIA_TYPE = "application/json";

    public CatFactService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<CatFactDTO> execute(Integer input) {
        // sets up URL
        URI uri = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParam(MAX_LENGTH, input)
                .build()
                .toUri();

        // headers
        HttpHeaders headers = new HttpHeaders();
        headers.set(ACCEPT, MEDIA_TYPE);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // handle a cat fact error
        try {
            ResponseEntity<CatFactResponse> response = restTemplate.exchange(uri, HttpMethod.GET, entity, CatFactResponse.class);

            CatFactDTO catFactDTO = new CatFactDTO(response.getBody().getFact());
            return ResponseEntity.ok(catFactDTO);
        } catch (Exception exception) {
            throw new RuntimeException("Cat Facts API is down");
        }

    }
}
