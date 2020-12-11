/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dostojic.climbers.communication;

import com.dostojic.climbers.communication.Operation;
import com.dostojic.climbers.communication.Receiver;
import com.dostojic.climbers.communication.Request;
import com.dostojic.climbers.communication.Response;
import com.dostojic.climbers.communication.Sender;
import com.dostojic.climbers.domain.Climber;
import com.dostojic.climbers.domain.User;
import com.dostojic.climbers.exception.LoginException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author planina
 */
public class Communication {

    private Socket socket;
    private Sender sender;
    private Receiver receiver;
    private static Communication instance;

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
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return new GeneralCommunication<User>(){}
                .makeDataRequest(Operation.LOGIN, user);
    }

    public List<Climber> getAllClimbers() throws Exception {
        return new GeneralCommunication<List<Climber>>() {}
                .makeDataRequest(Operation.GET_ALL_CLIMBERS);
    }

    public Climber findClimberById(Integer id) throws Exception {
        return new GeneralCommunication<Climber>() {}
                .makeDataRequest(Operation.FIND_CLIMBER, id);
    }

    public void delteClimberById(Integer id) throws Exception {
        new GeneralCommunication<Optional<Climber>>() {}
                .makeDataRequest(Operation.DELETE_CLIMBER, id);
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
