package com.automationanywhere.botcommand.sk;

import com.automationanywhere.bot.service.GlobalSessionContext;
import com.automationanywhere.botcommand.BotCommand;
import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import com.automationanywhere.core.security.SecureString;
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

public final class StartSessionAWSDynamoDBCommand implements BotCommand {
  private static final Logger logger = LogManager.getLogger(StartSessionAWSDynamoDBCommand.class);

  private static final Messages MESSAGES_GENERIC = MessagesFactory.getMessages("com.automationanywhere.commandsdk.generic.messages");

  @Deprecated
  public Optional<Value> execute(Map<String, Value> parameters, Map<String, Object> sessionMap) {
    return execute(null, parameters, sessionMap);
  }

  public Optional<Value> execute(GlobalSessionContext globalSessionContext,
      Map<String, Value> parameters, Map<String, Object> sessionMap) {
    logger.traceEntry(() -> parameters != null ? parameters.toString() : null, ()-> sessionMap != null ?sessionMap.toString() : null);
    StartSessionAWSDynamoDB command = new StartSessionAWSDynamoDB();
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

    if(parameters.containsKey("region") && parameters.get("region") != null && parameters.get("region").get() != null) {
      convertedParameters.put("region", parameters.get("region").get());
      if(!(convertedParameters.get("region") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","region", "String", parameters.get("region").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("region") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","region"));
    }
    if(convertedParameters.get("region") != null) {
      switch((String)convertedParameters.get("region")) {
        case "us-east-2" : {

        } break;
        case "us-east-1" : {

        } break;
        case "us-west-1" : {

        } break;
        case "us-west-2" : {

        } break;
        case "ap-east-1" : {

        } break;
        case "ap-south-1" : {

        } break;
        case "ap-northeast-3" : {

        } break;
        case "ap-northeast-2" : {

        } break;
        case "ap-southeast-1" : {

        } break;
        case "ap-southeast-2" : {

        } break;
        case "ap-northeast-1" : {

        } break;
        case "ca-central-1" : {

        } break;
        case "cn-north-1" : {

        } break;
        case "cn-northwest-1" : {

        } break;
        case "eu-central-1" : {

        } break;
        case "eu-west-1" : {

        } break;
        case "eu-west-2" : {

        } break;
        case "eu-west-3" : {

        } break;
        case "eu-north-1" : {

        } break;
        case "me-south-1" : {

        } break;
        case "sa-east-1" : {

        } break;
        case "us-gov-east-1" : {

        } break;
        case "us-gov-west-1" : {

        } break;
        default : throw new BotCommandException(MESSAGES_GENERIC.getString("generic.InvalidOption","region"));
      }
    }

    if(parameters.containsKey("access_key_id") && parameters.get("access_key_id") != null && parameters.get("access_key_id").get() != null) {
      convertedParameters.put("access_key_id", parameters.get("access_key_id").get());
      if(!(convertedParameters.get("access_key_id") instanceof SecureString)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","access_key_id", "SecureString", parameters.get("access_key_id").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("access_key_id") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","access_key_id"));
    }

    if(parameters.containsKey("secret_key_id") && parameters.get("secret_key_id") != null && parameters.get("secret_key_id").get() != null) {
      convertedParameters.put("secret_key_id", parameters.get("secret_key_id").get());
      if(!(convertedParameters.get("secret_key_id") instanceof SecureString)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","secret_key_id", "SecureString", parameters.get("secret_key_id").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("secret_key_id") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","secret_key_id"));
    }

    command.setSessions(sessionMap);
    command.setGlobalSessionContext(globalSessionContext);
    try {
      command.start((String)convertedParameters.get("sessionName"),(String)convertedParameters.get("region"),(SecureString)convertedParameters.get("access_key_id"),(SecureString)convertedParameters.get("secret_key_id"));Optional<Value> result = Optional.empty();
      return logger.traceExit(result);
    }
    catch (ClassCastException e) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.IllegalParameters","start"));
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
