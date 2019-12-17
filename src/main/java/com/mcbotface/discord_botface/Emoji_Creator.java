package com.mcbotface.discord_botface;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import javax.security.auth.login.LoginException;
import javax.swing.ImageIcon;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.MessageEmbed.ImageInfo;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageEmbedEvent;


public class Emoji_Creator {
	private MessageReceivedEvent event;
	private String emoji_name;
	
	Emoji_Creator(MessageReceivedEvent e, String name){
		this.event = e;
		emoji_name = name;
		create_emoji();
	}
	
	
	private void create_emoji()  {
		Boolean attachments = (event.getMessage().getAttachments().size() >= 1 ? true : false);
		if(attachments) {
			List<Attachment> attch = event.getMessage().getAttachments();
			for(Attachment a : attch) {
				if(a.isImage()) {
					try {
						a.downloadToFile();
						File f = new File(a.getFileName());
						while(!f.exists()) {
							System.out.println("Sleeping");
							Thread.sleep(2000);
						}
						repeat_resize(f, a.getFileExtension());
						System.out.println("Image resized to needed size");
						add_emoji(f);
						System.out.println("added emoji");
						f.delete();
//						event.getGuild().createEmote(name, icon, roles);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
			
		}else {
			
		}	
	}
	private void add_emoji(File img) {
		try {
			System.out.println(img.getName());
			Icon ic = Icon.from(img);
			System.out.println(emoji_name);
			event.getGuild().createEmote(emoji_name, ic).complete();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void repeat_resize(File a, String ext) {
		System.out.println("repeat_resize");
		try {
			if(!checkSize(a)) {
				System.out.println("Resizing image - ");
				BufferedImage b = ImageIO.read(a);
				int type = b.getType() == 0? BufferedImage.TYPE_INT_ARGB : b.getType();
				BufferedImage resizedBuffImg = new BufferedImage((int) Math.round(b.getWidth() * 0.25), (int)Math.round(b.getHeight() * 0.25), type);
				Graphics2D g = resizedBuffImg.createGraphics();
				g.drawImage(b, 0, 0, (int)Math.round(b.getWidth() * 0.25), (int)Math.round(b.getHeight() * 0.25), null);
				g.dispose();
				a.delete();
				ImageIO.write(resizedBuffImg, ext, a);
				repeat_resize(a, ext);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Boolean checkSize(File img) {
		System.out.println("checkSize");
		if(img != null && img.exists()) {
			System.out.println("Checking file size - " + img.length());
			if((img.length() / 1024) <= 256)
				return true;
		}
		return false;
	}
	
	
	private String find__old_message_url() {
		String url = "";
		
		
		
		
		
		return url;
	}
	
}
