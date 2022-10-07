package semi.spring.mvc.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import semi.spring.mvc.dao.BoardDAO;
import semi.spring.mvc.vo.BoardVO;

@Service("bsrv")
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO bdao;
	
	@Override
	public boolean newBoard(BoardVO bvo) {
		boolean isInsert = false;
		
		if(bdao.insertBoard(bvo) > 0) isInsert = true;		
		
		return isInsert;
	}

	@Override
	public List<BoardVO> readBoard(String fkey, String fval, int snum) {
		
		return bdao.selectBoard(fkey, fval, snum);
	}

	@Override
	public BoardVO readOneBoard(String bno) {
		
		return bdao.selectOneBoard(bno);
	}

	@Override
	public int readCountBoard(String fkey, String fval) {
		
		return bdao.selectCountBoard(fkey, fval);
	}

	@Override
	public boolean removeBoard(String bno) {
		boolean isDelete = false;
		
		if (bdao.deleteBoard(bno) > 0) isDelete = true;

		return isDelete;
	}

	@Override
	public boolean modifyBoard(BoardVO bvo) {
		boolean isModify = false;
		
		if (bdao.updateBoard(bvo) > 0) isModify = true;
		
		return isModify;
	}



}
