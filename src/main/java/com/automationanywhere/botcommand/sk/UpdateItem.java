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


import java.util.HashMap;

import java.util.Map;
import java.util.Map.Entry;



/**
 * @author Stefan Karsten
 *
 */

@BotCommand
@CommandPkg(label = "Update Item", name = "UpdateItem", description = "Update Item Values", 
icon = "pkg.svg", node_label = "Update Item {{sessionName}}", comment = true ,  text_color = "#FF9900" , background_color =  "#FF9900" ,
return_type=STRING , return_label="Status", return_required=true)

public class UpdateItem{
 
    @Sessions
    private Map<String, Object> sessions;
    
    @Execute
    public StringValue action(@Idx(index = "1", type = TEXT) @Pkg(label = "Session Name",  default_value_type = STRING, default_value = "Default") @NotEmpty String sessionName,
    								@Idx(index = "2", type = TEXT) @Pkg(label = "Table",  default_value_type = STRING) @NotEmpty String table,
    								@Idx(index = "3", type = TEXT) @Pkg(label = "Partition Key",  default_value_type = STRING) @NotEmpty String partitionkey,
    								@Idx(index = "4", type = AttributeType.VARIABLE) @Pkg(label = "Partition Value",  default_value_type = DataType.ANY) @NotEmpty Value partitionvalue,
       								@Idx(index = "5", type = TEXT) @Pkg(label = "Sort Key",  default_value_type = STRING) String sortkey,
    								@Idx(index = "6", type = AttributeType.VARIABLE) @Pkg(label = "Sort Value",  default_value_type = DataType.ANY)  Value sortvalue,
    								@Idx(index = "7", type = AttributeType.DICTIONARY )  @Pkg(label = "Dictionary of Item Attributes"  , description = "If an entry with the key 'payload' and a JSON value exists, it is used as the payload" , default_value_type = DataType.DICTIONARY ) @NotEmpty Map<String,Value> updateDict)
    								throws Exception {
 

    	AWSDynamoDBConnection connection  = (AWSDynamoDBConnection) this.sessions.get(sessionName);  
    	
    	String result = connection.updateItem(table,partitionkey,partitionvalue,sortkey,sortvalue,updateDict) ;

        return new StringValue(result);


    }
 
    
    
    public void setSessions(Map<String, Object> sessions) {
        this.sessions = sessions;
    }
    

    
    
 
    
    
}
