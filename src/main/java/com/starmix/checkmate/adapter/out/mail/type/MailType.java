package com.starmix.checkmate.adapter.out.mail.type;

import lombok.Getter;

@Getter
public enum MailType {
    PROJECT_INVITE("PROJECT_INVITE", "project-invite", "[checkmate] 프로젝트 초대");

    private final String key;
    private final String template;
    private final String title;

    MailType( String key, String template, String title) {
        this.key = key;
        this.template = template;
        this.title = title;
    }
}