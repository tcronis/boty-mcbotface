package com.mcbotface.discord_botface;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed.ImageInfo;
import net.dv8tion.jda.api.events.message.MessageEmbedEvent;

/**
 * Hello world!
 *
 */
public class App extends ListenerAdapter
{
    public static void main( String[] args ) throws Exception
    {
    	
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        Authenticator authen = new Authenticator();				//call the custom authenticator class to get token
        builder.setStatus(OnlineStatus.OFFLINE);
        builder.setToken(authen.getToken());
        builder.addEventListeners(new App());
        builder.build();
    }
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
    	if(event.getMessage().getAuthor().isBot())
    		return;
    	if(event.getMessage().getContentDisplay().toString().charAt(0) == '!') {
    		Boolean space = event.getMessage().getContentDisplay().contains(" ");
    		String command;
    		if(space)
    			command = event.getMessage().getContentDisplay().toString().substring(1, event.getMessage().getContentDisplay().indexOf(" "));
    		else
    			command = event.getMessage().getContentDisplay().toString().substring(1);
    		//!create_emoji - will create an emoji based on the photo given
    		if(command.equalsIgnoreCase("create_emoji")) {
    			
    		}
    		//!self_destruct_mcbotface - will exit the server
    		else if(command.equalsIgnoreCase("self_destruct_mcbotface")) {
    			
    		}
    		//!execute_order_66 - kill all the bots on the server
    		else if(command.equalsIgnoreCase("execute_order_66")) {
    			
    		}
    		//!murder_INSERTBOTNAMEHERE - remove the bot by this name off the sever
    		else if(command.contains("murder_")) {
    			
    		}
    		else if(command.contains("i_am_a_smooth_brain")) {
    			event.getChannel().sendMessage("```I am a smooth brain```").queue();
    		}
    		else {
    			event.getChannel().sendMessage("I don't know what the fuck you meant to say, but "+ 
    					"you can put '!i_am_a_smooth_brain' to display all of my commands").queue();
    		}
    	}
    	
    	//F reaction after someone put F in the chat    	
    }
}
