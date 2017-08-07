package DB;

@SuppressWarnings("deprecation")
public class PageshowDao  {
	int Page;

	public PageshowDao (int showPage) {
		this.Page = showPage ;
	}
	public int getshowPage(){
		return Page;
	}
	
}
