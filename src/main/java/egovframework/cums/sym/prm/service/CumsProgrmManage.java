package egovframework.cums.sym.prm.service;

public class CumsProgrmManage {
	/**
	 * 프로그램설명
	 */
	private String progrmDc;
	/**
	 * 프로그램파일명
	 */
	private String progrmFileNm;
	/**
	 * 프로그램한글명
	 */
	private String progrmKoreanNm;
	/**
	 * 프로그램저장경로
	 */
	private String progrmStrePath;
	/**
	 * URL
	 */
	private String url;

	public String getProgrmDc() {
		return progrmDc;
	}
	public void setProgrmDc(String progrmDc) {
		this.progrmDc = progrmDc;
	}
	public String getProgrmFileNm() {
		return progrmFileNm;
	}
	public void setProgrmFileNm(String progrmFileNm) {
		this.progrmFileNm = progrmFileNm;
	}
	public String getProgrmKoreanNm() {
		return progrmKoreanNm;
	}
	public void setProgrmKoreanNm(String progrmKoreanNm) {
		this.progrmKoreanNm = progrmKoreanNm;
	}
	public String getProgrmStrePath() {
		return progrmStrePath;
	}
	public void setProgrmStrePath(String progrmStrePath) {
		this.progrmStrePath = progrmStrePath;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String urlTemp) {
		url = urlTemp;
	}
}
