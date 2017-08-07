package DB;

public class BusInfo {
	
	String stationName; //정류장이름
	String arrmsg1; //첫번째 버스 도착 남은시간
	//String arrmsg2; //두번째 버스 도착 남은시간
	String stationOrd; //정류소 순번
	String nextord1; //첫번째 도착 버스의 현재 기준 다음 도착 정류장 순번
	//String makeTm; //갱신시간
	//String lastTm; //막차시간
	//String firstTm; //첫차시간
	//String nextBus; //막차운행여부(N - 막차아님, Y - 막차임)
	//String arrBus1Number; //첫번째 도착버스 번호
	//String arrBus2Number; //첫번째 도착버스 번호
	
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getArrmsg1() {
		return arrmsg1;
	}
	public void setArrmsg1(String arrmsg1) {
		this.arrmsg1 = arrmsg1;
	}
	/*public String getArrmsg2() {
		return arrmsg2;
	}
	public void setArrmsg2(String arrmsg2) {
		this.arrmsg2 = arrmsg2;
	}
	public String getMakeTm() {
		return makeTm;
	}
	public void setMakeTm(String makeTm) {
		this.makeTm = makeTm;
	}
	public String getLastTm() {
		return lastTm;
	}
	public void setLastTm(String lastTm) {
		this.lastTm = lastTm;
	}
	public String getFirstTm() {
		return firstTm;
	}
	public void setFirstTm(String firstTm) {
		this.firstTm = firstTm;
	}*/
	public String getStationOrd() {
		return stationOrd;
	}
	public void setStationOrd(String stationOrd) {
		this.stationOrd = stationOrd;
	}
	/*
	public String getNextBus() {
		return nextBus;
	}
	public void setNextBus(String nextBus) {
		this.nextBus = nextBus;
	}
	public String getArrBus1Number() {
		return arrBus1Number;
	}
	public void setArrBus1Number(String arrBus1Number) {
		this.arrBus1Number = arrBus1Number;
	}
	public String getArrBus2Number() {
		return arrBus2Number;
	}
	public void setArrBus2Number(String arrBus2Number) {
		this.arrBus2Number = arrBus2Number;
	}*/
	
	public String getNextord1() {
		return nextord1;
	}
	public void setNextord1(String nextord1) {
		this.nextord1 = nextord1;
	}

}

