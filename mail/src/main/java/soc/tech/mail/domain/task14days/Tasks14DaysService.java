package soc.tech.mail.domain.task14days;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import soc.tech.mail.config.MailConfig;
import soc.tech.report.domain.devandchangtask.DevAndChangTaskService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@EnableScheduling
@EnableAsync
@Log4j2
public class Tasks14DaysService {

    private final SpringTemplateEngine springTemplateEngine;
 private final DevAndChangTaskService devAndChangTaskService;
    private final MailConfig mailConfig;

    public void sendMailTwoWeeksChangeAndDevTasks(String recipient) {
        List<Tasks14Days> data = tasks14Days();
        MimeMessage message = mailConfig.JavaMailSender().createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariables(Map.of("mailtasks", data));
            String emailContent = springTemplateEngine.process("tasks-two-week", context);

            mimeMessageHelper.setTo(recipient);
            mimeMessageHelper.setSubject("Задачи со сроком сдачи заказчику менее двух недель");
            mimeMessageHelper.setFrom(mailConfig.getUsername());
            mimeMessageHelper.setText(emailContent, true);
            mailConfig.JavaMailSender().send(message);
        } catch (MessagingException m) {
            m.printStackTrace();
        }
    }

    private List<Tasks14Days> tasks14Days() {

        return devAndChangTaskService.tasksForLimit(15, -365).stream()
                .map(i -> new Tasks14Days(i.getId(), i.getName(), i.getStatus(), i.getExecutor(), i.getPriority(), i.getStartDate(), i.getDeadline()))
                .collect(Collectors.toList());
    }

    @Scheduled(cron = "${mail.cron}")
    @Async
    public void sendToRecipients() {
        log.info("recipients: " + mailConfig.getUsernameTo1() + "; " + mailConfig.getUsernameTo2()
                + "; " + mailConfig.getUsernameTo3());
        sendMailTwoWeeksChangeAndDevTasks(mailConfig.getUsernameTo1());
        sendMailTwoWeeksChangeAndDevTasks(mailConfig.getUsernameTo2());
        sendMailTwoWeeksChangeAndDevTasks(mailConfig.getUsernameTo3());
        sendMailTwoWeeksChangeAndDevTasks(mailConfig.getUsernameTo4());
    }
}
