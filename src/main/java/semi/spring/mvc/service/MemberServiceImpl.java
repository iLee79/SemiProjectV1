package semi.spring.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import semi.spring.mvc.dao.MemberDAO;
import semi.spring.mvc.vo.MemberVO;

@Service("msrv")
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO mdao;
	
	@Override
	public boolean newMember(MemberVO mvo) {
		boolean isInsert = false;
		
		// 회원가입이 성공시 true 리턴
		if (mdao.insertMember(mvo) > 0) isInsert = true; 
		
		return isInsert;
	}

	@Override
	public MemberVO readOneMember(String userid) {
				
		return mdao.selectOneMember(userid);
	}

	@Override
	public boolean checkLogin(MemberVO m) {
		boolean isLogin = false;
		
		// 회원이 존재한다면 
		if (mdao.selectOneMember(m) > 0) isLogin = true;		
		
		return isLogin;
	}
	

}
