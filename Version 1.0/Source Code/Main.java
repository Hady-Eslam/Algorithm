package algorithm_project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Main extends JFrame{
    
    private JPanel PTitle,PSelect,PInPut,PTime,PGO,PShow;
    private JLabel Title,Select,Input,Show,Time;
    private JTextField InPut;
    private JComboBox CSelect;
    private JButton GO;
    private Box box;
    
    private ArrayList<Integer> Data=new ArrayList<>();
    private final String Input_File_Path="E://InPutFile.txt"
            ,OutPut_File_Path="E://OutPutFile.txt";
    
    public Main(){
        
        SetFrameAndPanels();
        SetComponents();
        AddComponentsToPanels();
    }

    private void SetFrameAndPanels() {
    
        this.setTitle("My Project");
        this.setSize( 300 , 300 );
        this.setResizable(false);
        this.setLocation( 530 , 230 );
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        
        PTitle=new JPanel();
        PTitle.setBorder(new EmptyBorder(5,5,5,5));
        
        PSelect=new JPanel();
        
        PInPut=new JPanel();
        
        PTime=new JPanel();
        
        PGO=new JPanel();
        
        PShow=new JPanel();
        
        box=Box.createVerticalBox();
        box.setBorder(new EmptyBorder(10,10,10,10));
    }
    private void SetComponents() {
    
        Title=new JLabel();
        Title.setText("Hello From Algo Project");
        
        Select=new JLabel();
        Select.setText("Select Option :- ");
        
        CSelect=new JComboBox();
        CSelect.setMaximumRowCount(3);
        CSelect.addItem("Read And Write To File");
        CSelect.addItem("Binary Search");
        CSelect.addItem("Merge Sort");
        CSelect.setSelectedIndex(-1);
        
        Input=new JLabel();
        Input.setText("Input :- ");
        
        InPut=new JTextField();
        InPut.setColumns(6);
        InPut.setHorizontalAlignment(JTextField.CENTER);
        InPut.setBorder(new EmptyBorder(3,3,3,3));
        InPut.setForeground(Color.red);
        
        Time=new JLabel();
        Time.setText("Time :- ");
        
        GO=new JButton();
        GO.setText("GO");
        GO.addActionListener(new GOAction());
        
        Show=new JLabel();
        Show.setText("No Operation Done");
    }
    private void AddComponentsToPanels() {
    
        PTitle.add(Title);
        
        PSelect.add(Select);
        PSelect.add(CSelect);
        
        PInPut.add(Input);
        PInPut.add(InPut);
        
        PTime.add(Time);
        
        PGO.add(GO);
        
        PShow.add(Show);
        
        box.add(PSelect);
        box.add(PInPut);
        box.add(PTime);
        box.add(PGO);
        box.add(PShow);
        
        this.add(PTitle,BorderLayout.NORTH);
        this.add(box,BorderLayout.CENTER);
    }
    
    private void SetRes(String Text){
        Show.setText( "<html><body><p "+Text+"</p></body></html>" );
        Data.clear();
    }
    private void SetTime(long time,String Text){
        Time.setText("<html><body><p>Time :- &nbsp;<span style=\"color: #1D9701\">"+time+"&nbsp;</span>\n" +
                           "<span style=\"color: #CF0D00\">"+Text+"</span></p></body></html>");
    }
    
    private class GOAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
        
            if ( CSelect.getSelectedIndex()==-1 ){
                SetRes("style=\"color: #8E9404\">Must Operation Selected");
                return ;
            }
            
            if ( CSelect.getSelectedIndex()==0 ){
                long Start = System.currentTimeMillis();
                if ( Read_From_File()&&Write_Into_File() ){
                    
                    Long End = System.currentTimeMillis();
                    
                    SetTime( (End-Start),"MilliSecond" );
                    SetRes("style=\"color: green\">The Operation Is Done");
                    return ;    
                }
            }
            else if ( CSelect.getSelectedIndex()==1 ){
                if ( InPut.getText().length()==0 ){
                    SetRes("style=\"color: red\">Please Enter The Target");
                    return ;
                }
                else if ( Read_From_File() ){
                    Merge_Sort( 0, Data.size()-1 );
                    
                    long Start = System.nanoTime();
                    int res=Binary_Search( 0,Data.size()-1,Integer.valueOf( InPut.getText()) );
                    long End = System.nanoTime();
                    
                    if ( Write_Res(res) ){
                        SetTime( (End-Start),"NanoSecond" );
                        SetRes("style=\"color: green\">The Operation Is Done");
                        return ;
                    }
                }
            }
            else{
                if ( Read_From_File() ){
                    
                    long Start = System.nanoTime();
                    Merge_Sort( 0, Data.size()-1 );
                    long End = System.nanoTime();
                    
                    if ( Write_Into_File() ){
                        SetTime( (End-Start),"NanoSecond" );
                        SetRes("style=\"color: green\">The Operation Is Done");
                        return ;
                    }
                }
            }
            SetRes("style=\"color: red\">SomeThing Goes Wrong In the Operation");
        }

        private boolean Read_From_File() {
        
            try{
                File file=new File( Input_File_Path );
                Scanner out=new Scanner( file );
                
                while(out.hasNext()){
                    Data.add( Integer.valueOf( out.next() ) );
                }
                out.close();
                return true;
            }
            catch(Exception E){
                JOptionPane.showMessageDialog(null,"Please Check The InPut File Path");
            }
            return false;
        }
        private boolean Write_Into_File() {
        
            try{
                File file=new File( OutPut_File_Path );
                if (!file.exists()){
                    JOptionPane.showMessageDialog(null,"Please Check The OutPut File Path");
                    return false;
                }
                
                file.delete();
                file.createNewFile();
                
                PrintWriter in=new PrintWriter( file );
                
                for (int i=0;i<Data.size();i++)
                    in.println( Data.get(i) );
                in.close();
                return true;
            }
            catch(Exception E){
                JOptionPane.showMessageDialog(null,"Please Enter The Right OutPut File Path");
            }
            return false;
        }
        
        private int Binary_Search(int Start,int End,int Target) {
        
            int Mid;
            
            while(Start<=End){
                Mid=(Start+End)/2;
                if ( Data.get(Mid)==Target )
                    return Mid;
                else if ( Data.get(Mid)>Target )
                    return Binary_Search(Start,Mid-1,Target);
                else
                    return Binary_Search(Mid+1,End,Target);
            }
            return -1;
        }
        private boolean Write_Res(int res){
            
            try{
                File file=new File( OutPut_File_Path );
                if ( !file.exists() ){
                    JOptionPane.showMessageDialog(null,"Please Check The OutPut File Path");
                    return false;
                }
                file.delete();
                file.createNewFile();

                PrintWriter in=new PrintWriter( file );
                in.println(res);
                in.close();
                return true;
            }
            catch(Exception E){
                JOptionPane.showMessageDialog(null,"Please Enter The Right OutPut File Path");
            }
            return false;
        }
        
        private void Merge_Sort(int Left,int Right){
            
            if ( Left<Right ){
                int Mid=(Left+Right)/2;
                
                Merge_Sort( Left,Mid );
                Merge_Sort( Mid+1,Right );
                
                Merge( Left,Mid,Right );
            }
        }
        private void Merge(int Left,int Mid,int Right) {
        
            int n1=Mid-Left+1,
                n2=Right-Mid;
            int L[]=new int[n1],R[]=new int[n2];
            
            for (int i=0;i<n1;i++)
                L[i]=Data.get( Left+i );
            for (int i=0;i<n2;i++)
                R[i]=Data.get( Mid+1+i );
            
            int i=0,j=0,k=Left;
            while( i<n1&&j<n2 ){
                
                if ( L[i] <= R[j] ){
                    Data.set( k , L[i] );
                    i++;
                }
                else{
                    Data.set( k , R[j] );
                    j++;
                }
                k++;
            }
            
            while( i<n1 ){
                Data.set( k , L[i] );
                i++;
                k++;
            }
            while( j<n2 ){
                Data.set(  k , R[j] );
                j++;
                k++;
            }
        }
    }
}