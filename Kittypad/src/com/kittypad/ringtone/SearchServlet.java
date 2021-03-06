/**
 * @author mingzhu
 * this servlet is used for search music under a certain condition
 * 
 */
package com.kittypad.ringtone;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.util.json.JSONArray;
import com.kittypad.ringtone.util.MusicItem;
import com.kittypad.ringtone.util.MusicUtil;

/**
 * Servlet implementation class SearchServlet
 */
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() throws ClassNotFoundException, SQLException {
        super();
        // TODO Auto-generated constructor stub
       
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 try {
			MusicUtil.init();
			String key = req.getParameter("q");
			if(key!=null)
				key=new String(key.getBytes("ISO-8859-1"),"utf-8");
			String startStr = req.getParameter("start");
			String type = req.getParameter("type");
			String platform = req.getParameter("platform");
			if(platform == null){
				platform = "android";
				
			}
			int start = 0;
			if (startStr != null) {
				start = Integer.parseInt(startStr);
			}
			JSONArray jsonArray = new JSONArray();
		
				List<MusicItem> searchResults = null;
		   try{
			    
				if(type == null || type.equals("keyword")){
					searchResults =MusicUtil.getResultsByKeyword(platform,key, start);
				}
				else if(type.equals("category")){
					searchResults = MusicUtil.getResultsByCategory(platform,key, start);
				}
				else if(type.equals("download_count")){
					searchResults = MusicUtil.getResultsByDownloadCount(platform,start);
				}
				else if(type.equals("add_date")){
					searchResults = MusicUtil.getResultsByDate(platform,start);
				}
				else if(type.equals("artist")){
					searchResults = MusicUtil.getResultsByArtist(platform,key, start);
				}
				else if(type.equals("random")) {
					searchResults =MusicUtil.getResultsByRandom(platform);
				}
			
				for (MusicItem musicItem : searchResults) {
					Map<String, String> musicMap=musicItem.josonMap();
					jsonArray.put(musicMap);
				}
			String response = null;
			response = jsonArray.toString();
			resp.getOutputStream().write(response.getBytes());
			resp.flushBuffer();
		   }catch(Exception e){
			   e.printStackTrace(resp.getWriter());
			  // e.printStackTrace( resp.getOutputStream());
			   resp.flushBuffer();
			
		   }
		   MusicUtil.disconnect();
		 
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			resp.getOutputStream().write(e1.getMessage().getBytes());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			resp.getOutputStream().write(e1.getMessage().getBytes());
		}
		
		
	}

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
