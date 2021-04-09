package com.cloud.authservice;

import com.cloud.authservice.entity.*;
import com.cloud.authservice.exception.NoMessageFoundException;
import com.cloud.authservice.exception.NoUserFoundException;
import com.cloud.authservice.service.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class AuthServiceApplicationTests {

	@Autowired
	UserService userService;

	@Autowired
	OperatorService operatorService;

	@Autowired
	ActionService actionService;

	@Autowired
	ErrorsService errorsService;

	@Test
	void testOperatorService() throws NoUserFoundException, NoMessageFoundException {
		// create users
		User user1 = new User();
		user1.setUsername("deneme1");
		user1.setEmail("deneme1@gmail.com");
		user1.setPassword("123456");

		User user2 = new User();
		user2.setUsername("deneme2");
		user2.setEmail("deneme2@gmail.com");
		user2.setPassword("123456");

		User user3 = new User();
		user3.setUsername("deneme3");
		user3.setEmail("deneme3@gmail.com");
		user3.setPassword("123456");

		User user4 = new User();
		user4.setUsername("deneme4");
		user4.setEmail("deneme4@gmail.com");
		user4.setPassword("123456");

		User addedUser1 = operatorService.addUser(user1);
		User addedUser2 = operatorService.addUser(user2);
		User addedUser3 = operatorService.addUser(user3);
		User addedUser4 = operatorService.addUser(user4);
		User addUser1Again = operatorService.addUser(user1);

		// test delete user

		User userToDelete = userService.get(addedUser1.getId());

		operatorService.deleteUser(userToDelete);

		Assert.assertEquals(3, userService.getAll().size());

		// test actions

		List<Action> allActions = actionService.getAll();
		List<Action> allActionsOfUser1 = actionService.getAllByUserId(addedUser1.getId());
		List<Action> allSendMessageActionsOfUser1 = actionService.getAllByUserIdAndActionType(addedUser1.getId(), Action.ActionType.SEND_MESSAGE);

		Assert.assertEquals(18, allActions.size());
		Assert.assertEquals(8, allActionsOfUser1.size());
		Assert.assertEquals(2, allSendMessageActionsOfUser1.size());
	}

	@Test
	void testUser() throws NoUserFoundException {
		// test add
		User user1 = new User();
		user1.setUsername("deneme1");
		user1.setEmail("deneme1@gmail.com");
		user1.setPassword("123456");

		User user2 = new User();
		user2.setUsername("deneme2");
		user2.setEmail("deneme2@gmail.com");
		user2.setPassword("123456");

		User addedUser1 = userService.add(user1);
		User addedUser2 = userService.add(user2);

		Assert.assertEquals(user1.getUsername(),userService.get(addedUser1.getId()).getUsername());
		Assert.assertEquals(user2.getUsername(),userService.get(addedUser2.getId()).getUsername());
		Assert.assertEquals(2, userService.getAll().size());

		// test update
		addedUser1.setEmail("changed@gmail.com");
		addedUser2.setUsername("changed2");

		User updatedUser1 = userService.update(addedUser1);
		User updatedUser2 = userService.update(addedUser2);

		Assert.assertEquals(updatedUser1.getEmail(), userService.get(addedUser1.getId()).getEmail());
		Assert.assertEquals(updatedUser2.getUsername(), userService.get(addedUser2.getId()).getUsername());

		// test delete
		userService.delete(updatedUser1);
		userService.delete(updatedUser2);

		Assert.assertEquals(0, userService.getAll().size());

		// test errors
		Errors errors = Errors.newError(addedUser1.getId(), addedUser1.getUsername(), "error");
		errorsService.add(errors);

		Assert.assertEquals(1, errorsService.getAll().size());
		Assert.assertEquals(1, errorsService.getAllByUserId(addedUser1.getId()).size());
	}

}
