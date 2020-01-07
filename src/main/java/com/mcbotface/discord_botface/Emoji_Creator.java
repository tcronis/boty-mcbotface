package com.mcbotface.discord_botface;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.entities.Message.Attachment;



public class Emoji_Creator {
	private MessageReceivedEvent event;
	private String emoji_name;
	
	Emoji_Creator(MessageReceivedEvent event){
		this.event = event;
	}
	
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
							Thread.sleep(2000);
						}
						repeat_resize(f, a.getFileExtension());
						add_emoji(f, emoji_name);
						f.delete();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		else {
			event.getChannel().sendMessage("<@"+event.getAuthor().getId()+">, everyone alread knows you're special, "+
						"there is no need to fuck my these commands smooth brain, refer to the help guide.");
		}	
	}
	//MAX_NAME_IS 32 MIN_NAME_IS 2
	private void add_emoji(File img, String name) {
		try {
			Icon ic = Icon.from(img);
			event.getGuild().createEmote(name, ic).complete();
		}catch(Exception e) {
			event.getChannel().sendMessage("ERROR - I couldn't add the emoji to the server ¯\\_(ツ)_/¯").queue();
			e.printStackTrace();
		}
	}
	
	//recursively resizing the image if needed
	private void repeat_resize(File a, String ext) {
		try {
			if(!checkSize(a)) {
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
			event.getChannel().sendMessage("ERROR - I failed on resizing the image ¯\\_(ツ)_/¯");
		}
	}
	//checking the size of the image, since it can't be greater than 256 kb
	private Boolean checkSize(File img) {
		if(img != null && img.exists()) {
			if((img.length() / 1024) <= 256)
				return true;
		}
		return false;
	}
	
	public void removeEmoji(long emoji_id) {
	}
}
