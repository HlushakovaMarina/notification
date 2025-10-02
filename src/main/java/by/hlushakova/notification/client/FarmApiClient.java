package by.hlushakova.notification.client;

import by.hlushakova.notification.dto.FarmRequest;
import by.hlushakova.notification.dto.FarmResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;



public class FarmApiClient {
    private final RestTemplate restTemplate;

    @Value("${farm.api.base-url:http://localhost:8080}")
    private String farmApiBaseURL;


    public FarmApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

   public FarmResponse createFarm(FarmRequest farmRequest){
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<FarmRequest> requestEntity = new HttpEntity<>(farmRequest, headers);

            ResponseEntity<FarmResponse> response = restTemplate.exchange(
                    farmApiBaseURL + "/api/farms",
                    HttpMethod.POST,
                    requestEntity,
                    FarmResponse.class
            );
            /*FarmResponse farmResponse = response.getBody();
            System.out.println("Farm created successfully: " + farmResponse.getName());
            return farmResponse;*/
            FarmResponse farmResponse = new FarmResponse();
            farmResponse.setId(175L);
            farmResponse.setName(farmRequest.getName());
            farmResponse.setLocation(farmRequest.getLocation());
            return farmResponse;
        } catch (Exception error){
            System.out.println("Error creating farm: " +  error.getMessage());
            throw new RuntimeException("Failed to create farm", error);
        }
   }
    public FarmResponse createFruit(FarmRequest farmRequest){

    }

}
