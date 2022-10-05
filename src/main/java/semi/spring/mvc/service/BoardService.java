package semi.spring.mvc.service;

import java.util.List;

import semi.spring.mvc.vo.BoardVO;

public interface BoardService {

	boolean newBoard(BoardVO bvo);

	List<BoardVO> readBoard(String fkey, String fval, int snum);

	BoardVO readOneBoard(String bno);

	int readCountBoard(String fkey, String fval);

}
