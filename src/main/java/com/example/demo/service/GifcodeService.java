package com.example.demo.service;

import com.example.demo.entity.Gifcode;
import com.example.demo.repository.GifcodeDao;
import com.example.demo.repository.GifcodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class GifcodeService {
    private final GifcodeRepository repository;
    private final GifcodeDao dao;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();

    public  String generateUniqueCode(int length) {
        StringBuilder codeBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            codeBuilder.append(CHARACTERS.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
    public Map<String, Double> generateGifcode(int amount, double percent) {
        Map<String, Double> map = new HashMap<>();
        while (map.size() < amount) {
            String gifCode = generateUniqueCode(10);
            map.put(gifCode, percent);
        }
        return map;
    }
    public  Integer createGifcode(String code, Integer remainingUsage, LocalDateTime expirationDate, Integer eventId, Double discountCode) {
        return  repository.saveGifcode(code,remainingUsage,expirationDate,eventId,discountCode);
    }
    public Integer createGifcodes(List<Gifcode> gifCodes){
        return dao.saveGifcodesWithEventTogether(gifCodes);
    }
    public Integer deleteGifcodeByEventId(Integer eventId){
        return repository.deleteGifcodesByEventId(eventId);
    }
    public Double getPercentByGifcode(String gifCode){
        Double result = repository.getDiscountByCode(gifCode);
        System.out.println(result);
        if(result==null){
            return null;
        }
        return result;
    }
    public Integer updateRemainingUsage(String code){
        Integer remainingUsage = repository.getRemainingUsageByCode(code);
        if(remainingUsage!=null){
            if(repository.updateRemainingUsageByCode(remainingUsage-1,code)==1){
                return 1;
            }
            return 0;
        }
        return 0;
    }



}
