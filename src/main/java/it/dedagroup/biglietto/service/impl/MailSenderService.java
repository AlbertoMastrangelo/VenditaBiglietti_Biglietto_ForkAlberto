package it.dedagroup.biglietto.service.impl;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Image;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.qrcode.EncodeHintType;
import com.itextpdf.text.pdf.qrcode.ErrorCorrectionLevel;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@AllArgsConstructor
@NoArgsConstructor
public class MailSenderService {
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean auth;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean starttls;
    public final static char EURO = '\u20ac';

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth",auth);
        props.put("mail.smtp.starttls.enable",starttls);
        return mailSender;
    }
    public void inviaMessaggio(String destinatario, String nome, String cognome){
        MimeMessage message = javaMailSender().createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(destinatario);
            helper.setSubject("Biglietto acquistato!");
            helper.setText("Gentile "+nome+" "+cognome+",\n"+
                    "Grazie per aver scelto di acquistare un biglietto per il nostro evento: Presentazione TicketOne.\n"+
                    "Dettagli dell'acquisto:\n"+
                    "Dettagli dell'evento:\n"+
                    "- Luogo: Via Sandro Sandri, 45\n"+
                    "- Aula: Leonardo\n"+
                    "- Data dell'evento: 2023-12-05\n"+
                    "- Importo del biglietto: 15.00"+EURO+"\n\n"+
                    "Grazie ancora per il tuo interesse e ci auguriamo di darti il benvenuto all'evento.\n\n" +
                    "Cordiali Saluti,\n"+
                    "Ticket-One");
            helper.setFrom(username);
            makePDF(nome,cognome);
            FileSystemResource file = new FileSystemResource(new File("src/main/resources/bigliettiAcquistati/bigliettoAcquistato.pdf"));
            helper.addAttachment("bigliettoAcquistato.pdf",file);
        } catch (MessagingException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Errore nell'inviare un messaggio allegando un file");
        }
        javaMailSender().send(message);

    }

    public void makePDF(String nome, String cognome) {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.H;
        hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        String nomeCognome = nome + " " + cognome;
        String percorsoInput = "bigliettoModify.pdf";
        String percorsoOutput = "bigliettoAcquistato.pdf";

        try {
            InputStream inputStream = new ClassPathResource(percorsoInput).getInputStream();
            PdfReader reader = new PdfReader(inputStream);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("src/main/resources/bigliettiAcquistati/"+percorsoOutput));

            PdfContentByte pdfContentByte = stamper.getOverContent(1);
            pdfContentByte.beginText();
            pdfContentByte.setFontAndSize(BaseFont.createFont(), 72);
            pdfContentByte.setColorFill(BaseColor.BLACK);
            pdfContentByte.setTextMatrix(1500,450);
            pdfContentByte.showText(nome.toUpperCase());
            pdfContentByte.endText();

            pdfContentByte.beginText();
            pdfContentByte.setFontAndSize(BaseFont.createFont(), 72);
            pdfContentByte.setColorFill(BaseColor.BLACK);
            pdfContentByte.setTextMatrix(1500,320);
            pdfContentByte.showText(cognome.toUpperCase());
            pdfContentByte.endText();

            BarcodeQRCode qrCode = new BarcodeQRCode(nomeCognome,500, 500, hints);
            Image image = qrCode.getImage();
            image.setAbsolutePosition(3000, 30);
            pdfContentByte.addImage(image);
            stamper.close();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
