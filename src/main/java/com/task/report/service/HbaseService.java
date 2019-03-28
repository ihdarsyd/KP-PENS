package com.task.report.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;




@Service("twService")
public class HbaseService{
	private final static String HMASTER = "namenode004.cluster02.bt";
	private final static String ZOOKEEPER = "master001.cluster02.bt,namenode004.cluster02.bt,namenode005.cluster02.bt";
	Configuration configuration = HBaseConfiguration.create();{
		configuration.set("hbase.master", HMASTER);
		configuration.setInt("timeout", 5000);
		configuration.set("hbase.zookeeper.quorum", ZOOKEEPER);
		configuration.set("zookeeper.znode.parent", "/hbase-unsecure2");		
	};

	public void deleteTable(String tableName) {
		try (Connection connection = ConnectionFactory.createConnection(configuration);
		     Admin admin = connection.getAdmin()) {
			TableName table = TableName.valueOf(tableName);
			if (!admin.tableExists(TableName.valueOf(tableName))) {
				return;
			}
			admin.disableTable(table);
			admin.deleteTable(table);
			System.out.println("delete table " + tableName + " successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void putRowValue(String tableName, String rowKey, String familyColumn, String columnName, String value) {
		try (Connection connection = ConnectionFactory.createConnection(configuration);
		     Table table = connection.getTable(TableName.valueOf(tableName))) {
			Put put = new Put(Bytes.toBytes(rowKey));
			put.addColumn(Bytes.toBytes(familyColumn), Bytes.toBytes(columnName), Bytes.toBytes(value));
			table.put(put);
			System.out.println("Update table success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void putRowValueBatch(String tableName, String rowKey, String familyColumn, Map<String, String> columnValues) {
		try (Connection connection = ConnectionFactory.createConnection(configuration);
		     Table table = connection.getTable(TableName.valueOf(tableName))) {
			Put put = new Put(Bytes.toBytes(rowKey));
			for (Map.Entry<String, String> entry : columnValues.entrySet()) {
				put.addColumn(Bytes.toBytes(familyColumn), Bytes.toBytes(entry.getKey()), Bytes.toBytes(entry.getValue()));
			}
			table.put(put);
			System.out.println("Update table success");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Cell> scanRegexRowKey(String tableName, String regexKey) {
		try (Connection connection = ConnectionFactory.createConnection(configuration);
		     Table table = connection.getTable(TableName.valueOf(tableName))) {
			Scan scan = new Scan();
			Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator("TW_"+regexKey));
			scan.setFilter(filter);
			ResultScanner rs = table.getScanner(scan);
			for (Result r : rs) {
				return r.listCells();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Cell> scanRegexRowKey(String tableName, Map<String,String> regexKey) {
		try (Connection connection = ConnectionFactory.createConnection(configuration);
		     Table table = connection.getTable(TableName.valueOf(tableName))) {
			Scan scan = new Scan();
			Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL, new RegexStringComparator("TW_"+regexKey));
			scan.setFilter(filter);
			ResultScanner rs = table.getScanner(scan);
			for (Result r : rs) {
				return r.listCells();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteAllColumn(String tableName, String rowKey) {
		try (Connection connection = ConnectionFactory.createConnection(configuration);
		     Table table = connection.getTable(TableName.valueOf(tableName))) {
			Delete delAllColumn = new Delete(Bytes.toBytes(rowKey));
			table.delete(delAllColumn);
			System.out.println("Delete AllColumn Success");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteColumn(String tableName, String rowKey, String familyName, String columnName) {
		try (Connection connection = ConnectionFactory.createConnection(configuration);
		     Table table = connection.getTable(TableName.valueOf(tableName))) {
			Delete delColumn = new Delete(Bytes.toBytes(rowKey));
			delColumn.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName));
			table.delete(delColumn);
			System.out.println("Delete Column Success");
		} catch (IOException e) {
			e.printStackTrace();
		}
}
     
}
