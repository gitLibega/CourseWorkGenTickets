package sample;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;


import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;


import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
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
import java.util.*;


public class Controller implements Initializable {


    public javafx.scene.layout.Pane Pane;
    public TextArea questions;                 //вопросы из файла
    public TextArea tickets;                  //сгенерированные билеты
    public Spinner countQuestions;           //компонент спиннер в котором задается значение количества вопросов в билете
    public Spinner countTickets;            //компонент спиннер в котором задается значение количества билетов
    public TextArea title;                  //заголовок билета



    public  String titleTicket;
    ArrayList <String> questionsArray= new ArrayList <String>();
    ArrayList <String> questionsArray2 = new ArrayList <String>();
    ArrayList <String> ticketsArray=new ArrayList<String>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    //Открыть файл

    /** метод нажатия на кнопку для открытия файла
     *
     * @param actionEvent
     * @throws IOException
     */
    public void clickOpenFile(ActionEvent actionEvent) throws IOException {
 OpenerQuestionsAndGeneratorTickets.openFileWithQuestions(questions,questionsArray,questionsArray2,countQuestions,countTickets);
    }

    /** метод нажатия на кнопку для сохранения в формате docx
     *
     * @param actionEvent
     * @throws IOException
     */
    public void clickSaveDocx(ActionEvent actionEvent) throws IOException {
SaverDocxAndPDF.saveInDocxFile(ticketsArray,titleTicket);
    }

    /** метод нажатия на кнопку для сохранения в формате pdf
     *
     * @param actionEvent
     * @throws IOException
     * @throws DocumentException
     */
    public void clickSavePdf(ActionEvent actionEvent) throws IOException, DocumentException {
SaverDocxAndPDF.saveInPdfFile(ticketsArray,titleTicket);

    }
    //Сгенерировать билеты
    public void clickGenTickets(ActionEvent actionEvent) {
        OpenerQuestionsAndGeneratorTickets.generateExamTickets(tickets,ticketsArray,questionsArray,questionsArray2,
                                                                titleTicket,countQuestions,countTickets);
    }


    /** Метод нажатия на кнопку для печати
     * @param actionEvent
     * @throws PrinterException
     * @throws IOException
     * @throws DocumentException
     */
    public void clickPrint(ActionEvent actionEvent) throws PrinterException, IOException, DocumentException {
        PrintTickets.printDocumentWithTickets(ticketsArray,titleTicket);
    }

    /** Метод нажатия на кнопку для ввода заголовка билета     *
     * @param actionEvent
     */
    public void applyTitleBtn(ActionEvent actionEvent) {
        titleTicket=title.getText();
    }
}







