package com.shaktipravesh.journalDemoApp.entity;

import com.shaktipravesh.journalDemoApp.enums.Sentiment;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Document(collection = "mongoDBEntry")
@Data
@NoArgsConstructor
public class JournalMongoDBEntry {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
    private Sentiment sentimentAnalysis;
}
