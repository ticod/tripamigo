package kr.tripamigo.tripamigo.controller;

import kr.tripamigo.tripamigo.dto.AreaSummaryDTO;
import kr.tripamigo.tripamigo.dto.PlaceSearchDTO;
import kr.tripamigo.tripamigo.service.InfoService;
import kr.tripamigo.tripamigo.util.APIKey;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;

@Controller
@RequestMapping("/area")
public class AreaController {

    @Autowired
    private InfoService infoService;

    @RequestMapping("/summary")
    @ResponseBody
    AreaSummaryDTO summary(String address, String placeName) {
        return infoService.getAreaDataBy(address, placeName);
    }

    @RequestMapping("/search")
    @ResponseBody
    JSONObject search(PlaceSearchDTO placeSearchDTO) {

        String stringUri = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?key="
                + APIKey.GOOGLE_MAP
                + "&input=" + placeSearchDTO.getInput()
                + "&inputtype=textquery"
                + "&fields=formatted_address,name,place_id";

        RestTemplate restTemplate = new RestTemplate();
        URI uri = URI.create(stringUri);

        ResponseEntity<JSONObject> apiResponse = restTemplate.getForEntity(uri, JSONObject.class);
        JSONObject responseBody = apiResponse.getBody();

        if (responseBody == null || !((String) responseBody.get("status")).equals("OK")) {
            return null;
        }

        return responseBody;

//        return PlaceSearchResultDTO.builder()
//                .formattedAddress((String) responseBody.get("formatted_address"))
//                .placeId((String) responseBody.get("formatted_address"))
//                .name((String) responseBody.get("formatted_address"))
//                .build();
    }

}
