package sample;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public TextArea questions;
    public TextArea tickets;
    public javafx.scene.layout.Pane Pane;
    public Spinner countQuestions;
    public Spinner countTickets;

    String questionstext;
    ArrayList <String> questiosArray= new ArrayList <String>();
    ArrayList <String> questiosArray2 = new ArrayList <String>();
    ArrayList <String> ticketsArray=new ArrayList<String>();

    Random ran=new Random();
    int rand=0;
    //Открыть файл
    public void ClickOpen(ActionEvent actionEvent) throws IOException {
        questions.setText("");
        JFileChooser fileopen = new JFileChooser();
        fileopen.setCurrentDirectory(new File (".." ));
        int ret = fileopen.showDialog(null, "Открыть файл");



        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileopen.getSelectedFile();
            questionstext=file.getPath();
            FileInputStream  fr = new FileInputStream(questionstext);
            BufferedReader br = new BufferedReader(new InputStreamReader(fr));
            String str;
            while((str = br.readLine()) !=null) {
                questiosArray.add(str);
                questiosArray2.add(str);
            }
            questiosArray.removeAll(Arrays.asList("", null));
            questiosArray2.removeAll(Arrays.asList("", null));
            SpinnerValueFactory svf=new SpinnerValueFactory.IntegerSpinnerValueFactory(1,questiosArray.size(),1);
            svf.setWrapAround(true);
            countQuestions.setValueFactory(svf);
            SpinnerValueFactory svf1=new SpinnerValueFactory.IntegerSpinnerValueFactory(1,150,1);
            svf1.setWrapAround(true);
            countTickets.setValueFactory(svf1);
            for (int i = 0; i <questiosArray.size(); i++) {
                questions.appendText(questiosArray.get(i)+"\n");
            }


            br.close();
        }
    }
    //Сохарнить в формате doc
    public void ClickSavedoc(ActionEvent actionEvent) {

    }
    //Сохранить в формате pdf
    public void ClickSavepdf(ActionEvent actionEvent) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("../iTextHelloWorld.pdf"));
        document.open();
        int count=0;

        for (int i = 0; i <ticketsArray.size() ; i++) {
            if(ticketsArray.get(i)=="________________________________________________") {
                count++;
                Paragraph p=new Paragraph();
                p.setAlignment(1);
                p.add("question"+count);
                document.add(p);
            }
            Paragraph p = new Paragraph();
            p.add(ticketsArray.get(i));
            document.add(p);
        }
        document.close();





    }
    //Сгенерировать билеты
    public void ClickGen(ActionEvent actionEvent) {
        tickets.setText("");
        ticketsArray.removeAll(ticketsArray);
        int countTicketss=Integer.parseInt(countTickets.getValue().toString());

        while(countTicketss!=0)
        { countTicketss--;
            tickets.appendText("________________________________________________");
            tickets.appendText("\n");
            ticketsArray.add("________________________________________________");
            for (int i = 0; i <Integer.parseInt(countQuestions.getValue().toString()) ; i++)
            {
               rand=ran.nextInt(questiosArray.size());
                tickets.appendText(questiosArray.get(rand)+" "+"\n");
                ticketsArray.add(questiosArray.get(rand));
                questiosArray.remove(rand);
            }
            ticketsArray.add("\n");


            questiosArray.removeAll(questiosArray);
            for (int i = 0; i <questiosArray2.size() ; i++) {
                questiosArray.add(i,questiosArray2.get(i));
            }

        }


        }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}




