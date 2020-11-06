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

public final class UpdateItemCommand implements BotCommand {
  private static final Logger logger = LogManager.getLogger(UpdateItemCommand.class);

  private static final Messages MESSAGES_GENERIC = MessagesFactory.getMessages("com.automationanywhere.commandsdk.generic.messages");

  @Deprecated
  public Optional<Value> execute(Map<String, Value> parameters, Map<String, Object> sessionMap) {
    return execute(null, parameters, sessionMap);
  }

  public Optional<Value> execute(GlobalSessionContext globalSessionContext,
      Map<String, Value> parameters, Map<String, Object> sessionMap) {
    logger.traceEntry(() -> parameters != null ? parameters.toString() : null, ()-> sessionMap != null ?sessionMap.toString() : null);
    UpdateItem command = new UpdateItem();
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

    if(parameters.containsKey("sortkey") && parameters.get("sortkey") != null && parameters.get("sortkey").get() != null) {
      convertedParameters.put("sortkey", parameters.get("sortkey").get());
      if(!(convertedParameters.get("sortkey") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","sortkey", "String", parameters.get("sortkey").get().getClass().getSimpleName()));
      }
    }

    if(parameters.containsKey("sortvalue") && parameters.get("sortvalue") != null && parameters.get("sortvalue").get() != null) {
      convertedParameters.put("sortvalue", parameters.get("sortvalue").get());
      if(!(parameters.get("sortvalue") instanceof Value)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","sortvalue", "Value", parameters.get("sortvalue").get().getClass().getSimpleName()));
      }
    }

    if(parameters.containsKey("updateDict") && parameters.get("updateDict") != null && parameters.get("updateDict").get() != null) {
      convertedParameters.put("updateDict", parameters.get("updateDict").get());
      if(!(convertedParameters.get("updateDict") instanceof Map)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","updateDict", "Map", parameters.get("updateDict").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("updateDict") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","updateDict"));
    }

    command.setSessions(sessionMap);
    try {
      Optional<Value> result =  Optional.ofNullable(command.action((String)convertedParameters.get("sessionName"),(String)convertedParameters.get("table"),(String)convertedParameters.get("partitionkey"),(Value)parameters.get("partitionvalue") ,(String)convertedParameters.get("sortkey"),(Value)parameters.get("sortvalue") ,(Map<String, Value>)convertedParameters.get("updateDict")));
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
