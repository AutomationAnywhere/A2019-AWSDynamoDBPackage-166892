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

import com.automationanywhere.botcommand.data.impl.StringValue;
import com.automationanywhere.botcommand.sk.utils.AWSDynamoDBConnection;
import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.CommandPkg;
import com.automationanywhere.commandsdk.annotations.Execute;
import com.automationanywhere.commandsdk.annotations.Idx;
import com.automationanywhere.commandsdk.annotations.Pkg;
import com.automationanywhere.commandsdk.annotations.Sessions;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import com.automationanywhere.commandsdk.model.DataType;

import static com.automationanywhere.commandsdk.model.AttributeType.TEXT;
import static com.automationanywhere.commandsdk.model.DataType.STRING;


import java.util.Map;




/**
 * @author Stefan Karsten
 *
 */

@BotCommand
@CommandPkg(label = "Get Attribute Value", name = "GetAttributeValue", description = "Get Attribute Value of a Item", 
icon = "pkg.svg", node_label = "Get Attribute Value {{sessionName}}", comment = true ,  text_color = "#FF9900" , background_color =  "#FF9900" ,
return_type=DataType.STRING , return_label="Value", return_required=true)

public class GetAttributeValue{
 
    @Sessions
    private Map<String, Object> sessions;
    
	private static final Messages MESSAGES = MessagesFactory
			.getMessages("com.automationanywhere.botcommand.samples.messages");
    
	 
    
    @Execute
    public StringValue action(@Idx(index = "1", type = TEXT) @Pkg(label = "Session Name",  default_value_type = STRING, default_value = "Default") @NotEmpty String sessionName,
    								@Idx(index = "2", type = TEXT) @Pkg(label = "Item",  default_value_type = STRING) @NotEmpty String item,
    								@Idx(index = "3", type = TEXT )  @Pkg(label = "JSON Key"  , default_value_type = DataType.STRING ) @NotEmpty String key)
    								throws Exception {
 

    	AWSDynamoDBConnection connection  = (AWSDynamoDBConnection) this.sessions.get(sessionName);  
    	String value = connection.getAttributeValue(item, key) ;
    	return new StringValue(value);
       
    }
 
    
    
    public void setSessions(Map<String, Object> sessions) {
        this.sessions = sessions;
    }
    

    
    
 
    
    
}
