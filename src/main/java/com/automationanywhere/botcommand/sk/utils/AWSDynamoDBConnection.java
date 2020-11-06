package com.automationanywhere.botcommand.sk.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.KeyAttribute;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableResult;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.BooleanValue;
import com.automationanywhere.botcommand.data.impl.NumberValue;
import com.automationanywhere.botcommand.data.impl.StringValue;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

public class AWSDynamoDBConnection {

	
	private AmazonDynamoDB client;
	private String accesskey;
	private String secretkey;
	
	public  AWSDynamoDBConnection(String accessKey,String secretKey,String region) {
		this.accesskey = accessKey;
		this.secretkey = secretKey;
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(this.accesskey,this.secretkey);
		this.client = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
				.withRegion(region)
				.build();
	}
	

	
	public void close() {
		client.shutdown();
	}
	
	
	public List<String>  getTables() {

		DynamoDB dynamoDB = new DynamoDB(client);
		List<String> tableList = new ArrayList<String>();

		TableCollection<ListTablesResult> tables = dynamoDB.listTables();
		Iterator<Table> iterator = tables.iterator();

		while (iterator.hasNext()) {
		    Table table = iterator.next();
		    tableList.add(table.getTableName());
		}
		
		return tableList;
	}
	
	
	 public String deleteTable(String tableName) {

		 	DynamoDB dynamoDB = new DynamoDB(client);
		 	String result = "";
	        Table table = dynamoDB.getTable(tableName);
	        try {
	            DeleteTableResult deleteresult = table.delete();
	            table.waitForDelete();
	            result = "Success";
	        }
	        catch (Exception e) {
	        	result =e.getMessage();
	        }
	        
	        return result;
	    }
	 
	 
	 public String getTableStatus(String tableName) {

		 	DynamoDB dynamoDB = new DynamoDB(client);
		 	TableDescription tableDescription = dynamoDB.getTable(tableName).describe();
		 	return tableDescription.getTableStatus();
	 }
	
	 
	 
	 public List<Map<String,Value>>  getAllTableItems(String tablename) {

			List<Map<String,Value>> items = new ArrayList<Map<String,Value>>();
			ScanRequest scanRequest = new ScanRequest()
				    .withTableName(tablename);

			ScanResult result = client.scan(scanRequest);
			for (Map<String, AttributeValue> item : result.getItems()){
				Map<String,Value> itemmap = new HashMap<String,Value>();
				for (Entry<String, AttributeValue> entry : item.entrySet()) {
					itemmap.put(entry.getKey(),new StringValue(entry.getValue().toString()));
				}
				items.add(itemmap);
			}
			return items;
		}
	 
	 
	 public List<String>  querywithPrimaryKey(String tablename,String key, String query) {

			DynamoDB dynamoDB = new DynamoDB(client);
			List<String> items = new ArrayList<String>();
			Table table = dynamoDB.getTable(tablename);

			QuerySpec spec = new QuerySpec()
			    .withKeyConditionExpression(key+ " = :v_id")
			    .withValueMap(new ValueMap()
			    .withString(":v_id", query));

			ItemCollection<QueryOutcome> result = table.query(spec);

			Iterator<Item> iterator = result.iterator();
			Item item = null;
			while (iterator.hasNext()) {
			    item = iterator.next();
			    items.add(item.toJSONPretty());
			}
			return items;
		}
	 
	 
	 
	 
	 
