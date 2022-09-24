package com.kim.biz.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.kim.biz.member.MemberVO;
import com.kim.biz.member.impl.MemberDAO;

@Controller
@SessionAttributes("member")//member라는 정보가 Model에 add되면 session에 저장해라!! 
public class MemberController {

	@RequestMapping(value="/login.do",method=RequestMethod.GET)
	public String index() {
		return "login.jsp";
	}
	@RequestMapping(value="/login.do",method=RequestMethod.POST)
	public String selectOneMember(MemberVO mVO,MemberDAO mDAO, HttpSession session, Model model) {
		mVO=mDAO.selectOneMember(mVO);
		
		if(mVO==null) {
			return "redirect:login.jsp";
		}
		else {
			model.addAttribute("member", mVO);//model에 "member"가 add!! -> session에 저장
			return "main.do";
		}
	}

	@RequestMapping("/logout.do")
	public String logout(HttpSession session)  {
		session.invalidate();
		return "redirect:login.do"; //VR 디폴트 값이 forward
	}

	@RequestMapping("/mypage.do")
	public String mypageselectOne(@ModelAttribute("member")MemberVO mVO,MemberDAO mDAO,Model model) {
		/* mVO =(MemberVO)session.getAttribute("userId"); */
		mVO = mDAO.selectOneMember(mVO);
		
		 model.addAttribute("member", mVO); 
		return "mypage.jsp";
	}
	
	@RequestMapping("/update.do")
	public  String mypageupdate(@ModelAttribute("member")MemberVO mVO,MemberDAO mDAO) {
		//원래 존재하던 member를 set해주고, 변경되는 정보는 그 뒤에 set! 
		System.out.println("update로그 :" + mVO);
		 mDAO.updateMember(mVO);
		//만약 변경사항이 있다면 새로운 정보로 덮어씌워줌! 
		
		return "main.do";
	}

	@RequestMapping("/signin.do")
	public String signin(MemberVO mVO,MemberDAO mDAO) {
		
		 mDAO.insertMember(mVO);
		return "login.jsp";
	}
	
	@RequestMapping("/delete.do")
	public String memberdelete(@ModelAttribute("member")MemberVO mVO, MemberDAO mDAO) {
		/* mVO =(MemberVO)session.getAttribute("userId"); */
		mDAO.deleteMember(mVO);
		/* session.invalidate(); */
		return "login.jsp";
	}
	
	
	
}
