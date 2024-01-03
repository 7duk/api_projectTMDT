package com.example.demo.service;

import com.example.demo.dto.event.EventDto;
import com.example.demo.dto.event.EventSaveDto;
import com.example.demo.entity.Event;
import com.example.demo.entity.Gifcode;
import com.example.demo.repository.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
@RequiredArgsConstructor
public class EventService {
    private final EventRepository repository;
    private final ItemRepository itemRepository;
    private final  GifcodeService service;
    private final UserDao dao;
    @Autowired
    private JavaMailSender mailSender;
    private Integer createEvent(EventSaveDto dto){
        try{
            LocalDateTime startDate = LocalDateTime.parse(dto.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime endDate = LocalDateTime.parse(dto.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                if(repository.saveEvent(dto.getTitle(), dto.getImage(),dto.getDescription(),startDate,endDate)==1){
                    return 1;
                }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return 0;
    }
    public Integer createEventWithGifcodePublic(EventSaveDto dto){
        if(!isValidDate(dto.getStartDate(), dto.getEndDate())){
            return 2;
        }
        Map<String,Double> gifCodes = dto.getGiftCodes();
        Set<String> keys = gifCodes.keySet();
        String gifCode = keys.stream().toList().get(0);
        Double discountCode = gifCodes.get(gifCode);
        LocalDateTime expirationDate = LocalDateTime.parse(dto.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if(createEvent(dto)==1){
            Event event = repository.getEventByTilleAndDescription(dto.getTitle() ,dto.getDescription()).orElseThrow(()->new NullPointerException("EVENT IS NOT EXIST!"));
            if(service.createGifcode(gifCode,dto.getRemainingUsage(),expirationDate,event.getId(),discountCode)==1){
                return 1;
            }
        }
        return 0;
    }
    public Integer createEventWithGifcodePrivate(EventSaveDto dto) throws MessagingException, UnsupportedEncodingException {
        if(!isValidDate(dto.getStartDate(), dto.getEndDate())){
            return 2;
        }
        List<Gifcode> gifcodeList = new ArrayList<>();
        Map<String,Double> gifCodes = dto.getGiftCodes();
        Set<String> keys = gifCodes.keySet();
        LocalDateTime expirationDate = LocalDateTime.parse(dto.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if(createEvent(dto)==1){
            System.out.println("1");
            Event event = repository.getEventByTilleAndDescription(dto.getTitle() ,dto.getDescription()).orElseThrow(()->new NullPointerException("EVENT IS NOT EXIST!"));
            System.out.println("3");
            keys.forEach((e)->{
                Gifcode gifcode = Gifcode.builder().code(e).remainingUsage(1).expirationDate(expirationDate).eventId(event.getId()).discountCode(gifCodes.get(e)).build();
                gifcodeList.add(gifcode);
            });
            if(service.createGifcodes(gifcodeList)==1){
                System.out.println("2");
                List<String> topRatingOrderEmails = dao.getEmailsTopRatingOrder(keys.size()).stream().map(UserDao.ResultObject::getEmail).toList();
                for(int i = 0;i<topRatingOrderEmails.size();i++){
                    sendEmailGiveawayGifcode(topRatingOrderEmails.get(i),gifcodeList.get(i).getCode(),event);
                }
                return 1;
            }
        }
        return 0;
    }
    private void sendEmailGiveawayGifcode(String mailToAddress,String code,Event event)
            throws MessagingException, UnsupportedEncodingException {
        String fromAddress = "shopiecommerce2023@gmail.com";
        String senderName = "Admin Shopiec";
        String subject =event.getTitle();
        String content = "<div style='font-family: Arial, sans-serif;'>" +
                "<h2 style='color: #3498db;'>" + event.getTitle() + "</h2>" +
                "<div style='text-align: center;'>" +
                "<img src='" + event.getImage() + "' style='max-width: 100%; height: auto;'/>" +
                "</div>" +
                "<p style='color: #555;'>" + event.getDescription() + "</p>" +
                "<br>" +
                "<p style='color: #555;'>Here is the code we have for you:<strong style='font-size: 18px; font-weight: bold;'>" + code + "</strong></p>" +
                "<br>" +
                "<p style='color: #3498db;'>Thank you!</p>" +
                "</div>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(fromAddress, senderName);
        helper.setTo(mailToAddress);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }
    public Integer createEventDecreasePrice(EventSaveDto dto) {
        if(!isValidDate(dto.getStartDate(), dto.getEndDate())){
            return 2;
        }
        Map<Integer,Integer> itemGroupIdWithDiscounts = dto.getItemGroupIds();
        List<Integer> itemGroupID = itemGroupIdWithDiscounts.keySet().stream().toList();
        if(createEvent(dto)==1){
            for (Integer integer : itemGroupID) {
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime lastUpdateAt = LocalDateTime.parse(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                itemRepository.updateDiscountItemsWithItemGroupId(integer, itemGroupIdWithDiscounts.get(integer), lastUpdateAt) ;
                return 1;
            }
        }
        return  0;
    }
    public List<EventDto> getAllExpiredEvent(){
        LocalDateTime now = LocalDateTime.now();
        List<Event> events = repository.findAll();
        List<Event> expiredEvents =events.stream().filter(e-> e.getStartDate().isBefore(now) && e.getEndDate().isAfter(now)).toList();
        return expiredEvents.stream().map(e->  EventDto.builder().id(e.getId()).title(e.getTitle())
                .image(e.getImage()).description(e.getDescription())
                .startDate(e.getStartDate().format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy")))
                .endDate(e.getEndDate().format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy"))).build()).toList();
    }
    public List<EventDto> getAllEvents() {
        List<Event> events = repository.findAll();
        return events.stream().map(e->  EventDto.builder().id(e.getId()).title(e.getTitle())
                .image(e.getImage()).description(e.getDescription())
                .startDate(e.getStartDate().format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy")))
                .endDate(e.getEndDate().format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy"))).build()).toList();
    }
    public Integer deleleEventById(Integer id){
        if(service.deleteGifcodeByEventId(id)>=1){
            try{
                repository.deleteById(id);
                return 1;
            }
            catch (Exception e){
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }
    private Boolean isValidDate(String startDate,String endDate){
        LocalDateTime start = LocalDateTime.parse(startDate,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime end = LocalDateTime.parse(endDate,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if(start.isBefore(end)){
            return true;
        }
        return false;
    }
}
