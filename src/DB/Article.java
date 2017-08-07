package DB;

//게시물의 정보를 임시로 저장할 클래스
public class Article {
	int record; //기본키
	String Beacon_no; // 비콘번호
	String Path; // 파일 경로
	String URL; // 인터넷 홈페이지 경로(www.~.com)
	String Filename; //파일이름
	String Type;
	String Writer;
	String Dates;
	public int getRecord() {
		return record;
	}

	public void setRecord(int record) {
		this.record = record;
	}
	
	public String getBeacon_no() {
		return Beacon_no;
	}

	public void setBeacon_no(String Beacon_no) {
		this.Beacon_no = Beacon_no;
	}

	public String getPath() {
		return Path;
	}

	public void setPath(String Path) {
		this.Path = Path;
	}
	
	public String getURL() {
		return URL;
	}

	public void setURL(String URL) {
		this.URL = URL;
	}
	public String getFilename() {
		return Filename;
	}

	public void setFilename(String Filename) {
		this.Filename = Filename;
	}
	
	public String getType() {
		return Type;
	}

	public void setType(String Type) {
		this.Type = Type;
	}
	public String getWriter() {
		return Writer;
	}

	public void setWriter(String Writer) {
		this.Writer = Writer;
	}
	public String getDates() {
		return Dates;
	}

	public void setDates(String Dates) {
		this.Dates = Dates;
	}
}
