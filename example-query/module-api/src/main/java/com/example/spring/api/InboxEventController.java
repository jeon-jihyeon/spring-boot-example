package com.example.spring.api;

import com.example.spring.common.ResponseModel;
import com.example.spring.domain.InboxQueryService;
import com.example.spring.domain.event.InboxEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InboxEventController {
    private final InboxQueryService inboxService;

    public InboxEventController(InboxQueryService inboxService) {
        this.inboxService = inboxService;
    }

    @PostMapping("/api/events/inbox")
    public ResponseModel<?> create(final @RequestBody InboxEvent event) {
        inboxService.create(event);
        return ResponseModel.ok();
    }
}
