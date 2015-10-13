import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
                                       
public class ThreeAddressFinal extends JFrame
{
   private JLabel label1;
   private JTextField textfield1;
   private JButton button1;
   private JTextArea textarea1;
   private JScrollPane sbrText;
   private ImageIcon image1;
   private JLabel ImageLabel;
   
   public ThreeAddressFinal()
   {
      setLayout(null);
      
      /*image1=new ImageIcon(getClass().getResource("back.jpg"));
      ImageLabel=new JLabel(image1);
      Dimension size = ImageLabel.getPreferredSize();
      ImageLabel.setBounds(0, 0, size.width, size.height);
      add(ImageLabel);*/
      
      label1=new JLabel("Enter The Input FileName = ");
      add(label1);
      label1.setFont(new Font("courier",Font.ITALIC,26));
      //label1.setForeground(Color.GREEN);
      Dimension size = label1.getPreferredSize();
      label1.setBounds(10, 10, size.width, size.height);
      
      
      textfield1=new JTextField(15);
      add(textfield1);
      textfield1.setFont(new Font("courier",Font.ITALIC,29));
      //textfield1.setForeground(Color.ORANGE);
      textfield1.setHorizontalAlignment(JTextField.CENTER);
      size = textfield1.getPreferredSize();
      textfield1.setBounds(432, 10, size.width*2, size.height);
           
      
      button1=new JButton("GENERATE INTERMEDIATE CODE");
      add(button1);
      button1.setFont(new Font("courier",Font.ITALIC,27));
      size = button1.getPreferredSize();
      button1.setBounds(250, 80, size.width, size.height);
      
      
      textarea1 =new JTextArea(14,55);
      textarea1.setFont(new Font("courier",Font.ITALIC,29));
      textarea1.setEditable(false);
      textarea1.setText("Output will be Displayed Here");
      sbrText = new JScrollPane(textarea1);
      add(sbrText);
      size = sbrText.getPreferredSize();
      sbrText.setBounds(25, 150,size.width, size.height);
      //sbrText.setVisible(false);
      
      event e=new event();
      button1.addActionListener(e);
   }//End Constructor
   
