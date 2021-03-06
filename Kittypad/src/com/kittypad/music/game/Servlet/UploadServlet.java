/**
 * @author mingzhu
 * this servlet is used for user upload music files 
 * first we need to storge the uploaded file into s3 bucket
 * second  we need to add 2 recodes:music Info, music and user relation
 * if the user haved in our userInfo database we still need to add user Info Into database  
 * 
 */
package com.kittypad.music.game.Servlet;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.json.JSONArray;
import com.kittypad.music.game.translate.MTranslate;
import com.kittypad.music.game.util.MusicItem;
import com.kittypad.music.game.util.S3StorageManager;
import com.kittypad.music.game.util.UserItem;
import com.kittypad.music.game.util.UserMusicUtil;
import com.kittypad.music.game.util.UserUtil;




/**
 * Servlet implementation class uploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static String bucketName = "kittypad_music_game";
	private static int SIZE_THRESHOLD=500*1024;
   
    private  static ObjectListing objectListing;
    private static int uploadCount = 0;
    
    private S3StorageManager manager;
    
       
    /**
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @see HttpServlet#HttpServlet()
     */



    public UploadServlet() throws IOException, ClassNotFoundException, SQLException {
        super();
        // TODO Auto-generated constructor stub
      
        
    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		  try {
			  UserMusicUtil.init();
			//  UserUtil.init();
			
		     manager=new S3StorageManager();
			 InputStream stream = null;
			    stream=req.getInputStream();
			    String UUID=req.getParameter("UUID");
			    String musicName=new String(req.getParameter("musicName").getBytes("ISO-8859-1"),"utf-8");
			    String type=new String(req.getParameter("type").getBytes("ISO-8859-1"),"utf-8");
			    String artist=req.getParameter("artist");
			    if(artist==null){
			    	artist="unknown";    	
			    }else{
			    	artist=new String(artist.getBytes("ISO-8859-1"),"utf-8");
			    }
			    String category=req.getParameter("category");
			    if(category==null){
			    	category="MIDI";
			    }else{
			    	category=new String(category.getBytes("ISO-8859-1"),"utf-8");
			    	
			    }  
			    int size=Integer.parseInt(req.getParameter("size"));
			    int row =Integer.parseInt(req.getParameter("row"));
			    MusicItem musicItem=new MusicItem(UUID,artist,musicName,category,type,size,bucketName);	
			    DataInputStream data=new DataInputStream(stream);
			    

			   
			    manager.storePublicRead(musicItem, data, true);
			  
			    try {
					UserMusicUtil.insertItem(musicItem);
				//	UserUtil.insertItem(userItem);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					resp.getWriter().append(e.getMessage());
				}
				//UserUtil.disconnect();
				UserMusicUtil.disconnect();
				JSONArray jsonArray = new JSONArray();
				jsonArray.put(musicItem.josonMap("en",0));
				resp.getOutputStream().write(jsonArray.toString().getBytes());
			    
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			resp.getOutputStream().write("classNotFoundException".getBytes());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			resp.getOutputStream().write("SQLException".getBytes());
		}

 
	}
	
 
}
