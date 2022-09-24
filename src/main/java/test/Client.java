package test;

import java.util.Scanner;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.kim.biz.member.MemberService;
import com.kim.biz.member.MemberVO;

public class Client {
	public static void main(String[] args) {

		AbstractApplicationContext factory=new GenericXmlApplicationContext("applicationContext.xml");
		Scanner sc=new Scanner(System.in);

		MemberService ms=(MemberService)factory.getBean("memberService"); // Lookup == �޸𸮿��� ��ü�� "ã��" ��û
		
		System.out.print("���̵� >> ");
		String mid=sc.next();
		System.out.print("��й�ȣ >> ");
		String mpw=sc.next();
		MemberVO mvo=new MemberVO();
		mvo.setMid(mid);
		mvo.setMpw(mpw);
		mvo=ms.selectOneMember(mvo); // �ٽɷ����� �����Ϸ����Ѵ�!
		if(mvo==null) {
			System.out.println("�α��� ����...");
		}
		else {
			System.out.println(mvo.getName()+"��, �ȳ��ϼ���! :D");
		}
		
		factory.close();
		

	}
}
