/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.communication;

import com.dostojic.climbers.common.communication.Operation;
import com.dostojic.climbers.common.communication.Receiver;
import com.dostojic.climbers.common.communication.Request;
import com.dostojic.climbers.common.communication.Response;
import com.dostojic.climbers.common.communication.Sender;
import com.dostojic.climbers.common.dto.ClimberDto;
import com.dostojic.climbers.common.dto.LoginCredentialsDto;
import com.dostojic.climbers.common.dto.UserDto;
import com.dostojic.climbers.view.model.Climber;
import com.dostojic.climbers.view.model.User;
import com.dostojic.climbers.view.model.mapper.ClimberMapper;
import com.dostojic.climbers.view.model.mapper.UserMapper;
import java.net.Socket;
import java.util.List;

/**
 *
 * @author planina
 */
public class Communication {

    private final Socket socket;
    private final Sender sender;
    private final Receiver receiver;
    private static Communication instance;
    private final ClimberMapper climberMapper = ClimberMapper.INSTANCE;

    private Communication() throws Exception {
        socket = new Socket("localhost", 9000);
        sender = new Sender(socket);
        receiver = new Receiver(socket);
    }

    public static Communication getInstance() throws Exception {
        if (instance == null) {
            instance = new Communication();
        }
        return instance;
    }

    public User login(String username, String password) throws Exception {
        LoginCredentialsDto loginCredentials = new LoginCredentialsDto(username, password);
        UserDto resp = new GeneralCommunication<UserDto>(){}
                .makeDataRequest(Operation.LOGIN, loginCredentials);
        return UserMapper.INSTANCE.fromDto(resp);
    }

    public List<Climber> getAllClimbers() throws Exception {
        List<ClimberDto> climberDtoList = new GeneralCommunication<List<ClimberDto>>() {}
                .makeDataRequest(Operation.GET_ALL_CLIMBERS);
        return climberMapper.fromDto(climberDtoList);
    }

    public Climber findClimberById(Integer id) throws Exception {
        ClimberDto climberDto = new GeneralCommunication<ClimberDto>() {}
                .makeDataRequest(Operation.FIND_CLIMBER, id);
        return climberMapper.fromDto(climberDto);
                
    }

    public void delteClimberById(Integer id) throws Exception {
        new GeneralCommunication() {}
                .makeVoidRequest(Operation.DELETE_CLIMBER, id);
    }

    public void updateClimber(Climber climber) throws Exception {
        new GeneralCommunication() {}
                .makeVoidRequest(Operation.UPDATE_CLIMBER,
                        climberMapper.toDto(climber));
    }

    public Climber saveClimber(Climber climber) throws Exception {
        ClimberDto dto = new GeneralCommunication<ClimberDto>() {}
                .makeDataRequest(Operation.SAVE_CLIMBER,
                        climberMapper.toDto(climber));
        return climberMapper.fromDto(dto);
    }

    /**
     * Generic template for creating requests.
     * It abstracts away dealing with sender and receiver 
     * @param <T>
     */
    private abstract class GeneralCommunication<T> {

        public final T makeDataRequest(Operation operation, Object argument) throws Exception {
            Request request = new Request(operation, argument);
            sender.send(request);
            Response response = (Response) receiver.receive();
            if (response.getException() == null) {
                if (response.getResult() != null) {
                    return (T) response.getResult();
                }
                return null;
            } else {
                throw response.getException();
            }
        }

        public final T makeDataRequest(Operation operation) throws Exception {
            return makeDataRequest(operation, null);
        }
        
        public final void makeVoidRequest(Operation operation) throws Exception {
            makeDataRequest(operation, null);
        }
        
        public final void makeVoidRequest(Operation operation, Object argument) throws Exception {
            makeDataRequest(operation, argument);
        }

    }
}
