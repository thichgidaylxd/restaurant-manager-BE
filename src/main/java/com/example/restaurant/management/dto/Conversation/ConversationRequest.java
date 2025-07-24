package com.example.restaurant.management.dto.Conversation;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ConversationRequest {
    UUID userAccountId1;
    UUID userAccountId2;
    String title;
}
