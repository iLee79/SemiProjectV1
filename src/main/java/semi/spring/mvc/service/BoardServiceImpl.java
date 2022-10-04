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
	public List<BoardVO> readBoard(int snum) {
		
		return bdao.selectBoard(snum);
	}

	@Override
	public BoardVO readOneBoard(String bno) {
		
		return bdao.selectOneBoard(bno);
	}



}
