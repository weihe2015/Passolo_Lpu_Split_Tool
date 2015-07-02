/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localization;

import java.io.File;
import org.apache.commons.io.FileUtils;


/**
 *
 * @author wei7771
 */
public class split {
    
    public static void splitFile(String filepath){
		String path = filepath;
		String folder = path.substring(0,path.lastIndexOf("\\"));
		String[] files = new String[6];
		if(!path.endsWith(".lpu")){
			return;
		}else{
			
			files[0] = path.substring(0,path.lastIndexOf(".")) + "_ECI.lpu";
			files[1] = path.substring(0,path.lastIndexOf(".")) + "_ECI_th.lpu";
			files[2] = path.substring(0,path.lastIndexOf(".")) + "_AAC.lpu";
			files[3] = path.substring(0,path.lastIndexOf(".")) + "_TOIN.lpu";
			files[4] = path.substring(0,path.lastIndexOf(".")) + "_LION_Self.lpu";
			files[5] = path.substring(0,path.lastIndexOf(".")) + "_LION_main.lpu";
			
			File srcDir = new File(path);
			File trgDir1 = new File(path.substring(0,path.lastIndexOf(".")) + "_ECI.lpu");
			File trgDir2 = new File(path.substring(0,path.lastIndexOf(".")) + "_ECI_th.lpu");
			File trgDir3 = new File(path.substring(0,path.lastIndexOf(".")) + "_AAC.lpu");
			File trgDir4 = new File(path.substring(0,path.lastIndexOf(".")) + "_TOIN.lpu");
			File trgDir5 = new File(path.substring(0,path.lastIndexOf(".")) + "_LION_Self.lpu");
			File trgDir6 = new File(path.substring(0,path.lastIndexOf(".")) + "_LION_main.lpu");
			try{
				FileUtils.copyFile(srcDir, trgDir1);
				FileUtils.copyFile(srcDir, trgDir2);
				FileUtils.copyFile(srcDir, trgDir3);
				FileUtils.copyFile(srcDir, trgDir4);
				FileUtils.copyFile(srcDir, trgDir5);
				FileUtils.copyFile(srcDir, trgDir6);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		String os = System.getProperty("os.arch");
		String passoloPath = "";
		if(os.contains("x86")){
			passoloPath = "\"C:\\Program Files\\SDL Passolo 2011\\pslcmd.exe\"";
		}
		else{
			passoloPath = "\"C:\\Program Files (x86)\\SDL Passolo 2011\\pslcmd.exe\"";
		}	
	 	String logfile = folder + "\\" + path.substring(path.lastIndexOf("\\")+1,path.lastIndexOf(".")) + ".log";
	 	try{
	 		File log = new File(logfile);
		 	if(!log.exists()){
		 		log.createNewFile();
		 	}
	 	}catch(Exception e){
	 		
	 	}
	 		
		for(int i = 0; i < files.length; i++){
				String cmd2 = passoloPath + " /openproject:" + files[i] + " /runmacro=PslLpuSplitter.bas";
				try{
					String osName = System.getProperty("os.name");

					String[] cmd = new String[3];
					if(osName.equals("Windows NT") || (osName.equals("Windows 7"))){
						cmd[0] = "cmd.exe";
						cmd[1] = "/C";
						cmd[2] = cmd2 + " >> " + logfile;
					}
					else if(osName.equals("Windows 95")){
						cmd[0] = "command.com";
						cmd[1] = "/C";
						cmd[2] = cmd2 + " >> " + logfile;
					}	
					System.out.println(cmd[0] + " " + cmd[1] + " " + cmd[2] );
					Runtime rt = Runtime.getRuntime();
					Process proc = rt.exec(cmd);
					StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(),"ERROR");
					StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(),"OUTPUT");
					errorGobbler.start();
					outputGobbler.start();
					int exitVal = proc.waitFor();
		            System.out.println("ExitValue: " + exitVal); 
					
				} catch(Exception e){
					e.printStackTrace();
				}

		}
    }
}
