package filter;

import java.util.NavigableMap;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;


public class Tools {
	public static String resultMapToString(Result result) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("Result with rowKey " + Bytes.toString(result.getRow()) + " : ");
		NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> familyMap = result.getMap();

		for (byte[] family : familyMap.keySet()) {
			strBuilder.append(" Family - " + Bytes.toString(family));
			NavigableMap<byte[], NavigableMap<Long, byte[]>> qualMap = familyMap.get(family);

			for (byte[] qual : qualMap.keySet()) {
				String quals = Bytes.toString(qual);
				strBuilder.append(" : Qualifier - " + quals);
				NavigableMap<Long, byte[]> valueTsMap = qualMap.get(qual);

				for (Long tstamp : valueTsMap.keySet()) {
					byte[] valueBytes = valueTsMap.get(tstamp);

					if (quals.equals("vol")) {
						strBuilder.append(" : Value(long): " + Bytes.toLong(valueBytes));
					}

					if (quals.equals("price")) {
						strBuilder.append(" : Value(long): " + Bytes.toFloat(valueBytes));
					}
				}
			}
		}

		return strBuilder.toString();
	}
}
