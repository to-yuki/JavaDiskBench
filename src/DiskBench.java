import java.io.*;
import java.util.Random;
import java.nio.file.Files;

class DiskBench {
  
  public static void main(String args[]){
      
      
    if(args.length < 1){
        System.err.println("usage : java DiskBench [FileName] [512kWriteCount (Drfault:25000/2.5G)]");
        System.err.println("This tool writes 512k of data.");
        System.err.println("The default is 2.5 G capacity.");
        System.exit(-1);
    }
  
    char c = 'あ';
    
    int i = 51;
    long size = 25000; //最大書き込み回数
    if(args.length == 2){
      try {
        size = Integer.parseInt(args[1]);
      }catch(Exception e) {
        System.err.println("Input Number");
        System.exit(-2);
      }
    }
    
    long count = 0;
    char[] buf = new char[32768]; //1ループ書き込みデータ
     Random rnd = new Random();
     
     long start = System.currentTimeMillis();
     System.out.println("Write Start!");
     File file = new File(args[0]);
     
     try(FileOutputStream os2 = new FileOutputStream(file)){
         OutputStreamWriter ow2 = new OutputStreamWriter(os2,"UTF-8");
         while(true){
                for(int l = 0;l < buf.length;l++){
                    buf[l] = ((char)(rnd.nextInt(i)+c));
                }
                count++;
                ow2.write(buf,0,buf.length);
                ow2.flush();
                if(count==size)
                    break;
            }
     }catch (Exception e){
            e.printStackTrace();
     }
     
     long end = System.currentTimeMillis();
     
     System.out.println("Write End!");
     
     long filesize = file.length() / 1024/1024;
     System.out.println("WriteFileSize " + filesize + " MByte");
     
     double sec = (end-start)/1000.0;
     System.out.println("WriteTime "+ sec + " Sec");
     
     double avg = (file.length() / 1024 / 1024) / sec;
     
     System.out.println("WriteAvg " + avg + " Mbyte/Sec");
     
     try{
       Files.delete(file.toPath());
     }catch (Exception e){
            e.printStackTrace();
     }
     
  }
}