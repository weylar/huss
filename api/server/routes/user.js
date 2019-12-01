import { Router } from 'express';
import UserController from '../controllers/user';
import UserValidation from '../middleware/validations/user';
import Auth from '../middleware/utils/Auth';

const {
  signUpCheck, loginCheck
} = UserValidation;
const {
  signUpUser, logInUser
} = UserController;
const { getUser } = Auth;

const userRouter = Router();

userRouter.post('/signup', signUpCheck, signUpUser);
userRouter.post('/login', loginCheck, logInUser)

export default userRouter;