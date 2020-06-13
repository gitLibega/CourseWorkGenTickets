package sample;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/*
 * OpenerQuestionsAndGeneratorTickets- класс, в котоором реализуется открытие файла с вопросами(docx или txt)
 * а также реализован метод генерации билетов
 *
 * Version 1.0
 *
 * Author:Libega Ilya
 */
public class OpenerQuestionsAndGeneratorTickets
{

    public static void openFileWithQuestions(TextArea questions, ArrayList<String> questionsArray, ArrayList<String> questionsArray2,
                                      Spinner countQuestions,Spinner countTickets) throws IOException {
        questions.setText("");

        FileChooser fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
        fileChooser.setTitle("Save Document");//Заголовок диалога
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("allowed files", "*.docx", "*.txt");//Расширение
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(null);


        if (file!=null)
        {


            if(FilenameUtils.isExtension(file.getName(),"txt" )) {// Если файл с вопросами имеет расширение .txt

                String str; //строка из тестового документа
                FileInputStream fr = new FileInputStream( file.getPath());

                BufferedReader br = new BufferedReader(new InputStreamReader(fr));

                while ((str = br.readLine()) != null) {
                    questionsArray.add(str);
                    questionsArray2.add(str);

                }
                br.close();

            }
              else //В противном случае выбран файл с расширением .docx
                {
                    FileInputStream fis = new FileInputStream(file.getAbsolutePath());
                    XWPFDocument document = new XWPFDocument(fis);
                    List<XWPFParagraph> paragraphs = document.getParagraphs();
                    for(XWPFParagraph para:paragraphs)
                    {
                        questionsArray.add(para.getText());
                        questionsArray2.add(para.getText());
                    }
                    fis.close();
                }
            questions.setText("");
            questionsArray.removeAll(Arrays.asList("", null));
            questionsArray2.removeAll(Arrays.asList("", null));
            SpinnerValueFactory svf=new SpinnerValueFactory.IntegerSpinnerValueFactory(1,questionsArray.size(),1);
            svf.setWrapAround(true);
            countQuestions.setValueFactory(svf);
            SpinnerValueFactory svf1=new SpinnerValueFactory.IntegerSpinnerValueFactory(1,150,1);
            svf1.setWrapAround(true);
            countTickets.setValueFactory(svf1);
            for (int i = 0; i <questionsArray.size(); i++) {
                questions.appendText(questionsArray.get(i)+"\n");
            }
            }
        }
        public static void generateExamTickets(TextArea tickets, ArrayList<String> ticketsArray, ArrayList<String> questionsArray,
                                               ArrayList<String> questionsArray2, String titleTicket, Spinner countQuestions,Spinner countTickets)
        {
            if(questionsArray.size()>0) {
                Random ran=new Random();
                int rand=0;
                tickets.setText("");
                ticketsArray.removeAll(ticketsArray);
                int countTicketss = Integer.parseInt(countTickets.getValue().toString());

                while (countTicketss != 0) {
                    countTicketss--;

                    tickets.appendText(titleTicket+" "+ (Integer.parseInt(countTickets.getValue().toString()) - countTicketss));
                    tickets.appendText("\n");
                    tickets.appendText("________________________________________________");
                    tickets.appendText("\n");
                    ticketsArray.add("________________________________________________");
                    for (int i = 0; i < Integer.parseInt(countQuestions.getValue().toString()); i++) {
                        rand = ran.nextInt(questionsArray.size());
                        tickets.appendText(questionsArray.get(rand) + " " + "\n");
                        ticketsArray.add(questionsArray.get(rand));
                        questionsArray.remove(rand);
                    }
                    tickets.appendText("________________________________________________");
                    tickets.appendText("\n");


                    questionsArray.removeAll(questionsArray);
                    for (int i = 0; i < questionsArray2.size(); i++) {
                        questionsArray.add(i, questionsArray2.get(i));
                    }

                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Open questions please");
            }
        }

    }



