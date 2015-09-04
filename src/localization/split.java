/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localization;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.io.FileUtils;


/**
 *
 * @author wei7771
 */
public class split {
    
    public static Vector<String> readzipfile(String filepath){
        Vector<String> v = new Vector<String>();
        byte[] buffer = new byte[1024];
        String outputFolder = filepath.substring(0,filepath.lastIndexOf("."));
        System.out.println(outputFolder);
        try{
            File folder = new File(outputFolder);
            if(!folder.exists()){
		folder.mkdir();
	  }
                ZipInputStream zis = new ZipInputStream(new FileInputStream(filepath));
		ZipEntry ze = zis.getNextEntry();
                while(ze != null){
                    String fileName = ze.getName();
		    File newFile = new File(outputFolder + "\\" + fileName);
		    v.addElement(newFile.getAbsolutePath());
		    FileOutputStream fos = new FileOutputStream(newFile);
		    int len;
		    while((len = zis.read(buffer)) > 0){
			fos.write(buffer, 0, len);
		     }
                fos.close();
                ze = zis.getNextEntry();
              }	 
	    zis.closeEntry();
	    zis.close();  
        }catch(Exception e){
            
        }
        return v;
    }
    
    public static boolean checkLPU(String filepath,String passoloPath){
        File logfile = new File(filepath.substring(0,filepath.lastIndexOf("\\")+1)+"error.log");
        try{
            String cmd = "cmd.exe /c " +  passoloPath  + " /openproject:" + filepath;
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(cmd);
            InputStreamReader isr = new InputStreamReader(proc.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while((line = br.readLine()) != null){
		if(line.contains("Opening in read-only mode")){
                    if(!logfile.exists()){
                        logfile.createNewFile();
                    }
                    String content = filepath + " is not able to process because it is a type of Passolo 2011. Please process it with Passolo 2011." + "\n";
                    FileWriter fw = new FileWriter(logfile.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(content);
                    bw.close();
                    return false;
		}
            }
            int exitVal = proc.waitFor();
            if(exitVal == 10){
                if(!logfile.exists()){
                    logfile.createNewFile();
                }
                String content = filepath + " is not able to process because it is a type of Passolo 2011. Please process it with Passolo 2015." + "\n";
                FileWriter fw = new FileWriter(logfile.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                return false;
            }
            
        }catch(Exception e){
            try{
                String content = e.getMessage();
                FileWriter fw = new FileWriter(logfile.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
            }catch(Exception e1){
                
            }

            return false;
        }
            return true;
    }
    
    public static boolean splitFile(String filepath, String passoloPath, boolean myesri){
        File log = null;
        try{
            String path = filepath.substring(filepath.lastIndexOf("\\")+1,filepath.length());
		String folder = filepath.substring(0,filepath.lastIndexOf("\\"));
		String[] files = null;
                Vector<String> zFile;
		if(filepath.endsWith(".zip")){
			zFile = readzipfile(filepath);
                        for(String s : zFile){
                            if(s.endsWith(".lpu")){
                                File unzipFolder = new File(s.substring(0,s.lastIndexOf("\\")));
                                splitFile(s,passoloPath,myesri);
                            }
                        }
		}
                else if(!filepath.endsWith(".lpu")){
                    return false;
                }
                else{
                    if(!checkLPU(filepath,passoloPath)){
                        return false;
                    }
                    File ECI = new File(folder + "\\ECI");
                    File AAC = new File(folder + "\\AAC");
                    File TOIN = new File(folder + "\\TOIN");
                    File LOIX = new File(folder + "\\LIOX");
                    if(!ECI.exists()){
                        ECI.mkdir();
                    }
                    if(!AAC.exists()){
                        AAC.mkdir();
                    }
                    if(!TOIN.exists()){
                        TOIN.mkdir();
                    }
                    if(!LOIX.exists()){
                        LOIX.mkdir();
                    }
                    if(myesri == true){
                        files  = new String[4];
                        files[0] = folder + "\\ECI\\" + path.substring(0,path.lastIndexOf(".")) + "_ECI.lpu";
			files[1] = folder + "\\AAC\\" + path.substring(0,path.lastIndexOf(".")) + "_AAC.lpu";
			files[2] = folder + "\\TOIN\\" + path.substring(0,path.lastIndexOf(".")) + "_TOIN.lpu";
			files[3] = folder + "\\LIOX\\" + path.substring(0,path.lastIndexOf(".")) + "_LIOX.lpu";
			
			File srcDir = new File(filepath);
			File trgDir1 = new File(files[0]);
			File trgDir2 = new File(files[1]);
			File trgDir3 = new File(files[2]);
			File trgDir4 = new File(files[3]);
                        try{
				FileUtils.copyFile(srcDir, trgDir1);
				FileUtils.copyFile(srcDir, trgDir2);
				FileUtils.copyFile(srcDir, trgDir3);
				FileUtils.copyFile(srcDir, trgDir4);
			}catch(Exception e){
				e.printStackTrace();
			}
                    }
                    else{
                        files  = new String[6];
                        files[0] = folder + "\\ECI\\" + path.substring(0,path.lastIndexOf(".")) + "_ECI.lpu";
			files[1] = folder + "\\ECI\\" + path.substring(0,path.lastIndexOf(".")) + "_ECI_10.lpu";
			files[2] = folder + "\\AAC\\" + path.substring(0,path.lastIndexOf(".")) + "_AAC.lpu";
			files[3] = folder + "\\TOIN\\" + path.substring(0,path.lastIndexOf(".")) + "_TOIN.lpu";
			files[4] = folder + "\\LIOX\\" + path.substring(0,path.lastIndexOf(".")) + "_LIOX_10.lpu";
			files[5] = folder + "\\LIOX\\" + path.substring(0,path.lastIndexOf(".")) + "_LIOX.lpu";
			
			File srcDir = new File(filepath);
			File trgDir1 = new File(files[0]);
			File trgDir2 = new File(files[1]);
			File trgDir3 = new File(files[2]);
			File trgDir4 = new File(files[3]);
			File trgDir5 = new File(files[4]);
			File trgDir6 = new File(files[5]);
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
		
	 	String logfile = folder + "\\" + path.substring(path.lastIndexOf("\\")+1,path.lastIndexOf(".")) + ".log";
	 	log = new File(logfile);
		if(!log.exists()){
                    log.createNewFile();
		 }

	 		
		for(int i = 0; i < files.length; i++){
                    int exitVal = 0;
                        try{
                            String osName = System.getProperty("os.name");
                            String cmd = "cmd.exe /c " + passoloPath + " /openproject:" + files[i] + " /runmacro=PslLpuSplitter_v3.bas" + " >> " + logfile;
                            System.out.println(cmd);
                            Runtime rt = Runtime.getRuntime();
                            Process proc = rt.exec(cmd);
                            exitVal = proc.waitFor();
                            System.out.println("Exit value: " + exitVal);
                                if(exitVal == 10){
                                    return false;
                                }
                            } catch(Exception e){
				e.printStackTrace();
                            }
                            File lpuFile = new File(files[i]);
                            File logFile = new File(files[i].substring(0,files[i].substring(0, files[i].lastIndexOf("\\")).lastIndexOf("\\")+1) + files[i].substring(files[i].lastIndexOf("\\")+1, files[i].lastIndexOf("."))+".log");
                            if(!lpuFile.exists()){
                                logFile.delete();
                            }
                            File lpuFolder = new File(files[i].substring(0, files[i].lastIndexOf("\\")));
                            if(lpuFolder.list().length == 0){
                                lpuFolder.delete();
                            }
                }// end for loop  
            }
                
        }catch(Exception e){
            try{
                 String content = e.getMessage();
                 FileWriter fw = new FileWriter(log.getAbsoluteFile());
                 BufferedWriter bw = new BufferedWriter(fw);
                 bw.write(content);
                 bw.close(); 
            }catch(Exception e1){
                
            }

            return false;
        }
	return true;
    }
}
