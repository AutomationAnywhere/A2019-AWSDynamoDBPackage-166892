package com.automationanywhere.botcommand.sk;

import com.automationanywhere.bot.service.GlobalSessionContext;
import com.automationanywhere.botcommand.BotCommand;
import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import java.lang.ClassCastException;
import java.lang.Deprecated;
import java.lang.Number;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class CreateTableCommand implements BotCommand {
  private static final Logger logger = LogManager.getLogger(CreateTableCommand.class);

  private static final Messages MESSAGES_GENERIC = MessagesFactory.getMessages("com.automationanywhere.commandsdk.generic.messages");

  @Deprecated
  public Optional<Value> execute(Map<String, Value> parameters, Map<String, Object> sessionMap) {
    return execute(null, parameters, sessionMap);
  }

  public Optional<Value> execute(GlobalSessionContext globalSessionContext,
      Map<String, Value> parameters, Map<String, Object> sessionMap) {
    logger.traceEntry(() -> parameters != null ? parameters.toString() : null, ()-> sessionMap != null ?sessionMap.toString() : null);
    CreateTable command = new CreateTable();
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

    if(parameters.containsKey("partitionkeytype") && parameters.get("partitionkeytype") != null && parameters.get("partitionkeytype").get() != null) {
      convertedParameters.put("partitionkeytype", parameters.get("partitionkeytype").get());
      if(!(convertedParameters.get("partitionkeytype") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","partitionkeytype", "String", parameters.get("partitionkeytype").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("partitionkeytype") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","partitionkeytype"));
    }
    if(convertedParameters.get("partitionkeytype") != null) {
      switch((String)convertedParameters.get("partitionkeytype")) {
        case "S" : {

        } break;
        case "N" : {

        } break;
        default : throw new BotCommandException(MESSAGES_GENERIC.getString("generic.InvalidOption","partitionkeytype"));
      }
    }

    if(parameters.containsKey("sortkey") && parameters.get("sortkey") != null && parameters.get("sortkey").get() != null) {
      convertedParameters.put("sortkey", parameters.get("sortkey").get());
      if(!(convertedParameters.get("sortkey") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","sortkey", "String", parameters.get("sortkey").get().getClass().getSimpleName()));
      }
    }

    if(parameters.containsKey("sortkeytype") && parameters.get("sortkeytype") != null && parameters.get("sortkeytype").get() != null) {
      convertedParameters.put("sortkeytype", parameters.get("sortkeytype").get());
      if(!(convertedParameters.get("sortkeytype") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","sortkeytype", "String", parameters.get("sortkeytype").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("sortkeytype") != null) {
      switch((String)convertedParameters.get("sortkeytype")) {
        case "S" : {

        } break;
        case "N" : {

        } break;
        default : throw new BotCommandException(MESSAGES_GENERIC.getString("generic.InvalidOption","sortkeytype"));
      }
    }

    if(parameters.containsKey("throughput") && parameters.get("throughput") != null && parameters.get("throughput").get() != null) {
      convertedParameters.put("throughput", parameters.get("throughput").get());
      if(!(convertedParameters.get("throughput") instanceof Number)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","throughput", "Number", parameters.get("throughput").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("throughput") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","throughput"));
    }

    command.setSessions(sessionMap);
    try {
      Optional<Value> result =  Optional.ofNullable(command.action((String)convertedParameters.get("sessionName"),(String)convertedParameters.get("table"),(String)convertedParameters.get("partitionkey"),(String)convertedParameters.get("partitionkeytype"),(String)convertedParameters.get("sortkey"),(String)convertedParameters.get("sortkeytype"),(Number)convertedParameters.get("throughput")));
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
