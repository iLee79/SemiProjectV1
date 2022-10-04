package semi.spring.mvc.dao;

import semi.spring.mvc.vo.MemberVO;

public interface MemberDAO {

	int insertMember(MemberVO mvo);

	MemberVO selectOneMember(String userid);

	int selectOneMember(MemberVO m);


}