   public class event implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         
         String t="";
         int j=0;
         int[] numbers = new int[50];
         for(int i=0;i<50;i++)
         {
            numbers[i]=i+1;
         }
         
         
         //File open
         String x= textfield1.getText();
         try
         {
         File inputFile = new File(x);
         
         Scanner opnScanner = new Scanner(inputFile);
         String input="";
         while(opnScanner.hasNext())
         {
          input = input+opnScanner.nextLine()+"\n";  
         }
         
         String [] lines=input.split("\n");
         for(int i=0;i<lines.length;i++)
         {
            lines[i]=lines[i].trim();
         }
         
         String ForLine="";
         String Result="";
         String r="";
         String cond="";
         String tempFor="t";
         boolean flag=true;
         
         for(int i=0;i<lines.length;i++)
         {
            
            if(lines[i].startsWith("for"))  //For Loop
            {
              ForLine=lines[i];
              String[] ss = ForLine.split("");
              for(int k=0;k<ss.length;k++)//ss1
              {
                  if(ss[k].equals("("))
                  {
                     for(int l=k+1;l<ss.length;l++)
                     {
                        if(ss[l].equals(";"))
                        {
                           break;
                        }
                        else
                        {
                           r=r+ss[l]; //Holds First Part of for loop
                        }
                     }
                  }
              
              }//For  ss1 
                 //r="i=0"
                 String[] rr=r.split("=");
                 t="t"+numbers[j];
                 j++;
                 String xx="\n\t"+t+" = "+rr[1]+"\n\t"+rr[0]+" = "+t;
                 Result +=xx+"\n";    //Added First part of for
                 
                 String[] sss = ForLine.split(";");
                 Result +=" L1 : \n";
                 tempFor ="t"+numbers[j];
                 j++;
                 Result += "\t"+tempFor+" = "+sss[1]+"\n";
                 Result +="\t"+"if "+tempFor+" goto L2"+"\n";
                 Result +="\t"+"goto L3"+"\n";   //L1 complete
                 
                 Result +=" L4 : \n";
                 String sss2=sss[2].substring(0,sss[2].length()-1);
                 String sss3=sss[2].substring(0,sss[2].length()-3);
                 String []sss4=sss2.split("=");
                 t="t"+numbers[j];
                 j++;
                 if(sss2.contains("++")||sss2.contains("--"))
                 {
                  if(sss2.contains("++"))
                     Result +="\t"+t+" = "+sss3+"+1 \n\t"+sss3+" = "+t;
                  else
                     Result +="\t"+t+" = "+sss3+"-1 \n\t"+sss3+" = "+t;   
                 }
                 else
                 {
                  Result +="\t"+t+" = "+sss4[1]+"\n\t"+sss4[0]+" = "+t;
                 }
                 Result +="\n\t"+"goto L1"+"\n";
                 
                 Result +=" L2 : \n";
                 
                 
                 
            }//If end
            else if(lines[i].contains("}") && flag)
            {
             Result +="\t"+"goto L4"+"\n";
             Result +="\n"+" L3 :"; 
             flag=false;
            }
            else if(lines[i].startsWith("#include"))
            {
               Result +=lines[i]+"\n";
            }
            else if(lines[i].contains("main("))
            {
             Result +="Main : \n";   
            }
            else   //x=0 x=a+b
            {
               //For Expression;
               if(!(lines[i].equals("")||lines[i].contains("+")||lines[i].contains("-")||lines[i].contains("*")||lines[i].contains("/")||lines[i].contains("{")||lines[i].contains("}")))
               {
                       String temp="";
                       String rhs="";
                       String lhs="";
                       t="t"+numbers[j];
                       j++;
                       String[] Exp = lines[i].split("");
                       
                       for(int k=0;k<Exp.length;k++)
                       {
                        if(Exp[k].equals("="))
                        {
                           for(int l=k+1;l<Exp.length;l++)
                           {
                               rhs +=Exp[l];
                           }
                        }
                        }
                        for(int k=0;k<Exp.length;k++)
                        {
                           if(Exp[k].equals("="))
                           {
                              break;
                           }
                           else
                           {
                              lhs +=Exp[k];
                           }
                        }
                        
                        temp +="\n\t"+t+" = "+rhs;
                        temp +="\n\t"+lhs+" = "+t;
                        Result +=temp+"\n";
               }//Ends Expression
               
               else if(!(lines[i].equals("") ||lines[i].equals("{")||lines[i].equals("}"))) //mixed
               {
                       String temp="";                    
                       String[] Exp = lines[i].split("");
                       
                       ArrayList<String> Exp0 = new ArrayList<String>();
                       Exp0.add(Exp[0]);
                       Exp0.add(Exp[1]);
                       Exp0.add(Exp[2]);
                       ArrayList<String> Exp1 = new ArrayList<String>();
                       Exp1.add(Exp[0]);
                       Exp1.add(Exp[1]);
                       Exp1.add(Exp[2]);
                       ArrayList<String> Exp2 = new ArrayList<String>();
                       Exp2.add(Exp[0]);
                       Exp2.add(Exp[1]);
                       Exp2.add(Exp[2]);
                  
                     for(int k=3;k<Exp.length;k++)
                     {
                          int l=0;
                          if(Exp[k].equals("("))
                          {
                               t="t"+numbers[j];
                               j++;
                               String y="";
                               for(l=1;l<Exp.length-k;l++)
                               {
                                    if(Exp[k+l].equals(")"))
                                    {
                                       temp +="\n\t"+t+" = "+y;
                                       Exp0.add(t);
                                       break;
                                    }
                                     else
                                     {
                                          y +=Exp[k+l];
                                     }
               
                                 }
                            k=k+l;
                        
                           }//ENDS "("
                      else
                      {
                        Exp0.add(Exp[k]);
                      }
                      
                       //
                  }//Ends For (
                  
                  String [] Exp00 = Exp0.toArray(new String[Exp1.size()]); //After Reducing (
                  //textarea1.setText(Exp11[0]); ==lhs
                  for(int k=3;k<Exp00.length;k++)
                  {
                     if(Exp00[k].equals("-"))
                     {
                           if(Exp00[k-1].equals("+")||Exp00[k-1].equals("-") ||Exp00[k-1].equals("*") || Exp00[k-1].equals("/")||Exp00[k-1].equals("="))
                           {
                               t="t"+numbers[j];
                               j++;
                     
                              temp +="\n\t"+t+" = "+Exp00[k]+Exp00[k+1];
                              Exp1.add(t);
                              Exp00[k]="";
                              Exp00[k+1]=t;
                              k=k+1;
                           }
                           else //- but not unary
                           {  
                              Exp1.add(Exp00[k]);
                           }
                     }// -
                     else
                     {
                        Exp1.add(Exp00[k]);
                     }
                  }//Ends For -
                  
                  String [] Exp11 = Exp1.toArray(new String[Exp1.size()]);//After Reducing UM
                  
                  //For * /
                  
                  for(int k=3;k<Exp11.length;k++)
                  {
                     if(Exp11[k].equals("*") || Exp11[k].equals("/"))
                     {
                        t="t"+numbers[j];
                        j++;
                        temp +="\n\t"+t+" = "+Exp11[k-1]+Exp11[k]+Exp11[k+1];
                        Exp2.add(t);
                        Exp11[k-1]="";
                        Exp11[k]="";
                        Exp11[k+1]=t;
                        k=k+1;
                     }
                     else
                     {
                        if(k!=Exp11.length-1 && (Exp11[k+1].equals("*")||Exp11[k+1].equals("/")))
                        {
                           continue;
                        }
                        else
                        {
                          Exp2.add(Exp11[k]); 
                        }
            
                     }
                  
                  }//Ends * /
                  
                  String [] Exp22 = Exp2.toArray(new String[Exp2.size()]);
                  //For + /
                  for(int k=3;k<Exp22.length;k++)
                  {
                     if(Exp22[k].equals("+") || Exp22[k].equals("-"))
                     {
                         t="t"+numbers[j];
                         j++;   
                         temp +="\n\t"+t+" = "+Exp22[k-1]+Exp22[k]+Exp22[k+1];
                         Exp22[k-1]="";
                         Exp22[k]="";
                         Exp22[k+1]=t;
                     }
                  }////for +-
                  temp +="\n\t"+Exp[1]+" "+Exp[2]+" "+t;
                  Result +=temp+"\n";
                  
               }//Ends Else Mixed
               
            }//Ends Else( Expression or Mixed)
            
           textarea1.setText(Result);
            
         }//Ends For loop Scan
         
         
         
         
         
         }//try
         catch(FileNotFoundException s)
         {
            textarea1.setText("File Not Found");
         }//catch
         sbrText.setVisible(true);    
      }//Actionperformed
   }//Ends event
   
   public static void main(String [] args)
   {
      ThreeAddressFinal gui=new ThreeAddressFinal();
      
           
      gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      gui.setSize(1000,750);
      gui.setVisible(true);
      gui.setResizable(false);
      gui.setTitle("INTERMEDIATE CODE GENERATION");
      gui.setBackground(Color.RED);
   }
   
}//Ends class