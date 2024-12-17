package com.shaktipravesh.journalDemoApp.scheduler;

import com.shaktipravesh.journalDemoApp.cache.AppCache;
import com.shaktipravesh.journalDemoApp.entity.JournalMongoDBEntry;
import com.shaktipravesh.journalDemoApp.entity.User;
import com.shaktipravesh.journalDemoApp.repository.UserRepositoryImpl;
import com.shaktipravesh.journalDemoApp.service.EmailService;
import com.shaktipravesh.journalDemoApp.service.SentimentAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
@EnableScheduling
public class UserScheduler {

    private static final Logger log = LoggerFactory.getLogger(UserScheduler.class);
    private final EmailService emailService;

    private final UserRepositoryImpl userRepository;

    private final SentimentAnalysisService sentimentAnalysisService;

    private final AppCache appCache;

    public UserScheduler(EmailService emailService, UserRepositoryImpl userRepository, SentimentAnalysisService sentimentAnalysisService, AppCache appCache) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.sentimentAnalysisService = sentimentAnalysisService;
        this.appCache = appCache;
    }

    @Scheduled(cron = "0 * * * * *")
    public void fetchUserNSendSAEmail() {
        log.info("fetchUserNSendSAEmail is called");
        List<User> users = userRepository.getUserForSentimentAnalysis();
        for (User user : users) {
            List<JournalMongoDBEntry> journalMongoDBEntries = user.getEntries();
            List<JournalMongoDBEntry> journalMongoDBEntryList =  journalMongoDBEntries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).collect(Collectors.toList());
            String entry = journalMongoDBEntryList.toString();
            String sentiment = sentimentAnalysisService.getSentiment(entry);
            emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", sentiment);
        }
    }

    @Scheduled(cron = "0 * * * * *")
    public void clearAppCache() {
        log.info("Clearing app cache");
        appCache.init();
    }

}
