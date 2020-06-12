package sample;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;


import org.apache.pdfbox.pdmodel.PDDocument;


import org.docx4j.openpackaging.packages.WordprocessingMLPackage;





import javax.print.PrintService;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;




import java.awt.print.PrinterException;

import java.awt.print.PrinterJob;
import  java.lang.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.io.File;
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
            fileopen.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("allowed files", "doc", "docx", "txt");
            fileopen.addChoosableFileFilter(filter);

            fileopen.setCurrentDirectory(new File(".."));
            int ret = fileopen.showDialog(null, "Открыть файл");


            if (ret == JFileChooser.APPROVE_OPTION) {

                File file = fileopen.getSelectedFile();
                questionstext = file.getPath();
                FileInputStream fr = new FileInputStream(questionstext);

                BufferedReader br = new BufferedReader(new InputStreamReader(fr));
                String str;
                while ((str = br.readLine()) != null) {
                    questiosArray.add(str);
                    questiosArray2.add(str);
                }
                br.close();

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




    }
    //Сохарнить в формате doc
    public void ClickSavedoc(ActionEvent actionEvent) throws IOException {
        if(ticketsArray.size()>0) {
            try{


            FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
            fileChooser.setTitle("Save Document");//Заголовок диалога
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("docx files (*.docx)", "*.docx");//Расширение
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                WordprocessingMLPackage docxFile;
                docxFile=WordprocessingMLPackage.createPackage();

                int count = 0;

                for (int i = 0; i < ticketsArray.size(); i++) {
                    if (ticketsArray.get(i) == "________________________________________________") {
                        count++;
                       docxFile.getMainDocumentPart().addParagraphOfText("ticket" + count);
                    }
                    docxFile.getMainDocumentPart().addParagraphOfText(ticketsArray.get(i));
                }
                File exportFile = new File("../new.docx");
            docxFile.save(exportFile);
            }
            }
            catch(Exception e) {
                    e.printStackTrace();

                }
            }

        else{
            JOptionPane.showMessageDialog(null, "Сгенерируйте пожавуста билеты!");
        }

    }
    //Сохранить в формате pdf
    public void ClickSavepdf(ActionEvent actionEvent) throws IOException, DocumentException {
        if(ticketsArray.size()>0) {
        Document document = new Document();
        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Save Document");//Заголовок диалога
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");//Расширение
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            String File=file.getPath();
            PdfWriter.getInstance(document,new FileOutputStream(File));
            document.open();
            int count=0;

            for (int i = 0; i <ticketsArray.size() ; i++) {
                if(ticketsArray.get(i)=="________________________________________________") {
                    count++;
                    Paragraph p=new Paragraph();
                    p.setAlignment(1);
                    p.add("ticket"+count);
                    document.add(p);
                }

                Paragraph p = new Paragraph();
                p.add(ticketsArray.get(i));
                document.add(p);
            }
            document.close();
        }
        }
        else{
            JOptionPane.showMessageDialog(null, "Сгенерируйте пожавуста билеты!");
        }

    }
    //Сгенерировать билеты
    public void ClickGen(ActionEvent actionEvent) {
        if(questiosArray.size()>0) {
            tickets.setText("");
            ticketsArray.removeAll(ticketsArray);
            int countTicketss = Integer.parseInt(countTickets.getValue().toString());

            while (countTicketss != 0) {
                countTicketss--;

                tickets.appendText("ticket" + (Integer.parseInt(countTickets.getValue().toString()) - countTicketss));
                tickets.appendText("\n");
                tickets.appendText("________________________________________________");
                tickets.appendText("\n");
                ticketsArray.add("________________________________________________");
                for (int i = 0; i < Integer.parseInt(countQuestions.getValue().toString()); i++) {
                    rand = ran.nextInt(questiosArray.size());
                    tickets.appendText(questiosArray.get(rand) + " " + "\n");
                    ticketsArray.add(questiosArray.get(rand));
                    questiosArray.remove(rand);
                }
                tickets.appendText("________________________________________________");
                tickets.appendText("\n");


                questiosArray.removeAll(questiosArray);
                for (int i = 0; i < questiosArray2.size(); i++) {
                    questiosArray.add(i, questiosArray2.get(i));
                }

            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Пожавуста заполните поле с вопросами!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void ClickPrint(ActionEvent actionEvent) throws PrinterException, IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("../printFile.pdf"));
        document.open();
        int count=0;

        for (int i = 0; i <ticketsArray.size() ; i++) {
            if(ticketsArray.get(i)=="________________________________________________") {
                count++;
                Paragraph p=new Paragraph();
                p.setAlignment(1);
                p.add("ticket"+count);
                document.add(p);
            }

            Paragraph p = new Paragraph();
            p.add(ticketsArray.get(i));
            document.add(p);
        }
        document.close();



        printPDF("../printFile.pdf");
        File file=new File("../printFile.pdf");
        file.delete();

    }







    public static void printPDF(String fileName)
            throws IOException, PrinterException {
        PrinterJob job = PrinterJob.getPrinterJob();
        PDDocument doc = PDDocument.load(fileName);
        doc.print(job);
    }


    }







