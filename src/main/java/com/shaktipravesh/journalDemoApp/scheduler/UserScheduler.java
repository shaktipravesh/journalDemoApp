package com.shaktipravesh.journalDemoApp.scheduler;

import com.shaktipravesh.journalDemoApp.cache.AppCache;
import com.shaktipravesh.journalDemoApp.entity.JournalMongoDBEntry;
import com.shaktipravesh.journalDemoApp.entity.User;
import com.shaktipravesh.journalDemoApp.enums.Sentiment;
import com.shaktipravesh.journalDemoApp.repository.UserRepositoryImpl;
import com.shaktipravesh.journalDemoApp.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@EnableScheduling
public class UserScheduler {

    private static final Logger log = LoggerFactory.getLogger(UserScheduler.class);
    private final EmailService emailService;

    private final UserRepositoryImpl userRepository;

    private final AppCache appCache;

    public UserScheduler(EmailService emailService, UserRepositoryImpl userRepository, AppCache appCache) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.appCache = appCache;
    }

    @Scheduled(cron = "0 0 * * * Sun")
    public boolean fetchUserNSendSAEmail() {
        List<User> users = userRepository.getUserForSentimentAnalysis();
        for (User user : users) {
            List<JournalMongoDBEntry> journalMongoDBEntries = user.getEntries();
            List<Sentiment> sentiments =  journalMongoDBEntries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minusDays(30))).map(JournalMongoDBEntry::getSentimentAnalysis).toList();
            Map<Sentiment, Integer> sentimentIntegerMap = new HashMap<>();
            for(Sentiment sentiment : sentiments) {
                if(sentiment != null) {
                    sentimentIntegerMap.put(sentiment, sentimentIntegerMap.getOrDefault(sentiment, 0) + 1);
                }
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for(Sentiment sentiment : sentimentIntegerMap.keySet()) {
                if(sentimentIntegerMap.get(sentiment) > maxCount) {
                    maxCount = sentimentIntegerMap.get(sentiment);
                    mostFrequentSentiment = sentiment;
                }
            }
            if(mostFrequentSentiment != null) {
                emailService.sendEmail(user.getEmail(), "Sentiment for last 30 days.", mostFrequentSentiment.toString());
                return true;
            }
        }
        return false;
    }

    @Scheduled(cron = "0 0 */6 * * *")
    public void clearAppCache() {
        log.info("Clearing app cache");
        appCache.init();
    }

}
