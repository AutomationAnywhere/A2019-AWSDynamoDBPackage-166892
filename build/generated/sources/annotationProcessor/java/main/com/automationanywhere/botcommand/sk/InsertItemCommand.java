package com.automationanywhere.botcommand.sk;

import com.automationanywhere.bot.service.GlobalSessionContext;
import com.automationanywhere.botcommand.BotCommand;
import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import java.lang.ClassCastException;
import java.lang.Deprecated;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class InsertItemCommand implements BotCommand {
  private static final Logger logger = LogManager.getLogger(InsertItemCommand.class);

  private static final Messages MESSAGES_GENERIC = MessagesFactory.getMessages("com.automationanywhere.commandsdk.generic.messages");

  @Deprecated
  public Optional<Value> execute(Map<String, Value> parameters, Map<String, Object> sessionMap) {
    return execute(null, parameters, sessionMap);
  }

  public Optional<Value> execute(GlobalSessionContext globalSessionContext,
      Map<String, Value> parameters, Map<String, Object> sessionMap) {
    logger.traceEntry(() -> parameters != null ? parameters.toString() : null, ()-> sessionMap != null ?sessionMap.toString() : null);
    InsertItem command = new InsertItem();
    HashMap<String, Object> convertedParameters = new HashMap<String, Object>();
    if(parameters.containsKey("sessionName") && parameters.get("sessionName") != null && parameters.get("sessionName").get() != null) {
      convertedParameters.put("sessionName", parameters.get("sessionName").get());
      if(!(convertedParameters.get("sessionName") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","sessionName", "String", parameters.get("sessionName").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("sessionName") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","sessionName"));
    }

    if(parameters.containsKey("table") && parameters.get("table") != null && parameters.get("table").get() != null) {
      convertedParameters.put("table", parameters.get("table").get());
      if(!(convertedParameters.get("table") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","table", "String", parameters.get("table").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("table") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","table"));
    }

    if(parameters.containsKey("partitionkey") && parameters.get("partitionkey") != null && parameters.get("partitionkey").get() != null) {
      convertedParameters.put("partitionkey", parameters.get("partitionkey").get());
      if(!(convertedParameters.get("partitionkey") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","partitionkey", "String", parameters.get("partitionkey").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("partitionkey") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","partitionkey"));
    }

    if(parameters.containsKey("partitionvalue") && parameters.get("partitionvalue") != null && parameters.get("partitionvalue").get() != null) {
      convertedParameters.put("partitionvalue", parameters.get("partitionvalue").get());
      if(!(parameters.get("partitionvalue") instanceof Value)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","partitionvalue", "Value", parameters.get("partitionvalue").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("partitionvalue") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","partitionvalue"));
    }

    if(parameters.containsKey("insertattributes") && parameters.get("insertattributes") != null && parameters.get("insertattributes").get() != null) {
      convertedParameters.put("insertattributes", parameters.get("insertattributes").get());
      if(!(convertedParameters.get("insertattributes") instanceof Map)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","insertattributes", "Map", parameters.get("insertattributes").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("insertattributes") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","insertattributes"));
    }

    command.setSessions(sessionMap);
    try {
      Optional<Value> result =  Optional.ofNullable(command.action((String)convertedParameters.get("sessionName"),(String)convertedParameters.get("table"),(String)convertedParameters.get("partitionkey"),(Value)parameters.get("partitionvalue") ,(Map<String, Value>)convertedParameters.get("insertattributes")));
      return logger.traceExit(result);
    }
    catch (ClassCastException e) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.IllegalParameters","action"));
    }
    catch (BotCommandException e) {
      logger.fatal(e.getMessage(),e);
      throw e;
    }
    catch (Throwable e) {
      logger.fatal(e.getMessage(),e);
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.NotBotCommandException",e.getMessage()),e);
    }
  }
}
