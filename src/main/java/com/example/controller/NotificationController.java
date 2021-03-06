package com.example.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.NotificationDTO;
import com.example.service.NotificationService;



@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    NotificationService notificationService;


    /**
     * Retourne la liste des notification par utilisateur
     * @param user_id user id
     * @return liste de NotificationDTO
     */
    @RequestMapping(value = "/user/{user_id}", method = RequestMethod.GET)
    public ResponseEntity<List<NotificationDTO>> getListByUser(@PathVariable(value = "user_id") Long user_id) {
        logger.info("Calling NotificationController.getListBoardByUser with {}", user_id);

        List<NotificationDTO> list = notificationService.getNotificationByUserId(user_id);

        if (CollectionUtils.isEmpty(list)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    /**
     * Change le statut de toutes les notifications a "Read" par utilisateur
     * @param user_id : id utilisateur
     */
    @RequestMapping(value = "/user/{user_id}/read", method = RequestMethod.GET)
    public void readAllNotification(@PathVariable(value = "user_id") Long user_id) {
        logger.info("Calling NotificationController.readAllNotification with {}", user_id);
        notificationService.readAllNotification(user_id);
    }
    
    /**
     * Créer Notification
     * @param notifDTO
     * @return notification Dto
     */
    @RequestMapping(method = RequestMethod.POST)
    public NotificationDTO createNotification(@RequestBody NotificationDTO notifDTO) {
        logger.info("Calling NotificationController.createNotification");
        
        return notificationService.createNotification(notifDTO);
    }
    
    

}
