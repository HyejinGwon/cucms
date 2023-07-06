package egovframework.cums.cop.cmt.service;

import java.util.Map;

import org.egovframe.rte.fdl.cmmn.exception.FdlException;

public interface CumsArticleCommentService {

    public boolean canUseComment(String bbsId) throws Exception;

    Map<String, Object> selectArticleCommentList(CumsCommentVO commentVO);

	void insertArticleComment(CumsComment comment) throws FdlException;

	void deleteArticleComment(CumsCommentVO commentVO);

	CumsCommentVO selectArticleCommentDetail(CumsCommentVO commentVO);

	void updateArticleComment(CumsComment comment);

}
