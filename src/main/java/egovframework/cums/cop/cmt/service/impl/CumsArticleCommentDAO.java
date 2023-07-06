package egovframework.cums.cop.cmt.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.cums.cop.cmt.service.CumsComment;
import egovframework.cums.cop.cmt.service.CumsCommentVO;

@Repository("CumsArticleCommentDAO")
public class CumsArticleCommentDAO extends EgovComAbstractDAO{

	public List<?> selectArticleCommentList(CumsCommentVO commentVO) {
		return list("ArticleComment.selectArticleCommentList", commentVO);
	}

	public int selectArticleCommentListCnt(CumsCommentVO commentVO) {
		return (Integer)selectOne("ArticleComment.selectArticleCommentListCnt", commentVO);
	}

	public void insertArticleComment(CumsComment comment) {
		insert("ArticleComment.insertArticleComment", comment);
	}

	public void deleteArticleComment(CumsCommentVO commentVO) {
		update("ArticleComment.deleteArticleComment", commentVO);
	}

	public CumsCommentVO selectArticleCommentDetail(CumsCommentVO commentVO) {
		return (CumsCommentVO) selectOne("ArticleComment.selectArticleCommentDetail", commentVO);
	}

	public void updateArticleComment(CumsComment comment) {
		update("ArticleComment.updateArticleComment", comment);
	}

}
