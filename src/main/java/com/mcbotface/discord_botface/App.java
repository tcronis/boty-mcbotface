package com.mcbotface.discord_botface;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed.ImageInfo;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageEmbedEvent;

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
    		//strip spaces recursively
    		
    		Boolean space = event.getMessage().getContentDisplay().contains(" ");
    		Boolean attachments = (event.getMessage().getAttachments().size() >= 1 ? true : false);
    		String command;
    		if(space)
    			command = event.getMessage().getContentDisplay().toString().substring(1, event.getMessage().getContentDisplay().indexOf(" "));
    		else
    			command = event.getMessage().getContentDisplay().toString().substring(1);
    		
    		//!create_emoji-NAMEOFEMOJI - will create an emoji based on the photo given
    		if(command.toLowerCase().contains("create_emoji")) {
    			String emoji_name = "";
    			try {
    				//substring the emoji name and then remove all non-alphabetical characters
    				emoji_name = event.getMessage().getContentDisplay().substring(event.getMessage().getContentDisplay().indexOf("-") + 1);
    				emoji_name = emoji_name.replaceAll("[^a-zA-Z]", "");
    				if(emoji_name.length() < 2) {
    					event.getChannel().sendMessage("Chief, your emoji name must be larger than 2 characters").queue();
    					throw new Exception("Too small of an emoji name");
    				}
    				if(emoji_name.length() > 32) {
    					event.getChannel().sendMessage("Chief, you emoji name can't be longer than 32 chars");
    					throw new Exception("Too large of an emoji name");
    				}
    			}catch(Exception e){
    				event.getChannel().sendMessage("Chief, looks like there was an error in your emoji name. Remember must be" +
    							"it must be at least 2 characters long and only alphabetical characters").queue();
    				e.printStackTrace();
    			}
    			try {
    				new Emoji_Creator(event, emoji_name);
    			} catch(Exception e) {
    				System.out.println("Error occured when creating the emoji object");
    				e.printStackTrace();
    			}
    		}
    		//!execute_order_66 - kill all the bots on the server
    		else if(command.equalsIgnoreCase("execute_order_66")) {
    			//get a list of the members of the discord and find the bots, then kick them off the server
    			List <Member> users = event.getGuild().getMembers();
    			Boolean kicked = false;
    			for(Member you : users) {
    				if(you.getUser().isBot()) {
    					System.out.println("attempting to kick - " + you.getUser().getName());
    					event.getChannel().sendMessage("Cya later fucko <@" + you.getId() + ">").queue();
    					event.getGuild().kick(you.getId()).queue();
    					kicked = true;
    				}
    			}
    			if(kicked) {
    				event.getChannel().sendMessage("Damn it feels good to get all dem BOTS out of here, amirights?");
    			}
    		}
    		//!murder_INSERT_AT_NAME - remove user by this name off the sever
    		else if(command.contains("murder")) {
    			List <Member> users_to_kick = event.getMessage().getMentionedMembers();
    			if(users_to_kick.size() > 0) {
    				event.getChannel().sendMessage("I would delighted to kick them").queue();
    				for(Member m : users_to_kick) {
    					event.getGuild().kick(m).queue();
    				}
    			}else {
    				event.getChannel().sendMessage("Listen man, you need to learn how to use my commands. Refer to my commands list on how to kick other bots/members. This is your first and list warning.").queue();
    			}
    		}
    		else if(command.equalsIgnoreCase("i_am_a_smooth_brain") || command.equalsIgnoreCase("help")) {    			
    			event.getChannel().sendMessage(getCommandsTextFile()).queue();
    		}
    		else if(command.equalsIgnoreCase("insult")){
    			if(event.getMessage().getMentionedMembers().size() > 0) {
    				event.getMessage().getMentionedMembers().forEach((Member m) -> {
    					try {
    						File insults = new File("insults.txt");
							BufferedReader reader = new BufferedReader(new FileReader(insults));
							int num_insults_in_file = Integer.valueOf(reader.readLine());
							int location = new Random().nextInt(num_insults_in_file) + 1;	//first line is the number of insults, shift by 1 
							int count = 0;
							while(count < (location - 1)) {reader.readLine(); count++;}
							String insult_to_say = reader.readLine();
							reader.close();
							event.getChannel().sendMessage("<@" + m.getId() + "> " + insult_to_say).queue();
						} catch (Exception e) {e.printStackTrace();}
    					
    				});
    			}else {
    				try {
    					File insults = new File("insults.txt");
						BufferedReader reader = new BufferedReader(new FileReader(insults));
						int num_insults_in_file = Integer.valueOf(reader.readLine());
						int location = new Random().nextInt(num_insults_in_file) + 1;	//first line is the number of insults, shift by 1 
						int count = 0;
						while(count < (location - 1)) {reader.readLine(); count++;}
						String insult_to_say = reader.readLine();
						reader.close();
						event.getChannel().sendMessage("<@" + event.getMessage().getAuthor().getId() + "> " + insult_to_say).queue();
    				}catch(Exception e) {
    					e.printStackTrace();
    				}
    			}
    		}
    		else {
    			event.getChannel().sendMessage("I don't know what the fuck you meant to say, but "+ 
    					"you can put '!i_am_a_smooth_brain' to display all of my commands").queue();
    		}
    	}
    	

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
    		file = "**Well some FUCK FACE didn't put the commands.txt file in my sighxts, so I dont' know what to tell you. Go look me up on github https://github.com/tcronis/boty-mcbotface";
    		e.printStackTrace();
    	}
    	return file;
    }
}
