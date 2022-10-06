package semi.spring.mvc.dao;

import java.util.List;

import semi.spring.mvc.vo.MemberVO;
import semi.spring.mvc.vo.Zipcode;

public interface MemberDAO {

	int insertMember(MemberVO mvo);

	MemberVO selectOneMember(String userid);

	int selectOneMember(MemberVO m);

	int selectCountUserid(String uid);

	List<Zipcode> selectZipcode(String dong);


}
