package semi.spring.mvc.service;

import semi.spring.mvc.vo.MemberVO;

public interface MemberService {

	boolean newMember(MemberVO mvo);

	MemberVO readOneMember(String userid);

	boolean checkLogin(MemberVO mvo);

}