	 public String create(String tablename,String partitionkey,String partitionkeytype, String sortkey, String sortkeytype, Long throughput) {

			DynamoDB dynamoDB = new DynamoDB(client);
			String result;
			
			List<KeySchemaElement> keyschemadef = new ArrayList<KeySchemaElement>();
			List<AttributeDefinition> attributedef = new ArrayList<AttributeDefinition>();
			keyschemadef.add(new KeySchemaElement(partitionkey, KeyType.HASH));
			attributedef.add(new AttributeDefinition().withAttributeName(partitionkey).withAttributeType(partitionkeytype));
			if (sortkey != null && sortkey != "") {
				keyschemadef.add(new KeySchemaElement(sortkey, KeyType.RANGE));	
				attributedef.add(new AttributeDefinition().withAttributeName(sortkey).withAttributeType(sortkeytype));
			}
			
	
				
			try {
				
				CreateTableRequest req;

	            Table table = dynamoDB.createTable(tablename,
	            				  keyschemadef,
	                			  attributedef,
	                			  new ProvisionedThroughput(throughput,throughput));
	            table.waitForActive();
	            result = "Success";


	        }
	        catch (Exception e) {

	            result = e.getMessage();
	        }
			
			return result;

		}
	 
	 
	 public List<String>  scan(String tablename,Map<String,Value> filterattributes, Boolean and) {

			DynamoDB dynamoDB = new DynamoDB(client);
			List<String> items = new ArrayList<String>();
			Table table = dynamoDB.getTable(tablename);
			String operator = (and) ? " and " : " or ";
				
			
			Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
		    String filterexpression = "";
		    Integer counter = filterattributes.size();
		    for (Entry<String, Value> filterattribute : filterattributes.entrySet()){
		    	String filterkey = ":f"+counter;
		    	filterexpression = filterexpression + filterattribute.getKey()+" = :f"+counter;
		    	counter--;
		    	if (counter > 0) {
		    		filterexpression = filterexpression + operator;
		    	}
		    	 expressionAttributeValues.put(filterkey,toObject(filterattribute.getValue()));
		    		
		    }


			ItemCollection<ScanOutcome> result= table.scan(filterexpression,null,expressionAttributeValues);
			
			Iterator<Item> iterator = result.iterator();
	        while (iterator.hasNext()) {
	            items.add(iterator.next().toJSONPretty());
	        }

			return items;
		}
	 
	 
	 
	 
	 public String updateItem(String tablename,String key,Value keyvalue,String sortkey,Value sortkeyvalue,Map<String,Value> updateattributes) {

			DynamoDB dynamoDB = new DynamoDB(client);
			List<String> items = new ArrayList<String>();
			Table table = dynamoDB.getTable(tablename);
			String result = "";
			String updateexpression = "set ";
			
			
			Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
			if (updateattributes.containsKey("payload")) {
				JSONObject jsonObj = new JSONObject(updateattributes.get("payload").get().toString());
				Map<String, Object> map = jsonObj.toMap();
				 Integer counter = map.size();
				 for (Entry<String, Object> updateattribute : map.entrySet()){
				  	String updatekey = ":u"+counter;
				  	updateexpression  = updateexpression  + updateattribute.getKey()+" = :u"+counter;
				   	counter--;
				   	if (counter > 0) {
				   		updateexpression = updateexpression + " , ";
				   	}
			    	 expressionAttributeValues.put(updatekey,updateattribute.getValue());
			    }
			}
			else {
				 Integer counter = updateattributes.size();
				 for (Entry<String, Value> updateattribute : updateattributes.entrySet()){
				  	String updatekey = ":u"+counter;
				  	updateexpression  = updateexpression  + updateattribute.getKey()+" = :u"+counter;
				   	counter--;
				   	if (counter > 0) {
				   		updateexpression = updateexpression + " , ";
				   	}
			    	 expressionAttributeValues.put(updatekey,toObject(updateattribute.getValue()));
			    }
			}
			
			UpdateItemSpec updateItemSpec = new UpdateItemSpec();
		        
			if (sortkey == null || sortkey == "")
			{
				updateItemSpec.withPrimaryKey(key,toObject(keyvalue))
				.withUpdateExpression(updateexpression)
				.withValueMap(expressionAttributeValues)
				.withReturnValues(ReturnValue.UPDATED_NEW);
				
			}
			else {
				updateItemSpec.withPrimaryKey(key,toObject(keyvalue),sortkey,toObject(sortkeyvalue))
				.withUpdateExpression(updateexpression)
				.withValueMap(expressionAttributeValues)
				.withReturnValues(ReturnValue.UPDATED_NEW);
			}
			

		    try {
		    	UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
		    	result = "Success";

		    }
		    catch (Exception e) {
		    	result = e.getMessage();
		    }
		       
		    return result;
		}
	 
	 
	 
