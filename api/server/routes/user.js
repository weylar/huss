import { Router } from 'express';
import UserController from '../controllers/user';
import UserValidation from '../middleware/validations/user';
import Auth from '../middleware/utils/Auth';

const {
  signUpCheck, loginCheck, userDetailsCheck
} = UserValidation;
const {
  signUpUser, logInUser, addUserDetails
} = UserController;
const { getUser } = Auth;

const userRouter = Router();
const userDetails = Router();

userRouter.post('/signup', signUpCheck, signUpUser);
userRouter.post('/login', loginCheck, logInUser);
userDetails.put('/profile', getUser, userDetailsCheck, addUserDetails)

module.exports = {
  userRouter, userDetails
};