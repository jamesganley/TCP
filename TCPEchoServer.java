/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.net.*;
import java.util.*;
/**
 * @author JGanley
 */
public class TCPEchoServer
{
  private static ServerSocket servSock;
  private static final int PORT = 1234;

  public static void main(String[] args) {
    System.out.println("Opening port...\n");
    try
    {
        servSock = new ServerSocket(PORT);      //Step 1.
    }catch(IOException e)
    {
         System.out.println("Unable to attach to port!");
         System.exit(1);
    }
    do {
         run();
    }while (true);

  }

  private static void run()
  {
    Socket link = null;                        //Step 2.
    try
    {
      link = servSock.accept();               //Step 2.
      BufferedReader in = new BufferedReader( new InputStreamReader(link.getInputStream())); //Step 3.
      PrintWriter out = new PrintWriter(link.getOutputStream(),true); //Step 3.

      int numMessages = 0;
      String message = in.readLine();         //Step 4.
      while (!message.equalsIgnoreCase("stop"))
      {


        System.out.println("Message received: " + message);
        numMessages++;

        int pTotal = 1;
        int total = 0;

        List<Integer> mList = new ArrayList<Integer>();
        if(message.matches(".*\\d.*") && message.matches(".*[-+/*].*")){
        for(String numbers: message.split("[^0-9]+")){
          if(!numbers.isEmpty()){
            mList.add(Integer.parseInt(numbers));
          }
        }
      //  System.out.println(mList);



         for (int i=0; i<message.length() ;i++ ) {
           char a = message.charAt(i);

           if(a == '+'){
             System.out.println(a);
             for (int j=0;j<mList.size() ;j++ ) {
               total += mList.get(j);
             }
             out.println("Sum = " + total);
           }
           else if(a == '-'){
             System.out.println(a);

             int fnum = mList.get(0);
             int lnum = mList.get(1);

             total = (fnum - lnum);
             out.println("Sum = " + total);
           }
           else if(a == '*'){
             System.out.println(a);
             for (int j=0;j<mList.size() ;j++ ) {
               pTotal *= mList.get(j);
           }
           out.println("Sum = " + pTotal);
         }
           else if(a == '/'){
             System.out.println(a);
             int fnum = mList.get(0);
             int lnum = mList.get(1);
             total = (fnum / lnum);
             out.println("Sum = " + total);
           }
           else{
             continue;
           }
         }
       }

       else{
         out.println("That input is invalid");
       }

         message = in.readLine();

      }
      out.println(" A Total of "+ numMessages+ " messages were received.");	//Step 4.
    }
    catch(IOException e)
    {
        e.printStackTrace();
    }
    finally
    {
       try {
	    System.out.println("\n* Closing connection... *");
            link.close();				    //Step 5.
	}
       catch(IOException e)
       {
            System.out.println("Unable to disconnect!");
	    System.exit(1);
       }
    }
  } // finish run method
} // finish the class
