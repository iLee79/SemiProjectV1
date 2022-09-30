package semi.spring.mvc.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import semi.spring.mvc.vo.BoardVO;

@Repository("bdao")
public class BoardDAOImpl implements BoardDAO {

	//@Autowired 
	private JdbcTemplate jdbdTemplate;
	private SimpleJdbcInsert simpleInsert;
			
	public BoardDAOImpl(DataSource dataSource) {
		simpleInsert = new SimpleJdbcInsert(dataSource).withTableName("board").usingColumns("title","userid","contents");
	}

	@Override
	public int insertBoard(BoardVO bvo) {
		System.out.println("bdaoImpl:"+bvo);
		
		SqlParameterSource params = new BeanPropertySqlParameterSource(bvo);
		
		return simpleInsert.execute(params);
	}
	
	/*
	@Override
	public int insertBoard(BoardVO bvo) {
		String sql = "insert into board(title,userid,contents) values(?,?,?)";
		//System.out.println("boardDAOImple : "+bvo);
		Object params = new Object[] {
				bvo.getTitle(), bvo.getUserid(), bvo.getContents()
		};
		
		return jdbdTemplate.update(sql, params);
	}
	*/
}
