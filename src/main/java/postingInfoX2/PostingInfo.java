package postingInfoX2;

class PostingInfo {
	String title;
	String id;
	String href;
	String company;
	String location;
	String dateStr;
	String summary;

	public PostingInfo(String title, String id, String href, String company, String location, String dateStr,
			String summary) {
		this.title = title;
		this.id = id;
		this.href = href;
		this.company = company;
		this.location = location;
		this.dateStr = dateStr;
		this.summary = summary;
	}

	public String getTitle() {
		return title;
	}

	public String getId() {
		return id;
	}

	public String getHref() {
		return href;
	}

	public String getCompany() {
		return company;
	}

	public String getLocation() {
		return location;
	}

	public String getDateStr() {
		return dateStr;
	}

	public String getSummary() {
		return summary;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@Override
	public String toString() {
		return "PostingInfo [title=" + title + ", id=" + id + ", href=" + href + ", company=" + company + ", location="
				+ location + ", dateStr=" + dateStr + ", summary=" + summary + "]";
	}
}
