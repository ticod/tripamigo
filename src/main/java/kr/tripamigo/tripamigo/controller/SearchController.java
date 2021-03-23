package kr.tripamigo.tripamigo.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.tripamigo.tripamigo.service.CommentService;

@Controller
@RequestMapping("/community")
public class SearchController {

	@Autowired
	private CommentService commentService;

	@GetMapping("/search")
	public String home(Model model) {
		return "community/search";
	}

	@RequestMapping("/dataSearch")
	public String dataSearch(String area, Model model) throws Exception {
		System.out.println("검색지역 : " + area);

//		String apiUrl = "http://openapi.tour.go.kr/openapi/service/TourismResourceService/getTourResourceList?"
//		+"serviceKey=ICWUxsvP%2BvmkCpGY5KFkhlmpPlrK79ZO58tgcgEWyULmJFJWL3Iq0GDV3fI3kQueyjYr4jUi9g9pLHDIdEgLiA%3D%3D";

		StringBuilder urlBuilder = new StringBuilder(
				"http://openapi.tour.go.kr/openapi/service/TourismResourceService/getTourResourceList"); /* URL */
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "="
				+ "ICWUxsvP%2BvmkCpGY5KFkhlmpPlrK79ZO58tgcgEWyULmJFJWL3Iq0GDV3fI3kQueyjYr4jUi9g9pLHDIdEgLiA%3D%3D"); /* Service Key */
		urlBuilder
				.append("&" + URLEncoder.encode("SIDO", "UTF-8") + "=" + URLEncoder.encode("서울특별시", "UTF-8")); /* 시도 */
		urlBuilder
				.append("&" + URLEncoder.encode("GUNGU", "UTF-8") + "=" + URLEncoder.encode("종로구", "UTF-8")); /* 시군구 */
		urlBuilder.append("&" + URLEncoder.encode("RES_NM", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /* 관광지 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println(sb.toString());

		return "community/search";

	}

}
