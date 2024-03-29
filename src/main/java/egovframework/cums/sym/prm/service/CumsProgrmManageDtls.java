package egovframework.cums.sym.prm.service;

public class CumsProgrmManageDtls {

	/**
	 * 변경요청내용
	 */
	private String changerqesterCn;
	/**
	 * 처리자ID
	 */
	private String opetrId;
	/**
	 * 처리일자
	 */
	private String processDe;
	public String getChangerqesterCn() {
		return changerqesterCn;
	}
	public void setChangerqesterCn(String changerqesterCn) {
		this.changerqesterCn = changerqesterCn;
	}
	public String getOpetrId() {
		return opetrId;
	}
	public void setOpetrId(String opetrId) {
		this.opetrId = opetrId;
	}
	public String getProcessDe() {
		return processDe;
	}
	public void setProcessDe(String processDe) {
		this.processDe = processDe;
	}
	public String getProcessSttus() {
		return processSttus;
	}
	public void setProcessSttus(String processSttus) {
		this.processSttus = processSttus;
	}
	public String getProgrmFileNm() {
		return progrmFileNm;
	}
	public void setProgrmFileNm(String progrmFileNm) {
		this.progrmFileNm = progrmFileNm;
	}
	public String getRqesterDe() {
		return rqesterDe;
	}
	public void setRqesterDe(String rqesterDe) {
		this.rqesterDe = rqesterDe;
	}
	public int getRqesterNo() {
		return rqesterNo;
	}
	public void setRqesterNo(int rqesterNo) {
		this.rqesterNo = rqesterNo;
	}
	public String getRqesterpersonId() {
		return rqesterpersonId;
	}
	public void setRqesterpersonId(String rqesterpersonId) {
		this.rqesterpersonId = rqesterpersonId;
	}
	public String getRqesterProcessCn() {
		return rqesterProcessCn;
	}
	public void setRqesterProcessCn(String rqesterProcessCn) {
		this.rqesterProcessCn = rqesterProcessCn;
	}
	public String getRqesterSj() {
		return rqesterSj;
	}
	public void setRqesterSj(String rqesterSj) {
		this.rqesterSj = rqesterSj;
	}
	/**
	 * 처리상태코드
	 */
	private String processSttus;
	/**
	 * 프로그램파일명
	 */
	private String progrmFileNm;
	/**
	 * 요청일자
	 */
	private String rqesterDe;
	/**
	 * 요청번호
	 */
	private int rqesterNo;
	/**
	 * 요청자ID
	 */
	private String rqesterpersonId;
	/**
	 * 요청처리내용
	 */
	private String rqesterProcessCn;
	/**
	 * 요청제목
	 */
	private String rqesterSj;
}
