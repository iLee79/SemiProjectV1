package semi.spring.mvc.dao;

import java.util.List;

import semi.spring.mvc.vo.BoardVO;

public interface BoardDAO {

	int insertBoard(BoardVO bvo);

	List<BoardVO> selectBoard(String fkey, String fval, int snum);

	BoardVO selectOneBoard(String bno);

	int selectCountBoard(String fkey, String fval);

	int deleteBoard(String bno);

}
