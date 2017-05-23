package com.example.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.dto.ItemDto;
import com.example.model.Item;
import com.example.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.ConstanteGameMaster;
import com.example.dto.TagDTO;
import com.example.dto.UserDTO;
import com.example.exception.GameMasterException;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import com.example.transformers.Transformers;

/**
 * Created by Morgane TROYSI on 16/05/17.
 */

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private Transformers transformers;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    /**
     * Retourne les utilisateurs dont le nom + prénom contiennent la chaîne en paramètre, utilisateur connecté exclu
     * @param query chaîne à chercher
     * @return liste des utilisateurs dont le nom + prénom contiennent la chaîne en paramètre, utilisateur connecté exclu
     */
    @Override
    public List<UserDTO> loadUsers(String query) {
        User currentUser = this.getCurrentUser();
        List<User> users = userRepository.findByFirstNameContainingOrLastNameContainingAndEmailNot(query, currentUser.getEmail());
        List<UserDTO> result = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = transformers.transformUserToUserDto(user);
            result.add(userDTO);
        }
        return result;
    }

    /**
     * Crée un utilisateur
     * @param userDTO données de l'utilisateur à créer
     * @return
     */
    @Override
    public UserDTO createUser(UserDTO userDTO){
        User user = (User)transformers.convertDtoToEntity(userDTO, User.class);
        User userTest = userRepository.findByEmail(user.getEmail());
        if (userTest != null){
        	throw new GameMasterException(ConstanteGameMaster.SIGNUP_ERROR);
        }else{
            // TODO : chiffrer le password
        	user.setId(null);
            user.setDateCreation(Calendar.getInstance().getTime());
            userRepository.save(user);
        }
        return (UserDTO)transformers.convertEntityToDto(user, UserDTO.class);
    }

    /**
     * Retourne l'utilisateur connecté
     * @return l'utilisateur connecté
     */
    @Override
    public User getCurrentUser() {
        String mail = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(mail);
    }

    /**
     * Retourne l'utilisateur dont l'id est passé en paramètre
     * @param id l'id de l'utilisateur
     * @return l'utilisateur correspondant
     */
    @Override
    public UserDTO getUser(long id){
    	 return (UserDTO) transformers.convertEntityToDto(userRepository.findOne(id), UserDTO.class);
    }

    /**
     * Modifie un utilisateur
     * @param userDTO données de l'utilisateur à modifier
     * @return DTO de l'utilisateur modifié
     */
    @Override
    public UserDTO editUser(UserDTO userDTO){
        User user = userRepository.findOne(userDTO.getId());
        if(user!=null && (userDTO.getId()!=null)){
              user.setFirstName(userDTO.getFirstName());
              user.setLastName(userDTO.getLastName());
              userRepository.save(user);
        }
        else{
        	throw new GameMasterException(ConstanteGameMaster.EDIT_ERROR);
        }
      
        return (UserDTO)transformers.convertEntityToDto(user, UserDTO.class);
    }

    /**
     * Retourne l'inventaire d'un utilisateur
     * @param id l'id de l'utilisateur
     * @return liste des items possédés par l'utilisateur
     */
    @Override
    public List<ItemDto> getUserInventory(Long id) {
        User user = userRepository.findOne(id);
        List<ItemDto> itemDtoList = new ArrayList<>();
        user.getInventory().stream().forEach(item -> itemDtoList.add((ItemDto)this.transformers.convertEntityToDto(item, ItemDto.class)));
        return itemDtoList;
    }

    /**
     * Modifie l'inventaire d'un utilisateur
     * @param itemDtoList liste des objets possédés par l'utilisateur
     * @return inventaire mis à jour
     */
    @Override
    public List<ItemDto> updateInventory(List<ItemDto> itemDtoList) {
        User user = this.getCurrentUser();
        List<Item> items = new ArrayList<>();

        itemDtoList.stream().forEach(itemDto -> items.add((Item)this.transformers.convertDtoToEntity(itemDto, Item.class)));
        user.setInventory(items);
        user = userRepository.save(user);

        itemDtoList.clear();
        user.getInventory().stream().forEach(item -> itemDtoList.add((ItemDto)this.transformers.convertEntityToDto(item, ItemDto.class)));
        return itemDtoList;
    }
}
