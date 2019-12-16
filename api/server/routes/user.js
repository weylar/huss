import { Router } from 'express';
import UserController from '../controllers/user';
import UserValidation from '../middleware/validations/user';
import Auth from '../middleware/utils/Auth';

const {
  signUpCheck, loginCheck, userDetailsCheck, userImageCheck
} = UserValidation;
const {
  signUpUser, logInUser, addUserDetails, userImage, sendPasswordResetEmail, receiveNewPassword, logOutUser, deleteUser
} = UserController;
const { getUser } = Auth;

const userRouter = Router();
const userDetails = Router();

userRouter.post('/signup', signUpCheck, signUpUser);
userRouter.post('/login', loginCheck, logInUser);
userRouter.put('/logout', getUser, logOutUser);
userDetails.put('/profile', getUser, userDetailsCheck, addUserDetails);
userDetails.put('/profile/image', getUser, userImageCheck, userImage);
userDetails.get('/:email', sendPasswordResetEmail);
userDetails.put('/new_password/:id/:token', receiveNewPassword);
userDetails.put('/profile/delete', getUser, deleteUser);


module.exports = {
  userRouter, userDetails
};