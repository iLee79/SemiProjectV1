package semi.spring.mvc.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import semi.spring.mvc.vo.MemberVO;
import semi.spring.mvc.vo.Zipcode;

@Repository("mdao")
public class MemberDAOImpl implements MemberDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleJdbcInsert;
	private NamedParameterJdbcTemplate jdbcNamedTemplate;
	
	//private RowMapper<MemberVO> memberMapper = BeanPropertyRowMapper.newInstance(MemberVO.class); // RowMapper 가져와서 쓸때
	private RowMapper<Zipcode> zipcodeMapper = BeanPropertyRowMapper.newInstance(Zipcode.class);
	
	
	public MemberDAOImpl(DataSource dataSource) {
		simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("member").usingColumns("userid","passwd","name","email");
		
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
	public MemberVO selectOneMember(String userid) {
		String sql = "select userid,name,email,regdate from member where userid=?";
		
		Object[] param = {userid};
		
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
		return jdbcTemplate.queryForObject(sql, param, memberMapper); // RowMapper 직접 구현시
	}

	@Override
	public int selectOneMember(MemberVO m) {
		String sql = "select count(mno) cnt from member where userid=? and passwd=?";
		
		Object[] params = {m.getUserid(), m.getPasswd()};		
		
		return jdbcTemplate.queryForObject(sql, params, Integer.class);
	}

	@Override
	public int selectCountUserid(String uid) {
		String sql = "select count(mno) cnt from member where userid=?";
		
		Object[] param = new Object[] {uid};

		return jdbcTemplate.queryForObject(sql, param, Integer.class);
	}

	@Override
	public List<Zipcode> selectZipcode(String dong) {
		String sql = "select * from zipcode_2013 where dong like :dong";
		
		Map<String, Object> param = new HashMap<>();
		param.put("dong", dong);		
		
		return jdbcNamedTemplate.query(sql, param, zipcodeMapper);
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


