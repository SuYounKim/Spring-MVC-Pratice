package com.kim.biz.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.kim.biz.board.BoardVO;
import com.kim.biz.board.impl.BoardDAO;
import com.kim.biz.member.MemberVO;
import com.kim.biz.member.impl.MemberDAO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
@SessionAttributes("data")//"data"라는 이름의 데이터가 Model 객체에 세팅이 된다면, 그것을 sessio에 기억시키게싿. 
//개발자가 디테일한 시점을 지정할 수 없기때문에 디테일하게 사용시점마다 꺼내쓸 수 있는 session.setattibute사용 
// 다른곳에서 data로 선언한게 있다면 매번 바뀌게 된다 
public class BoardController {
	
	@ModelAttribute("scMap")
	public Map<String,String> searchConditionMap(){
		Map<String,String> scMap =new HashMap<String,String>();
		scMap.put("제목", "TITLE");
		scMap.put("작성자", "WRITER");
		return scMap;
	}

	@RequestMapping("/main.do")//command 맴버변수로 serch에 관려된게 없어서 검색조건, 검색어 자동 맵핑이 안됨 
	//-> 해결하기 위해서 자바에만 사용하기 위해 vo에 멤버변수로 추가한다. 
	//-> 다른 해결방법 : requestparam  
	public String main(@RequestParam(value="searchCondition",defaultValue="",required=false)String searchCondition,@RequestParam(value="searchContent",defaultValue="",required=false)String searchContent, BoardVO bVO, BoardDAO bDAO,MemberVO mVO,MemberDAO mDAO, Model model) {
		if(searchCondition.equals("TITLE")) {
			bVO.setTitle(searchContent);
		}else if(searchCondition.equals("WRITER")) {
			bVO.setWriter(searchContent);
		}
		
		mVO = mDAO.selectOneMember(mVO);
		System.out.println("검색조건: "+searchCondition);
		System.out.println("검색어: "+searchContent);
		List<BoardVO> datas=bDAO.selectAllBoard(bVO); 
		model.addAttribute("datas", datas);
		/* model.addAttribute("member", mVO); */
		System.out.println("main로그 :" + mVO);
		return "main.jsp";
	}
	
	@RequestMapping("/board.do")
	public String board(BoardVO bVO, BoardDAO bDAO, Model model) {
		/* bVO.setBid(Integer.parseInt(request.getParameter("bid"))); */
		bVO=bDAO.selectOneBoard(bVO);
		
		model.addAttribute("data", bVO);
		System.out.println("board.do 테스트용" + bVO);
	
		return "board.jsp";
	}
		
	@RequestMapping("/updateBoard.do")
	public String updateBoard(@ModelAttribute("data")BoardVO bVO, BoardDAO bDAO) {
		
		bDAO.updateBoard(bVO);
		System.out.println("updataboard.do로그"+bVO);
		return "redirect:main.do";
	}
	
	@RequestMapping("/insertBoard.do")
	public String insertBoard(BoardVO bVO,BoardDAO bDAO) {
		System.out.println("값이 들어가니" + bVO);
		bDAO.insertBoard(bVO);
		return "redirect:main.do";
	}
	
	@RequestMapping("/deleteBoard.do")
	public String deleteBoard(BoardVO bVO,BoardDAO bDAO) {
		bDAO.deleteBoard(bVO);
		return "main.do";
	}

}