	 public String getAttributeValue(String itemJSON, String key) {
		 JSONObject json = new JSONObject(itemJSON);
		 String value ;
		 try {
			value = json.query("/"+key.replaceAll("\\.", "/")).toString();
		 }
		 catch (Exception e){
			 value=null;;
		 }
		 return value;
	 }



	public String insertItem(String tablename,String key,Value keyvalue,Map<String,Value> insertattributes) {
	
		DynamoDB dynamoDB = new DynamoDB(client);
		Table table = dynamoDB.getTable(tablename);
		String result = "";
		
	    
	    Item newitem = new Item().withPrimaryKey(key,toObject(keyvalue));
	    
		if (insertattributes.containsKey("payload")) {
			JSONObject jsonObj = new JSONObject(insertattributes.get("payload").get().toString());
			Map<String, Object> map = jsonObj.toMap();
			for (Entry<String, Object> insertattribute : map.entrySet()){
				String name = insertattribute.getKey();
				String type = insertattribute.getValue().getClass().getSimpleName();
				newitem.with(name, insertattribute.getValue());
			}
		}
		else
		{
			for (Entry<String, Value> insertattribute : insertattributes.entrySet()){
				String name = insertattribute.getKey();
				String type = insertattribute.getValue().getClass().getSimpleName();
				newitem.with(name, toObject(insertattribute.getValue()));
			}
	    }
	    
	
	    try {
	       	PutItemOutcome outcome = table.putItem(newitem);
	        result = "Success";
	
       }
	   catch (Exception e) {
		    result =e.getMessage();
	   }
	        
	   return result;
	}
	
	
	
	public String deleteItemDB(String tablename,String partitionkey,Value partitionvalue,String sortkey,Value sortvalue) {
		
		DynamoDB dynamoDB = new DynamoDB(client);
		Table table = dynamoDB.getTable(tablename);
		String result = "";
		DeleteItemSpec  deleteitem;
		try {
			if (sortkey == null || sortkey == "") {
				deleteitem= new DeleteItemSpec().withPrimaryKey(partitionkey,toObject(partitionvalue));
			}
			else
			{
				deleteitem= new DeleteItemSpec().withPrimaryKey(partitionkey,toObject(partitionvalue),sortkey,toObject(sortvalue));
			}
            DeleteItemOutcome outcome = table.deleteItem(deleteitem);
	        result = "Success";
	    	
       }
	   catch (Exception e) {
		    result =e.getMessage();
	   }
	        
	   return result;
	}
	
	
	
	public String getItemDB(String tablename,String partitionkey,Value partitionvalue,String sortkey,Value sortvalue) {
		
		DynamoDB dynamoDB = new DynamoDB(client);
		Table table = dynamoDB.getTable(tablename);
		String result = "";
		Item item;
		try {
			if (sortkey == null || sortkey == "") {
			    item = table.getItem(partitionkey, toObject(partitionvalue));
			}
			else
			{
				item = table.getItem(partitionkey, toObject(partitionvalue),sortkey,toObject(sortvalue));
			}
	         result = item.toJSONPretty();
		           	
	       }
		   catch (Exception e) {
			    result = e.getMessage();
		   }
		        
		   return result;
	}
	    
	    
	public boolean isDouble(NumberValue value) {
			
			Double dvalue = value.get();
			Integer ivalue = dvalue.intValue();
			Double diff = dvalue - Double.valueOf(ivalue);
			System.out.println("Diff" +diff);
			return (diff != 0.0000000000);
			
			
	}
		
		
		public Object toObject(Value value) {
	    	String type = value.getClass().getSimpleName();
	    	Object returnValue = new String() ;
			switch (type) {
			case "StringValue":
				returnValue = ((StringValue)value).get();
				break;
			case "NumberValue":
					NumberValue nvalue = (NumberValue)value;
					if (isDouble(nvalue)) {
						returnValue = nvalue.get();
					}
					else
					{
						returnValue = nvalue.get().longValue();
					}
				break;
			case "BooleanValue":
					returnValue =((BooleanValue)value).get();
				break;
			default:
				break;
			}
			
			return returnValue;
					
		}






	
	
}
