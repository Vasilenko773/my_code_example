package soc.tech.mail.domain.incifents;

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
import soc.tech.report.domain.incident.Incident;
import soc.tech.report.domain.incident.IncidentService;

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
public class OverdueIncidentsService {

    private final SpringTemplateEngine springTemplateEngine;
    private final MailConfig mailConfig;
    private final IncidentService incidentService;


    public void sendMailOverdueIncidents(String recipient) {
        List<OverdueIncident> overdueIncidents = overdueIncidents();
        MimeMessage message = mailConfig.JavaMailSender().createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariables(Map.of("incidents", overdueIncidents));
            String emailContent = springTemplateEngine.process("overdueincidents/overdue-incident", context);

            mimeMessageHelper.setTo(recipient);
            mimeMessageHelper.setSubject("Просроченные инциденты");
            mimeMessageHelper.setFrom(mailConfig.getUsername());
            mimeMessageHelper.setText(emailContent, true);
            mailConfig.JavaMailSender().send(message);
        } catch (MessagingException m) {
            m.printStackTrace();
        }
    }

    private List<OverdueIncident> overdueIncidents() {
        List<Incident> data = incidentService.overdueDb();
        return data.stream()
                .map(i -> new OverdueIncident(i.getId(), i.getName(),i.getProject(),
                        i.getStatus(), i.getPriority(), i.getExecutor(), i.getStartDate(), i.getDueDate()))
                .collect(Collectors.toList());
    }


    @Scheduled(cron = "${mail.cron}")
    @Async
    public void sendToRecipientsOverdueIncidents() {
        log.info("recipients: " + mailConfig.getUsernameTo1() + "; " + mailConfig.getUsernameTo2()
                + "; " + mailConfig.getUsernameTo3());
        sendMailOverdueIncidents(mailConfig.getUsernameTo1());
        sendMailOverdueIncidents(mailConfig.getUsernameTo2());
        sendMailOverdueIncidents(mailConfig.getUsernameTo3());
        sendMailOverdueIncidents(mailConfig.getUsernameTo4());
    }
}
