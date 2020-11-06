/*
 * Copyright (c) 2020 Automation Anywhere.
 * All rights reserved.
 *
 * This software is the proprietary information of Automation Anywhere.
 * You shall use it only in accordance with the terms of the license agreement
 * you entered into with Automation Anywhere.
 * 
 */


/**
 * @author Stefan Karsten
 *
 */

package com.automationanywhere.botcommand.sk;

import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.ListValue;
import com.automationanywhere.botcommand.data.impl.StringValue;
import com.automationanywhere.botcommand.sk.utils.AWSDynamoDBConnection;
import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.CommandPkg;
import com.automationanywhere.commandsdk.annotations.Execute;
import com.automationanywhere.commandsdk.annotations.Idx;
import com.automationanywhere.commandsdk.annotations.Pkg;
import com.automationanywhere.commandsdk.annotations.Sessions;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.model.AttributeType;
import com.automationanywhere.commandsdk.model.DataType;

import static com.automationanywhere.commandsdk.model.AttributeType.TEXT;
import static com.automationanywhere.commandsdk.model.DataType.STRING;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;



/**
 * @author Stefan Karsten
 *
 */

@BotCommand
@CommandPkg(label = "Scan Table", name = "ScanTable", description = "Scan Table for Values", 
icon = "pkg.svg", node_label = "Scan Table {{sessionName}}", comment = true ,  text_color = "#FF9900" , background_color =  "#FF9900" ,
return_type=DataType.LIST , return_sub_type = DataType.STRING , return_label="List", return_required=true)

public class Scan{
 
    @Sessions
    private Map<String, Object> sessions;
    
    @Execute
    public ListValue<String> action(@Idx(index = "1", type = TEXT) @Pkg(label = "Session Name",  default_value_type = STRING, default_value = "Default") @NotEmpty String sessionName,
    								@Idx(index = "2", type = TEXT) @Pkg(label = "Table",  default_value_type = STRING) @NotEmpty String table,
    								@Idx(index = "3", type = AttributeType.DICTIONARY )  @Pkg(label = "Dictionary of filter"  , default_value_type = DataType.DICTIONARY ) @NotEmpty Map<String,Value> filterDict,
									@Idx(index = "4", type = AttributeType.BOOLEAN)  @Pkg(label = "use AND operator"  , default_value_type = DataType.BOOLEAN, default_value = "true") @NotEmpty Boolean logical)
    								throws Exception {
 

    	AWSDynamoDBConnection connection  = (AWSDynamoDBConnection) this.sessions.get(sessionName);  
    	String returnValue = "";
    	
		List<String> result = connection.scan(table, filterDict, logical);
    	ListValue<String> returnvalue = new ListValue<String>();
		List<Value> values = new ArrayList<Value>();
 	
 	   for (Iterator iterator = result.iterator(); iterator.hasNext();) {
 		   String value = (String) iterator.next();
 		  	values.add(new StringValue(value));
 	   }
 	   
 	   returnvalue.set(values);
 	   return returnvalue; 


    }
 
    
    
    public void setSessions(Map<String, Object> sessions) {
        this.sessions = sessions;
    }
    

    
    
 
    
    
}
