package com.kim.biz.member;

import java.util.List;

public interface MemberService {
	public boolean insertMember(MemberVO vo);
	void deleteMember(MemberVO vo);
	void updateMember(MemberVO vo);
	public MemberVO selectOneMember(MemberVO vo);
	List<MemberVO> selectAllMember(MemberVO vo);
}
