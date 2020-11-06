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
import com.automationanywhere.botcommand.data.impl.NumberValue;
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

import static com.automationanywhere.commandsdk.model.AttributeType.SELECT;
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
@CommandPkg(label = "Create Table", name = "CreateTable", description = "Create a Table", 
icon = "pkg.svg", node_label = "Create Table {{sessionName}}", comment = true ,  text_color = "#FF9900" , background_color =  "#FF9900" ,
return_type=DataType.STRING , return_label="Status", return_required=true)

public class CreateTable{
 
    @Sessions
    private Map<String, Object> sessions;
    
    @Execute
    public StringValue action(@Idx(index = "1", type = TEXT) @Pkg(label = "Session Name",  default_value_type = STRING, default_value = "Default") @NotEmpty String sessionName,
    					 @Idx(index = "2", type = TEXT) @Pkg(label = "Table",  default_value_type = STRING) @NotEmpty String table,
    					 @Idx(index = "3", type = TEXT) @Pkg(label = "Partition Key",  default_value_type = STRING) @NotEmpty String partitionkey,
    			    	 @Idx(index = "4", type = SELECT, options = {
    								@Idx.Option(index = "4.1", pkg = @Pkg(label = "String", value = "S")),
    								@Idx.Option(index = "4.2", pkg = @Pkg(label = "Number", value = "N"))}) 
    								@Pkg(label = "Partition Key Type", default_value = "S", default_value_type = STRING) @NotEmpty String partitionkeytype,
    					 @Idx(index = "5", type = TEXT) @Pkg(label = "Sort Key",  default_value_type = STRING)  String sortkey,
    			    	 @Idx(index = "6", type = SELECT, options = {
    								@Idx.Option(index = "6.1", pkg = @Pkg(label = "String", value = "S")),
    								@Idx.Option(index = "6.2", pkg = @Pkg(label = "Number", value = "N"))}) 
    								@Pkg(label = "Sort Key Type", default_value = "S", default_value_type = STRING) String sortkeytype,
    					 @Idx(index = "7", type = AttributeType.NUMBER) @Pkg(label = "Throughput",  default_value_type = DataType.NUMBER) @NotEmpty Number throughput) {
 

    	AWSDynamoDBConnection connection  = (AWSDynamoDBConnection) this.sessions.get(sessionName);  
    	String returnValue = "";
    	

    	returnValue = connection.create(table, partitionkey,partitionkeytype, sortkey,sortkeytype,throughput.longValue() );

 	   return new StringValue(returnValue); 


    }
 
    
    
    public void setSessions(Map<String, Object> sessions) {
        this.sessions = sessions;
    }
    

    
    
 
    
    
}
