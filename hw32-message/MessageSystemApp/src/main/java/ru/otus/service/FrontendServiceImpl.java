package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.otus.constants.Constants;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.web.dto.ClientDto;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FrontendServiceImpl<T extends ResultDataType> implements FrontendService {

    @Qualifier(Constants.RESPONSE_CLIENT_NAME)
    private final MsClient responseClient;
    private final SimpCallbackService<T> callbackService;

    @Override
    public void saveUserData(ClientDto clientDto) {
        var outMsg = responseClient.produceMessage(Constants.DATABASE_CLIENT_NAME, Collections.singletonList(clientDto),
                MessageType.SAVE_USER, (data) -> callbackService.callbackForBatch((List<T>) data));
        responseClient.sendMessage(outMsg);
    }

    @Override
    public void getAll() {
        var outMsg = responseClient.produceMessage(Constants.DATABASE_CLIENT_NAME, null,
                MessageType.GET_USERS, (data) -> callbackService.callbackForBatch((List<T>) data));
        responseClient.sendMessage(outMsg);
    }
}
