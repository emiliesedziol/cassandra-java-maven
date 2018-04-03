
import com.datastax.driver.core.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CassandraConnect {
	public Session connect() {
		String serverIP = "127.0.0.1";
		String keyspace = "testing";
		
		Cluster cluster = Cluster.builder()
				.addContactPoints(serverIP)
				.build();
		
		return cluster.connect(keyspace);
	}
	
	public List<TimeSeriesRecord> selectAllFromTimeSeries(Session session) {
		List<TimeSeriesRecord> result = new LinkedList();
		String cqlStatement = "SELECT * FROM timeseries";
		for (Row row : session.execute(cqlStatement)) {
			String event_type = row.getString("event_type");
			String content = row.getString("event");
			Date insertion_time = row.getTimestamp("insertion_time");
			result.add(new TimeSeriesRecord(event_type, insertion_time, content));
		};
		
		return result;
	}
	
	/*public void insertNewEvent(Session session, TimeSeriesRecord timeSeriesRecord) {
		
		PreparedStatement prepared = session.prepare(
				query:"insert into timeseries (event_type, event, insertion_time)" +
						" values(?, ?, ?");
		
		BoundStatement bound = prepared.bind(
				timeSeriesRecord.getId(),
				timeSeriesRecord.getContent(),
				timeSeriesRecord.getEventTime()
				);
		session.execute(bound);
	}*/

}
