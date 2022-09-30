package semi.spring.mvc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import semi.spring.mvc.vo.MemberVO;

@Repository("mdao")
public class MemberDAOImpl implements MemberDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleJdbcInsert;
	private NamedParameterJdbcTemplate jdbcNamedTemplate;
	
	//private RowMapper<MemberVO> memberMapper = BeanPropertyRowMapper.newInstance(MemberVO.class); // RowMapper 가져와서 쓸때
	
	
	public MemberDAOImpl(DataSource dataSource) {
		simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("member").usingColumns("userid","name","email","regdate");
		
		jdbcNamedTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public int insertMember(MemberVO mvo) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(mvo);
		
		return simpleJdbcInsert.execute(params);
	}
	
	/*
	@Override
	public int insertMember(MemberVO mvo) {
		String sql = "insert into member(userid,passwd,name,email) values(?,?,?,?)";
		
		Object[] params = new Object[] {
				mvo.getUserid(), mvo.getPasswd(), mvo.getName(), mvo.getEmail()
		};
		
		return jdbcTemplate.update(sql, params);
	}
	*/

	@Override
	public MemberVO selectOneMember() {
		String sql = "select userid,name,email,regdate from member where mno=1";
		
		//RowMapper<MemberVO> memberMapper = new MemberRowMapper(); // RowMapper 직접 구현시 
		
		RowMapper<MemberVO> memberMapper = (rs,num) -> { // RowMapper 직접구현 : 람다식 사용시
			MemberVO m = new MemberVO();		
			
			m.setUserid(rs.getString("userid"));
			m.setName(rs.getString("name"));
			m.setEmail(rs.getString("email"));
			m.setRegdate(rs.getString("regdate"));
			
			return m;
		};
		
		//return jdbcNamedTemplate.queryForObject(sql, Collections.emptyMap(), memberMapper); // RowMapper 가져와서 쓸때				
		return jdbcTemplate.queryForObject(sql, null, memberMapper); // RowMapper 직접 구현시
	}

	
	/*
	// 콜백 메서드 정의 : mapRow
	// RowMapper 직접 구현시
	private class MemberRowMapper implements RowMapper<MemberVO>{
		
		@Override
		public MemberVO mapRow(ResultSet rs, int num) throws SQLException {
			MemberVO m = new MemberVO();
			
			m.setUserid(rs.getString("userid"));
			m.setName(rs.getString("name"));
			m.setEmail(rs.getString("email"));
			m.setRegdate(rs.getString("regdate"));
			
			return m;
		}
		
	}
	*/
}	


