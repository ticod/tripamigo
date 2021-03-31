package kr.tripamigo.tripamigo.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.tripamigo.tripamigo.domain.board.Info;
import kr.tripamigo.tripamigo.domain.board.Magazine;
import kr.tripamigo.tripamigo.service.BoardService;
import kr.tripamigo.tripamigo.service.CommentService;
import kr.tripamigo.tripamigo.service.InfoService;

@Controller
@RequestMapping("/community")
public class SearchController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private InfoService infoService;

	@GetMapping("/search")
	public String home(Model model) {
		return "community/search";
	}
	
	@RequestMapping("/communitySearch")
	public String communitySearch(String sido, String gugun, String findString, Model model) throws Exception{
		
		List<Magazine> searchMagazineList = boardService.searchMagazineList(sido, gugun, findString);
		List<Info> searchInfoList = infoService.searchInfoList(sido, gugun, findString);
		
		model.addAttribute("searchMagazineList",searchMagazineList);
		model.addAttribute("searchInfoList",searchInfoList);
		
		
		return "community/communitySearch";
	}
	
	@RequestMapping("/dataSearch")
	public String dataSearch(String sido, String gugun, String findString, Model model) throws Exception {
		System.out.println("시/도 : " + sido);
		System.out.println("군/구 : " + gugun);
		
		StringBuilder urlBuilder = new StringBuilder("http://openapi.tour.go.kr/openapi/service/TourismResourceService/getTourResourceList"); /* URL */
		
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + "ICWUxsvP%2BvmkCpGY5KFkhlmpPlrK79ZO58tgcgEWyULmJFJWL3Iq0GDV3fI3kQueyjYr4jUi9g9pLHDIdEgLiA%3D%3D"); /* Service Key */
		urlBuilder.append("&" + URLEncoder.encode("SIDO", "UTF-8") + "=" + URLEncoder.encode(sido, "UTF-8")); /* 시도 */
		urlBuilder.append("&" + URLEncoder.encode("GUNGU", "UTF-8") + "=" + URLEncoder.encode(gugun, "UTF-8")); /* 시군구 */
		if(!findString.equals("") || findString != null) {
			urlBuilder.append("&" + URLEncoder.encode("RES_NM", "UTF-8") + "=" + URLEncoder.encode(findString, "UTF-8")); /* 검색어 */	
		}
		urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /* 관광지 */
		
		
		//SIDO, GUNGU 필수입력 키
		String line = "";
		String str = "";
		
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("Accept", "application/xml");
		conn.setRequestProperty("Content-type", "application/json");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); 
		while((str = br.readLine()) != null){
			line += str;
		}
		
//		System.out.println(line);
		Document doc = null;

		Map<Integer, List<String>> map = new TreeMap<>();
		List<String> list;
		
		try {
			doc = Jsoup.parse(line);
			
			Elements e1 = doc.select("item");
			
			for(Element ele : e1) {
				list = new ArrayList();
				list.add("<a href='https://www.google.com/search?q="+ele.select("BResNm").text()+"' target='_blank'><h2 style='font-weight:bold;'>"+ele.select("BResNm").text()+"</h2></a>");	
				list.add("<b class='w3-small'>분류 : "+ele.select("ASctnNm").text()+"</b>");
				list.add("<p class='w3-padding'>"+ele.select("EPreSimpleDesc").text()+"</p>");
				
				map.put(Integer.parseInt(ele.select("ARnum").text()), list);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
//		String tmp = result.toString();
//		System.out.println(tmp);
//		System.out.println(map);
		
		for (Object o : map.entrySet()) {
			System.out.println(o);
		}
		model.addAttribute("publicDataPlaceSearchMap", map);
		
		String sidoR=sido;
		String gugunR=gugun;
		String findStringR=findString;
		
		model.addAttribute("sido",sidoR);
		model.addAttribute("gugun",gugunR);
		model.addAttribute("findString",findStringR);
		
		Map<String, List<String>> festivalSearchMap = festival(sidoR, gugunR, findStringR);
		model.addAttribute("publicDataFestivalSearchMap", festivalSearchMap);
		
		return "community/dataSearch";

	}
	
	
	public Map<String, List<String>> festival(String sido, String gugun, String findString) throws Exception{
		
		Map<String, List<String>> map = new TreeMap<>();
		
		String urlStr = "http://api.kcisa.kr/openapi/service/rest/meta4/getKCPG0504?serviceKey=ae97c800-07e7-4e26-ab66-615ae4d63c67&numOfRows=1200";
		URL url = new URL(urlStr);
		
		String line = "";
		String str = "";
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("Accept", "application/xml");
		conn.setRequestProperty("Content-type", "application/json");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); 
		
		while((str = br.readLine()) != null){
			line += str;
		}
		String sido1 = sido;
		if(sido1.length()>=5) {
			sido1 = sido1.substring(0,2);
			System.out.println(sido1);
		}
		
		List<String> list = new ArrayList<>();
		
		Document doc = null;
		try {
			doc = Jsoup.parse(line);
			
			Elements e1 = doc.select("item");
			
			for(Element ele : e1) {
				list = new ArrayList();
				
				if(ele.select("spatial").text().contains(sido1) && ele.select("spatial").text().contains(gugun)) {
					if(!findString.equals("") || findString!=null) {
						if(ele.select("title").text().contains(findString)
								|| ele.select("description").text().contains(findString)
								|| ele.select("subjectCategory").text().contains(findString)
						) {
							list.add("<b class='w3-small'>분류 : "+((ele.select("subjectCategory").text().equals("") || ele.select("subjectCategory").text().equals("null"))? "기타": ele.select("subjectCategory").text())+"</b>");
							list.add("<p class='w3-padding'>"+ele.select("description").text()+"</p>");
							map.put("<a href='https://www.google.com/search?q="+ele.select("title").text()+"' target='_blank'><h2 style='font-weight:bold;'>"+ele.select("title").text()+"</h2></a>", list);
						}
					}else {
						list.add("<b class='w3-small'>분류 : "+ele.select("subjectCategory").text()+"</b>");
						list.add("<p class='w3-padding'>"+ele.select("description").text()+"</p>");
						map.put("<a href='https://www.google.com/search?q="+ele.select("title").text()+"' target='_blank'><h2 style='font-weight:bold;'>"+ele.select("title").text()+"</h2></a>", list);
					}
				}
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("축제검색완료");
		return map;
		
	} 

}
