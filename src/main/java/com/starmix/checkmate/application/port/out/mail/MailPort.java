package com.starmix.checkmate.application.port.out.mail;

import com.starmix.checkmate.adapter.out.mail.type.MailType;
import org.thymeleaf.context.Context;

public interface MailPort {
    void send(String address, MailType mailType, Context context);
}
