package semi.spring.mvc.dao;

import java.util.Collections;

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

@Repository("mdao")
public class MemberDAOImpl implements MemberDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert simpleJdbcInsert;
	private NamedParameterJdbcTemplate jdbcNamedTemplate;
	
	private RowMapper<MemberVO> memberMapper = BeanPropertyRowMapper.newInstance(MemberVO.class);
	
	public MemberDAOImpl(DataSource dataSource) {
		simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("member").usingColumns("userid","passwd","name","email");
		
		jdbcNamedTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Override
	public int insertMember(MemberVO mvo) {
//		System.out.println("bdaoImpl:"+bvo);
		
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
		
		return jdbcNamedTemplate.queryForObject(sql, Collections.emptyMap(), memberMapper);
	}

}
