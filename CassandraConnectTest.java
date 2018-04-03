import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;


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
		
	}

}
