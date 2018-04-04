import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.datastax.driver.core.Session;

public class CassandraConnectTest {
	
	@Test
	public void shouldConnectToLocalCassandraAndFetchAllTimeSeriesData() {
		// given
		CassandraConnect cassandraConnect = new CassandraConnect();
		Session session = cassandraConnect.connect();
		
		//when
		List<TimeSeriesRecord> result
			= cassandraConnect.selectAllFromTimeSeries(session);
		System.out.println(result);
		
		//then
		assertThat(result.size()).isGreaterThan(0);
		
		/* result returns the following
		 * [TimeSeriesRecord [id=buy, eventTime=Fri Mar 30 13:21:40 EDT 2018, content=third_event], 

		 TimeSeriesRecord [id=buy, eventTime=Fri Mar 30 13:21:32 EDT 2018, content=second_event], 

		 TimeSeriesRecord [id=buy, eventTime=Fri Mar 30 12:45:39 EDT 2018, content=first_event], 

		 TimeSeriesRecord [id=buy, eventTime=Wed Mar 28 13:39:25 EDT 2018, content=second_event], 

		 TimeSeriesRecord [id=buy, eventTime=Wed Mar 28 13:38:32 EDT 2018, content=first_event]]*/
		
	}
	
	@Test
	public void shouldInsertNewEventUsingPreparedStatement() {
		//given
		CassandraConnect cassandraConnect = new CassandraConnect();
		Session session = cassandraConnect.connect();
		
		TimeSeriesRecord timeSeriesRecord
			= new TimeSeriesRecord("id", new Date(), "checking");
		System.out.println(timeSeriesRecord);
		//when
		
		cassandraConnect.insertNewEvent(session, timeSeriesRecord);
		
		//then
		List<TimeSeriesRecord> timeSeriesRecords = cassandraConnect.selectAllFromTimeSeries(session);
		
		assertThat(hasInsertedTimeSeriesRecord(timeSeriesRecords, timeSeriesRecord));
		
		/*
		 * test passed
		 * TimeSeriesRecord [id=id, eventTime=Wed Apr 04 12:08:01 EDT 2018, content=using maven]
		 */
	}
	
	private boolean hasInsertedTimeSeriesRecord(List<TimeSeriesRecord> timeSeriesRecords,
												TimeSeriesRecord expected) {
		return timeSeriesRecords
				.stream()
				.anyMatch(
						v -> v.getContent().equals(expected.getContent()) &&
						v.getId().equals(expected.getId())
						);
	}

}
