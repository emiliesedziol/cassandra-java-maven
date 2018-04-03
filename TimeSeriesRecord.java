import java.util.Date;

public class TimeSeriesRecord {

	private final String id;
	private final Date eventTime;
	private final String content;
	
	public TimeSeriesRecord(String id, Date eventTime, String content) {
		this.id = id;
		this.eventTime = eventTime;
		this.content = content;
	}
	
	public String getId() { return id; }
	
	public Date getEventTime() { return eventTime; }
	
	public String getContent() { return content; }

	@Override
	public String toString() {
		return "TimeSeriesRecord [id=" + id 
				+ ", eventTime=" + eventTime 
				+ ", content=" + content + "]";
	}
	
	
}
