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
        builder.setToken(authen.getToken());
        builder.addEventListeners(new App());
        builder.build();
    }
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
    	if(event.getMessage().getAuthor().isBot())
    		return;
    	if(event.getMessage().getContentDisplay().toString().charAt(0) == '!') {
    		String command = event.getMessage().getContentDisplay().toString();
    		//!emoji call
    		
    		//!self destruct call
    		
    		
    	}
    	
    	//F reaction after someone put F in the chat
    	
    	
    	System.out.println("recieved the message");
    	System.out.println(event.getAuthor());
    	System.out.println(event.getMessage().getContentDisplay());
    	
    	
    	
    }
}
