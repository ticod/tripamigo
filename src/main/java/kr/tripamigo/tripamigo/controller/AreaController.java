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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Controller
@RequestMapping("/area")
public class AreaController {

    @Autowired
    private InfoService infoService;

    private boolean isEmptyString(String target) {
        return target == null || "".trim().equals(target);
    }

    @RequestMapping("/summary")
    @ResponseBody
    AreaSummaryDTO summary(String address, String placeName) {
        return infoService.getAreaDataBy(address, placeName);
    }

    @RequestMapping("/search")
    @ResponseBody
    JSONObject search(PlaceSearchDTO placeSearchDTO) {

        if (isEmptyString(placeSearchDTO.getInput())) {
            return null;
        }

        String stringUri = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?key="
                + APIKey.GOOGLE_MAP
                + "&input='" + placeSearchDTO.getInput() + "'"
                + "&inputtype=textquery"
                + "&fields=formatted_address,name,place_id";

        RestTemplate restTemplate = new RestTemplate();
        URI uri = URI.create(stringUri);

        ResponseEntity<JSONObject> apiResponse = restTemplate.getForEntity(uri, JSONObject.class);
        JSONObject responseBody = apiResponse.getBody();

        if (responseBody == null || !responseBody.get("status").equals("OK")) {
            return null;
        }

        return responseBody;
    }

    @RequestMapping("/direction")
    @ResponseBody
    JSONObject direction(@RequestParam("org") String org,
                         @RequestParam("des") String des) {

        if (isEmptyString(org) || isEmptyString(des)) {
            return null;
        }

        String stringUri = "https://maps.googleapis.com/maps/api/directions/json?key="
                + APIKey.GOOGLE_MAP
                + "&origin=place_id:" + org
                + "&destination=place_id:" + des
                + "&mode=transit"
                + "&departure_time=now";

        RestTemplate restTemplate = new RestTemplate();
        URI uri = URI.create(stringUri);
        ResponseEntity<JSONObject> apiResponse = restTemplate.getForEntity(uri, JSONObject.class);
        JSONObject responseBody = apiResponse.getBody();

        System.out.println(responseBody);

        if (responseBody == null || !responseBody.get("status").equals("OK")) {
            return null;
        }

        return responseBody;
    }

}
