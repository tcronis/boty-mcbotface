package com.mcbotface.discord_botface;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed.ImageInfo;
import net.dv8tion.jda.api.entities.MessageHistory;
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
//        builder.setStatus(OnlineStatus.OFFLINE);
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
    		Boolean attachments = (event.getMessage().getAttachments().size() >= 1 ? true : false);
    		String command;
    		if(space)
    			command = event.getMessage().getContentDisplay().toString().substring(1, event.getMessage().getContentDisplay().indexOf(" "));
    		else
    			command = event.getMessage().getContentDisplay().toString().substring(1);
    		//!create_emoji-NAMEOFEMOJI - will create an emoji based on the photo given
    		if(command.toLowerCase().contains("create_emoji")) {
    			Boolean spaces = (event.getMessage().getContentDisplay().contains(" "));
    			Boolean name_sep = event.getMessage().getContentDisplay().contains("-");
    			if(event.getMessage().getContentDisplay().substring(event.getMessage().getContentDisplay().indexOf("-") + 1).length() > 0) {
    				String name = "";
        			if(spaces)
        				name = event.getMessage().getContentDisplay().substring(event.getMessage().getContentDisplay().indexOf("-") + 1, event.getMessage().getContentDisplay().indexOf(" "));
        			else
        				name = event.getMessage().getContentDisplay().substring(event.getMessage().getContentDisplay().indexOf("-") + 1);
        			Emoji_Creator emoji_creation = new Emoji_Creator(event, name);
    			}else
    				event.getChannel().sendMessage("Hey the dickhead named " + event.getAuthor() + " you need to include a file name, refer to the '!i_am_a_smooth_brain' help screen");
    			
    		}
    		//!self_destruct_mcbotface - will exit the server 
    		else if(command.equalsIgnoreCase("self_destruct_mcbotface")) {
    			event.getChannel().sendMessage("Good bye cruel world :(");
    			System.exit(0);
    		}
    		//!execute_order_66 - kill all the bots on the server
    		else if(command.equalsIgnoreCase("execute_order_66")) {
    			
    		}
    		//!murder_INSERTBOTNAMEHERE - remove the bot by this name off the sever
    		else if(command.contains("murder_")) {
    			
    		}
    		else if(command.equalsIgnoreCase("i_am_a_smooth_brain")) {    			
    			event.getChannel().sendMessage(getCommandsTextFile()).queue();
    		}
    		else {
    			event.getChannel().sendMessage("I don't know what the fuck you meant to say, but "+ 
    					"you can put '!i_am_a_smooth_brain' to display all of my commands").queue();
    		}
    	}
    	
    	//F reaction after someone put F in the chat    	
    }
    
    //loading a the commands text file for it to be displayed in the  discord
    public String getCommandsTextFile() {
    	String file = "";
    	try {
        	File commands = new File("commands.txt");
        	BufferedReader reader = new BufferedReader(new FileReader(commands));
        	String line = reader.readLine() + "\n";
        	while(line != null) {
        		file += line + "\n";
        		line = reader.readLine();
        	}
        	reader.close();
    	}catch(Exception e) {
    		System.out.println("Error when reading in the commands text file");
    		file = "**Well some FUCK FACE didn't put the commands.txt file in my sights, so I dont' know what to tell you. Go look me up on github https://github.com/tcronis/boty-mcbotface";
    		e.printStackTrace();
    	}
    	return file;
    }
}
