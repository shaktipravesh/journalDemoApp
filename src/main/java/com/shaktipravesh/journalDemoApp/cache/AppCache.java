package com.shaktipravesh.journalDemoApp.cache;

import com.shaktipravesh.journalDemoApp.entity.ConfigJournalAppEntity;
import com.shaktipravesh.journalDemoApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Component
public class AppCache {

    final ConfigJournalAppRepository configJournalAppRepository;

    public Map<String, String> appCache;

    public AppCache(ConfigJournalAppRepository configJournalAppRepository) {
        this.configJournalAppRepository = configJournalAppRepository;
    }

    @PostConstruct
    public void init() {
        appCache = new HashMap<>();
        List<ConfigJournalAppEntity> all  = configJournalAppRepository.findAll();
        for (ConfigJournalAppEntity app : all) {
            appCache.put(app.getKey(), app.getValue());
        }
    }
}
