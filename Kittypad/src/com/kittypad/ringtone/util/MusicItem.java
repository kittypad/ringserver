package com.kittypad.ringtone.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MusicItem {
	
	private String ID_Name;
	private String UUID;
	private Date add_date;
	private String artist;
	private int avg_rate;
	private String category;
	private int download_count;
	private String musicName;
	private int rate_count;
	private int size;
	private String type;
	private String s3_url;
	
	public MusicItem(String ID_Name,String UUID,Date add_date,String artist,
			int avg_rate,String category,int download_count,String musicName,
			int rate_count,int size,String type,String s3_url)
	{
		this.ID_Name=ID_Name+"."+type;
		this.UUID=UUID;
		this.add_date=add_date;
		this.artist=artist;
		this.avg_rate=avg_rate;
		this.category=category;
		this.download_count=download_count;
		this.musicName=musicName;
		this.rate_count=rate_count;
		this.size=size;
		this.type=type;
		this.s3_url=s3_url;
		
	}
    public MusicItem(String UUID,String artists,String musicName,String category,String type,int size,String s3_url)
    {
    	this.ID_Name=UUID+musicName+"."+type;
    	this.UUID=UUID;
    	this.add_date=new Date();
    	this.artist=artists;
    	this.avg_rate=(int)(Math.random()*5+1);
    	this.category=category;
    	this.download_count=(int)Math.random()*2000+500;
    	this.musicName=musicName;
    	this.rate_count=1;
    	this.type=type;
    	this.size=size;
    	this.s3_url=s3_url;
    }
	public MusicItem(String UUID,String musicName,String category,String type,int size,String s3_url)
	{
		this.ID_Name=UUID+musicName+"."+type;
    	this.UUID=UUID;
    	this.add_date=new Date();
    	this.artist="unknown";
    	this.avg_rate=(int)(Math.random()*5+1);
    	this.category=category;
    	this.download_count=(int)Math.random()*2000+500;
    	this.musicName=musicName;
    	this.rate_count=1;
    	this.type=type;
    	this.size=size;
    	this.s3_url=s3_url;
		
	}
    public String getID_Name() {
		return ID_Name;
	}

	public String getUUID() {
		return UUID;
	}

	public Date getAdd_date() {
		return add_date;
	}

	public String getArtist() {
		return artist;
	}

	public int getAvg_rate() {
		return avg_rate;
	}

	public String getCategory() {
		return category;
	}

	public int getDownload_count() {
		return download_count;
	}

	public String getMusicName() {
		return musicName;
	}

	public int getRate_count() {
		return rate_count;
	}

	public int getSize() {
		return size;
	}

	public String getType() {
		return type;
	}
	public String getS3_url(){
		return s3_url;
	}
	public String display(){
		String item="ID_Name:"+ID_Name+"\nUUID:"+UUID+"\nadd_date"+add_date.toLocaleString()+"\nartist:"+artist
		+"\navg_rate:"+avg_rate+"\ncategory:"+category+"\ndownload_count:"+download_count+
	      "\nmusicName:"+musicName+"\nrate_count:"+rate_count+"\nsize:"+size+"\ntype"+type+"\ns3_url:"+s3_url;
		return item;
	}
   public Map<String,String> josonMap(){
		Map<String, String> musicMap = new HashMap<String, String>();
	
			musicMap.put("url", "https://s3.amazonaws.com/" +s3_url+"/"+UUID+musicName+"."+type);
		
		
		musicMap.put("musicName", musicName);
		musicMap.put("category", category);
		musicMap.put("type", type);
		musicMap.put("size", Integer.toString(size));
		musicMap.put("downloads", Integer.toString(download_count));
		musicMap.put("rate", Integer.toString(avg_rate));
       return musicMap;
	   
	}

}
