package sample;

import com.itextpdf.text.DocumentException;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Controller extends Main implements Initializable {


    public javafx.scene.layout.Pane Pane;
    public TextArea questions;                 //вопросы из файла
    public TextArea tickets;                  //сгенерированные билеты
    public Spinner countQuestions;           //компонент спиннер в котором задается значение количества вопросов в билете
    public Spinner countTickets;            //компонент спиннер в котором задается значение количества билетов
    public TextArea title;                  //заголовок билета



    public  String titleTicket="ticket";
    ArrayList <String> questionsArray= new ArrayList <String>();
    ArrayList <String> questionsArray2 = new ArrayList <String>();
    ArrayList <String> ticketsArray=new ArrayList<String>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setText("ticket ");

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
    public void clickPrint(ActionEvent actionEvent) throws PrinterException, IOException, DocumentException, URISyntaxException {
        PrintTickets.printDocumentWithTickets(ticketsArray,titleTicket);

    }

    /** Метод нажатия на кнопку для ввода заголовка билета     *
     * @param actionEvent
     */
    public void applyTitleBtn(ActionEvent actionEvent) {
        titleTicket=title.getText();
    }
}







