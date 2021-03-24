package com.ween;

import com.ween.entity.Event;
import com.ween.entity.Location;
import com.ween.service.EventService;
import com.ween.service.ParticipantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootTest
class SpringbootMysqlJsonApplicationTests {

	@Autowired
	private EventService eventService;
	@Autowired
	private ParticipantService participantService;

	@Test
	void insert() {
		for (int i = 0; i < 5; i++) {
			Event event=new Event();
			Location location=new Location();
			location.setCity("changsha"+i);
			location.setCountry("China");
			event.setLocation(location);
			eventService.saveOrUpdate(event);
		}

//		Participant participant=new Participant();
//		Ticket ticket=new Ticket();
//		ticket.setPrice("10");
//		ticket.setRegistrationCode("zs10");
//		participant.setTicket(ticket);
//		participant.setEvent(event);
//		participantService.saveOrUpdate(participant);
	}

	@Test
	void testQuery(){
		Pageable page= PageRequest.of(0,5, Sort.by(Sort.Direction.DESC,"id"));
		String eventId="";
		String city="";
		Page<Event> pages=eventService.findAll(eventId,city,page);
		List<Event> eventList=pages.getContent();
		eventList.forEach(System.out::println);
	}
}
