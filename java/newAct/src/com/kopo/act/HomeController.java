package com.kopo.act;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kopo.dto.MemberVO;
import com.kopo.service.MemberService;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	public int id;
	public String name;
	public String school_id;
	public float temperature;
	static int cnt = 0;
	static int step = 0;
	static int count = 0;
	@Inject
	private MemberService service;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model) throws Exception {
		logger.info("start");

		String result = "<h2> 저희는 ACT입니다.</h2>";
		int oldCnt = cnt;
		int newCnt = service.getRecordCount();
		StringBuilder sb = new StringBuilder();
		int timeDelay = 10; // [sec]

		// DB 의 맨 마지막 record
		MemberVO vo = service.selectOne();

		if (newCnt - oldCnt >= 1) {
			step = 1;
		}
		cnt = newCnt;
		name = vo.getName();
		char[] array_word = new char[name.length()];
		for (int i = 0; i < array_word.length; i++) {
			array_word[i] = (name.charAt(i));// 스트링을 한글자씩 끊어 배열에 저장
		}
		if (name.length() <= 3) {
			name = array_word[0] + "*" + array_word[(array_word.length - 1)];
		} else {
			name = array_word[0] + "**" + array_word[(array_word.length - 1)];
		}
		school_id = vo.getSchool_id();
		char[] array_id = new char[school_id.length()];
		for (int i = 0; i < array_id.length; i++) {
			array_id[i] = (school_id.charAt(i));// 스트링을 한글자씩 끊어 배열에 저장
		}
		school_id = array_id[(array_id.length - 4)] + "" + array_id[(array_id.length - 3)] + "" + array_id[(array_id.length - 2)] + ""
				+ array_id[(array_id.length - 1)] + "";

		temperature = vo.getTemperature();
		;
		switch (step) {
		case 1:
			result = sb.toString();
			timeDelay = 1;
			sb.setLength(0);
			System.out.println("--------------------");
			System.out.println("step: 1");
			System.out.println("temperature = " + temperature);
			System.out.println("timeDelay = " + timeDelay);
			if (temperature == 0) {
				System.out.println("--------------------");
				logger.info("step: 1 -> 2");
				step = 2;
			}
			break;
		case 2:
			logger.info("step: 2");
			sb.append("<h3> &nbsp;&nbsp; ").append(school_id).append(" ").append(name).append(" 님 반갑습니다.</h3><br>");
			sb.append("<h3>&nbsp;&nbsp;&nbsp;체온을 측정하겠습니다. </h3>");
			sb.append("<h3>&nbsp;&nbsp;오른쪽 모듈에 손목을 접근하여 주세요. </h3>");
			result = sb.toString();
			sb.setLength(0);
			timeDelay = 1;
			System.out.println("--------------------");
			System.out.println("step: 2");
			System.out.println("temperature = " + temperature);
			System.out.println("timeDelay = " + timeDelay);
			if (temperature != 0) {
				System.out.println("--------------------");
				logger.info("step: 2 -> 3");
				step = 3;
				System.out.println("temperature = " + temperature);
			} else {
				count++;
				System.out.println(count);
				if (count == 30) {
					int size = vo.getId();
					System.out.println(size);
					service.deleteOne(size);
					count = 0;
					step = 0;
					temperature = 0;
				}
			}
			break;
		case 3:

			if (temperature < 37.5) {
				sb.append("<h3>").append(name).append("님의 체온은 ").append(temperature).append("도 입니다.</h3><br>");
				sb.append("<h2><b>정상 체온</b>입니다.<br></h2>");
				sb.append("<h2 class=\"green2\">통과!!</h2>");
				result = sb.toString();
				sb.setLength(0);
				timeDelay = 10;
				System.out.println("--------------------");
				logger.info("step: 3 -> 4");
				System.out.println("tempeprature = " + temperature);
				System.out.println("timeDelay = " + timeDelay);
				step = 4;
			} else if (temperature >= 37.5) {
				sb.append("<h3>").append(name).append("님의 체온은 ").append(temperature).append("도 입니다.</h3><br>");
				sb.append(" <h2 class=\"red\">통과할 수 없습니다.</h2> <h3>가까운 선별 진료소를 방문해주시기 바랍니다!</h3>");
				result = sb.toString();
				sb.setLength(0);
				timeDelay = 10;
				System.out.println("--------------------");
				logger.info("step: 3 -> 4");
				System.out.println("tempeprature = " + temperature);
				System.out.println("timeDelay = " + timeDelay);
				step = 4;
			}
			break;
		case 4:
			System.out.println("--------------------");
			logger.info("step: 4");
			step = 0;
			logger.info("step: 4 -> 0");
			System.out.println("tempeprature = " + temperature);
			System.out.println("timeDelay = " + timeDelay);
			break;
		}

		model.addAttribute("result", result);
		model.addAttribute("timeDelay", timeDelay);

		return "home";
	}

	// 모바일 뷰
	@RequestMapping(value = "join_result.do", method = RequestMethod.GET)
	public String insert(HttpServletRequest request, Model model) throws Exception {
		// DB에 insert하기전에 개수를 셈
		// 이름과 아이디만 받아옴
		name = request.getParameter("name");
		school_id = request.getParameter("school_id");
		temperature = Float.parseFloat(request.getParameter("temperature"));
		// 날짜는 자바 메소드로 받아옴
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		Calendar now = Calendar.getInstance();
		String date_time = simpleDateFormat.format(now.getTime());

		// float temperature = Float.parseFloat((request.getParameter("temperature")));
		// 파이썬에서 온도 받아오게되면 이부분 지우기
		// float temperature = (float) 0.00;

		List<MemberVO> allList = service.select();
		int id = allList.size() + 1;

		MemberVO vo = new MemberVO();

		// 이 다음 번호로 id설정
		vo.setId(id);
		vo.setName(name);
		vo.setSchool_id(school_id);
		vo.setDate_time(date_time);
		vo.setTemperature(temperature);

		// 출력창에 학생 아이디만 보낸다.
		model.addAttribute("school_id", school_id);
		model.addAttribute("name", name);
		// sId=Integer.parseInt(school_id);

		// insert가 됐으면 insertResult로 jsp실행하게하고
		int result = service.insert(vo);
		if (result == 1) {
			return "InsertResult";
		} else {
			return "home";
		}
	}

	// 모바일 뷰
	@RequestMapping(value = "openResult", method = RequestMethod.GET)
	public String openResult(HttpServletRequest request, Model model) throws Exception {
		float number = service.selectTemperature();
		System.out.println(number);
		if (number < 30) {
			model.addAttribute("school_id", school_id);
			return "InsertResult";
		} else if (number > 37.5) {
			model.addAttribute("school_id", school_id);
			model.addAttribute("temperature", number);
			return "OpenResult_close";
		} else {
			model.addAttribute("school_id", school_id);
			model.addAttribute("temperature", number);
			return "OpenResult_open";
		}
	}

}
