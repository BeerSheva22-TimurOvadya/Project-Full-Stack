package telran.spring.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.spring.exceptions.NotFoundException;
import telran.spring.model.Message;
import telran.spring.service.Sender;

@RestController
@RequestMapping("sender")
@RequiredArgsConstructor
@Slf4j
public class SenderController {
	Map<String, Sender> sendersMap;
	final List<Sender> sendersList;
	final ObjectMapper mapper;

	@PostMapping
	String send(@RequestBody @Valid Message message) {
		log.debug("controller received message {}", message);
		Sender sender = sendersMap.get(message.type);
		String resWrong = message.type + " type not found";
		String res = null;
		if (sender != null) {
			res = sender.send(message);
		} else {
			throw new NotFoundException(resWrong);
		}
		return res;
	}

	@PostConstruct
	void init() {

		sendersMap = sendersList.stream().collect(Collectors.toMap(Sender::getMessageTypeString, s -> s));
		sendersList.forEach(s -> mapper.registerSubtypes(s.getMessageTypeObject()));
		log.info("registred senders: {}", sendersMap.keySet());

	}

	@PreDestroy
	void shutdown() {
		//
		log.info("context closed");
	}
}