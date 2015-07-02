/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localization;

import java.io.*;

/**
 *
 * @author wei7771
 */
public class StreamGobbler extends Thread {
    	InputStream is;
	String type;
	
	StreamGobbler(InputStream is, String type){
		this.is = is;
		this.type = type;
	}
	
	public void run(){
		try{	
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while((line = br.readLine()) != null){
				if(line.equals("Cannot read license information.")){
					
				}
				System.out.println(type + "< " + line + ">");
			}	
		}catch(IOException e){
			
		}
	}
}
